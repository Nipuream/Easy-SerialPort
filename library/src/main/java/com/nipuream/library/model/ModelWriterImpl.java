package com.nipuream.library.model;

import android.text.TextUtils;

import com.hikvision.communication.common.DeviceType;
import com.hikvision.communication.common.TMCommand;
import com.hikvision.communication.common.UpgradeInfo;
import com.hikvision.communication.common.WriterInfo;
import com.hikvision.communication.convery.upgrade.IUpgradeHandle;
import com.hikvision.communication.exception.ParamNullException;
import com.hikvision.communication.exception.UpgradeException;
import com.hikvision.communication.fetcher.IWriter;
import com.hikvision.communication.utils.Logger;
import com.hikvision.communication.utils.ParseUtil;
import com.hikvision.devicemodule.DeviceInfo;
import com.hikvision.devicemodule.inner.BusinessRecordQueryI;
import com.hikvision.devicemodule.inner.ClockQueryI;
import com.hikvision.devicemodule.inner.ExitQueryListI;
import com.hikvision.devicemodule.inner.FreightParamSettingI;
import com.hikvision.devicemodule.inner.GetCarAppInfoI;
import com.hikvision.devicemodule.inner.GetDriverExtraInfoI;
import com.hikvision.devicemodule.inner.HeartBeatI;
import com.hikvision.devicemodule.inner.LocalParamSettingI;
import com.hikvision.devicemodule.inner.MeterParamSettingI;
import com.hikvision.devicemodule.inner.MeterQueryI;
import com.hikvision.devicemodule.inner.MeterUpgradeI;
import com.hikvision.devicemodule.inner.OnceBusinessEndI;
import com.hikvision.devicemodule.inner.OnceBusinessStartI;
import com.hikvision.devicemodule.inner.OpenShowI;
import com.hikvision.devicemodule.inner.QueryDetailBusinessI;
import com.hikvision.devicemodule.inner.QueryListNotifyI;
import com.hikvision.devicemodule.inner.TxClockSettingI;
import com.hikvision.devicemodule.inner.WorkSignOutI;
import com.hikvision.devicemodule.inner.WorkSignOutNotifyI;
import com.hikvision.devicemodule.inner.WorkSignedI;

import java.util.Arrays;

/**
 * Created by yanghui11 on 2020/4/7.
 *
 */
public class ModelWriterImpl implements IModelWriter{

    private IWriter writer;
    private static ModelWriterImpl modelWriter;
    private IUpgradeHandle handle;

    public synchronized static ModelWriterImpl createWriter(IWriter writer,IUpgradeHandle handle){
        if(modelWriter == null){
            modelWriter = new ModelWriterImpl(writer,handle);
        }
        return modelWriter;
    }

    private ModelWriterImpl(IWriter writer,IUpgradeHandle handle){
        this.writer = writer;
        this.handle = handle;
    }

    @Override
    public void openShowResponse(OpenShowI info) throws ParamNullException,UpgradeException{

        checkSafyLock(info);

        byte response = info.getResponse();
        byte[] sendMsg = {response};
        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.OPEN_SHOW, sendMsg,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void meterClockQuery(ClockQueryI info) throws ParamNullException, UpgradeException{

        checkSafyLock(info);

        byte[] body = ParseUtil.str2Bcd(info.getCurrentTime());
        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.METER_CLOCK_QUERY, body, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void meterUpgrade(MeterUpgradeI info) throws ParamNullException, UpgradeException{

        checkSafyLock(info);

        byte vendorID = info.getVendorID();
        byte hwVersion = info.getHwVersion();
        byte softVersion = info.getSwVersion();
        byte softSubVersion = info.getSwbVersion();

        byte[] body = new byte[4];
        body[0] = vendorID;
        body[1] = hwVersion;
        body[2] = softVersion;
        body[3] = softSubVersion;

        if(handle != null){
            //升级用
            handle.changeUpgradeInfo(new UpgradeInfo(vendorID, hwVersion, softSubVersion, softSubVersion));
        }

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.METER_UPGRADE, body, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void meterQuery(MeterQueryI info) throws ParamNullException, UpgradeException{

        checkSafyLock(info);

        String curTime = info.getCurTime();
        byte[] body = ParseUtil.str2Bcd(curTime);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.METER_QUERY, body, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void heartBeat(HeartBeatI info) throws ParamNullException, UpgradeException{

        checkSafyLock(info);

        byte[] state = ParseUtil.int2BytesH(info.getIsuState(),2);
        byte[] timeLimit = ParseUtil.str2Bcd(info.getTimeLimit());
        byte numLimit = info.getNumLimit();
        byte[] rfuBuf = ParseUtil.int2BytesH(info.getRFU(),2);

        int offset = 0;
        byte[] body = new byte[10];
        System.arraycopy(state,0,body,offset,2);
        offset += 2;
        System.arraycopy(timeLimit,0,body,offset,5);
        offset += 5;
        body[offset++] = numLimit;
        System.arraycopy(rfuBuf,0,body,offset,2);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.HEART_BEAT,body,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void queryListNotify(QueryListNotifyI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] body = new byte[1];
        body[0] = info.getResponse();

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.QUERY_LIST_NOTITY, body, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void exitQueryList(ExitQueryListI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);
        byte[] body = new byte[1];
        body[0] = info.getState();

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.BACK_QUERY_NOTITY, body, true);
        writerInfo.setTryTimes(3);

        writer.writeMsg(writerInfo);
    }

    @Override
    public void queryTotalRecord(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.TOTAL_QUERY, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void queryDetailBusiness(QueryDetailBusinessI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.QUERY_DETAIL_RECORD,new byte[]{info.getState()}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void businessParamQuery(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.PARAM_QUERY_GET, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void localParamQuery(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.LOCAL_PARAM_SET_GET, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void paramSetting(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.PARAM_SET_NOTITY, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    // 0x9915
    @Override
    public void freightParamSetting(FreightParamSettingI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] data = new byte[30];
        int offset = 0;
        data[offset++] = info.getResult();

        byte[] lightRoundPrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getLightRoundPrice()),16),2);
        System.arraycopy(lightRoundPrice,0,data,offset,2);
        offset += 2;

        byte[] blackRoundPrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getBlackRoundPrice()),16),2);
        System.arraycopy(blackRoundPrice,0,data,offset,2);
        offset += 2;

        byte[] lightOnePrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getLightOnePrice()),16),2);
        System.arraycopy(lightOnePrice,0,data,offset,2);
        offset += 2;

        byte[] blackOnePrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getBlackOnePrice()),16),2);
        System.arraycopy(blackOnePrice, 0, data, offset,2);
        offset += 2;

        byte[] lightStartPrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getLightStartPrice()),16),2);
        System.arraycopy(lightStartPrice,0,data,offset,2);
        offset += 2;

        byte[] blackStartPrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getBlackStartPrice()),16),2);
        System.arraycopy(blackStartPrice,0,data,offset,2);
        offset += 2;

        data[offset++] = (byte) Integer.parseInt(String.valueOf(info.getContinueMile()),16);

        byte[] startMile = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getStartMile()),16),2);
        System.arraycopy(startMile,0,data,offset,2);
        offset += 2;

        byte[] oneMile = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getOneMile()),16),2);
        System.arraycopy(oneMile,0,data,offset,2);
        offset += 2;

        //白天等待单价
        byte[] lightWaitPrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getLightWaitMile()),16),2);
        System.arraycopy(lightWaitPrice,0,data,offset,2);
        offset += 2;

        //夜间等待单价
        byte[] blackWaitPrice = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getBlackWaitMile()),16),2);
        System.arraycopy(blackWaitPrice,0,data,offset,2);
        offset += 2;

        // 免费等待时间
        byte[] freeWaitTime = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getFreeWaitTime()),16),2);
        System.arraycopy(freeWaitTime,0,data,offset,2);
        offset += 2;

        //加费时间
        byte[] addFreeTime = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getAddFreeTime()),16),2);
        System.arraycopy(addFreeTime,0,data,offset,2);
        offset += 2;

        //夜间开始时间
        byte[] blackStartTime = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getBlackStartTime()),16),2);
        System.arraycopy(blackStartTime,0,data,offset,2);
        offset += 2;

        //夜间结束时间
        byte[] blackEndTime = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getBlackEndTime()),16),2);
        System.arraycopy(blackEndTime,0,data,offset,2);


        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER , TMCommand.PARAM_SET_SAVE, data, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void localParamSetting(LocalParamSettingI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] data = new byte[40];
        int offset = 0;

        data[offset++] = info.getResult();

        byte[] kValue = ParseUtil.int2BytesH(Integer.parseInt(String.valueOf(info.getkValue()),16),2);
        System.arraycopy(kValue,0,data,offset,2);
        offset += 2;

        byte[] phoneNumber = ParseUtil.strToAscii(info.getPhoneNum());
        System.arraycopy(phoneNumber,0, data,offset,12);
        offset += 12;

        byte[] carNumber = ParseUtil.strToAscii(info.getCarNumber());
        System.arraycopy(carNumber,0,data,offset,7);
        offset += 7;

        byte[] businessCode = ParseUtil.strToAscii(info.getBusinessCode());
        System.arraycopy(businessCode,0,data,offset,12);
        offset += 12;

        byte[] businessNo = ParseUtil.strToAscii(info.getBusinessNo());
        System.arraycopy(businessNo,0,data,offset,6);
        offset += 6;

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.LOCAL_PARAM_SET_SAVE, data,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void txClockLook(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.CLOCK_QUERY, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void txClockSetting(TxClockSettingI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] data = new byte[8];
        int offset = 0;
        data[offset++] = info.getResult();
        byte[] time = ParseUtil.str2Bcd(info.getMeterDate(),7);
        System.arraycopy(true,0,data,offset,7);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.CLOCK_SET_SAVE, data, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void txClockSettingA(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.CLOCK_SET_CALIBRATION, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void getDriverInfo(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.GET_DRIVER_INFO, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void getDriverExtraInfo(GetDriverExtraInfoI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] data = new byte[8];
        int offset = 0;

        data[offset ++] = info.getAppType();
        System.arraycopy(info.getFileDes(),0,data,offset,2);
        offset += 2;


        //filetype < 16 则直接转化
        byte[] fileType = ParseUtil.parseIntToHexInt(info.getFileType(),1);
        Logger.getLogger().i("fileType : "+Arrays.toString(fileType));
        data[offset ++] = fileType[0];

        //10 进制转16进制

        byte[] readFileAddrBuf = ParseUtil.parseIntToHexInt(info.getReadFileAddress(),2);
        Logger.getLogger().i("readFileAddrBuf : "+ Arrays.toString(readFileAddrBuf));
        if(readFileAddrBuf != null){
            System.arraycopy(readFileAddrBuf,0,data,offset,2);
        }
        offset += 2;

        byte[] readFileLengthBuf =  ParseUtil.parseIntToHexInt(info.getReadFileLength(),2);
        Logger.getLogger().i("readFileLengthBuf : "+Arrays.toString(readFileLengthBuf));
        if(readFileLengthBuf != null){
            System.arraycopy(readFileLengthBuf,0,data,offset,2);
        }

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.GET_DRIVER_EXTRA_INFO,data,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void getDriverAppInfo(GetCarAppInfoI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.GET_CAR_APP_INFO, info.getBytes(), false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void workSignResponse(WorkSignedI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] data = new byte[57];
        int offset = 0;

        //经营许可证号
        if(!TextUtils.isEmpty(info.getBusinessId())){
            byte[] businessId = ParseUtil.asciiToBytes(info.getBusinessId());
            System.arraycopy(businessId,0,data,offset,16);
        }
        offset += 16;

        //从业资格证号
        if(!TextUtils.isEmpty(info.getCertId())){
            byte[] certId = ParseUtil.asciiToBytes(info.getCertId());
            System.arraycopy(certId,0,data,offset,19);
        }
        offset += 19;

        //车牌号
        byte[] carNumber = ParseUtil.asciiToBytes(info.getCarNumber());
        System.arraycopy(carNumber,0,data,offset,6);
        offset += 6;

        //签到时间
        byte[] signTime = ParseUtil.str2Bcd(info.getSignTime(),6);
        System.arraycopy(signTime,0,data,offset,6);
        offset += 6;

        //ISU状态
        byte[] isuState = ParseUtil.parseIntToHexInt(info.getSignState(),2);
        System.arraycopy(isuState,0,data,offset,2);
        offset += 2;

        //时间限制
        byte[] timeLimit = ParseUtil.str2Bcd(info.getTimeLimit(),5);
        System.arraycopy(timeLimit,0,data,offset,5);
        offset += 5;

        //次数限制
        byte[] timesLimit = ParseUtil.str2Bcd(info.getTimesLimit(),2);
        System.arraycopy(timesLimit,0,data,offset,2);
        offset += 2;

        //操作结果
        byte[]  result = ParseUtil.parseIntToHexInt(info.getResult(),1);
        data[offset] = result[0];

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.WORK_SIGNED,data,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void meterParamQuery(DeviceInfo info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.METER_PARAM_QUERY, new byte[]{}, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void meterParamSetting(MeterParamSettingI info) throws ParamNullException, UpgradeException {
        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.Meter_PARAM_SETTING, info.getParamSetting(), false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void businessRecordQuery(BusinessRecordQueryI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        byte[] carNo   = ParseUtil.int2BytesH(info.getCarNo(),4);
        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER, TMCommand.BUSINESS_RECORD_QUERY, carNo, false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void workSignOut(WorkSignOutI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);
        byte[] data = new byte[57];
        int offset = 0;

        byte[] businessId = ParseUtil.asciiToBytes(info.getBusinessId());
        System.arraycopy(businessId,0,data,offset,16);
        offset += 16;

        byte[] certId = ParseUtil.asciiToBytes(info.getCertId());
        System.arraycopy(certId,0,data,offset,19);
        offset += 19;

        byte[] carNumber = ParseUtil.asciiToBytes(info.getCarNumber());
        System.arraycopy(carNumber,0,data,offset,6);
        offset += 6;

        byte[] signInTime = ParseUtil.str2Bcd(info.getSignInTime(),6);
        System.arraycopy(signInTime,0,data,offset,6);
        offset += 6;

        byte[] isuState = ParseUtil.int2BytesH(Integer.valueOf(String.valueOf(info.getIsuState()),16),2);
        System.arraycopy(isuState,0,data,offset,2);
        offset += 2;

        byte[] timeLimit = ParseUtil.str2Bcd(info.getTimeLimit(),5);
        System.arraycopy(timeLimit,0,data,offset,5);
        offset += 5;

        byte[] timesLimit = ParseUtil.str2Bcd(info.getTimesLimit(),2);
        System.arraycopy(timesLimit,0,data,offset,2);
        offset += 2;

        data[offset] = Integer.valueOf(String.valueOf(info.getResult()),16).byteValue();

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.WORK_SIGNED_OUT,data,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void workSignOutNotify(WorkSignOutNotifyI info) throws ParamNullException, UpgradeException {

        byte result = Integer.valueOf(String.valueOf(info.getResult()),16).byteValue();
        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.WORK_SIGNED_OUT_NOTIRY, new byte[]{result},false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void workSignOutSupple(WorkSignOutI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);
        byte[] data = new byte[57];
        int offset = 0;

        byte[] businessId = ParseUtil.asciiToBytes(info.getBusinessId());
        System.arraycopy(businessId,0,data,offset,16);
        offset += 16;

        byte[] certId = ParseUtil.asciiToBytes(info.getCertId());
        System.arraycopy(certId,0,data,offset,19);
        offset += 19;

        byte[] carNumber = ParseUtil.asciiToBytes(info.getCarNumber());
        System.arraycopy(carNumber,0,data,offset,6);
        offset += 6;

        byte[] signInTime = ParseUtil.str2Bcd(info.getSignInTime(),6);
        System.arraycopy(signInTime,0,data,offset,6);
        offset += 6;

        byte[] isuState = ParseUtil.int2BytesH(Integer.valueOf(String.valueOf(info.getIsuState()),16),2);
        System.arraycopy(isuState,0,data,offset,2);
        offset += 2;

        byte[] timeLimit = ParseUtil.str2Bcd(info.getTimeLimit(),5);
        System.arraycopy(timeLimit,0,data,offset,5);
        offset += 5;

        byte[] timesLimit = ParseUtil.str2Bcd(info.getTimesLimit(),2);
        System.arraycopy(timesLimit,0,data,offset,2);
        offset += 2;

        data[offset] = Integer.valueOf(String.valueOf(info.getResult()),16).byteValue();

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.SIGN_OUT_DATA_SUPPLEMENT,data,false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void onceBusinessStart(OnceBusinessStartI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.ONCE_BUSINESS_START_NOTIFY,new byte[]{info.getResult()},false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void onceBusinessEnd(OnceBusinessEndI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.ONCE_BUSINESS_END_NOTIFY, new byte[]{info.getResult()},false);
        writer.writeMsg(writerInfo);
    }

    @Override
    public void businessDataSupply(OnceBusinessEndI info) throws ParamNullException, UpgradeException {

        checkSafyLock(info);

        WriterInfo writerInfo = new WriterInfo(DeviceType.DEVICE_METER,TMCommand.BUSINESS_DATA_SUPPLE, new byte[]{info.getResult()}, false);
        writer.writeMsg(writerInfo);
    }


    private <T extends DeviceInfo> void checkSafyLock(T t) throws ParamNullException,UpgradeException{

        if(!((t != null) && (writer != null))){
            throw new ParamNullException();
        }

        if(handle != null && handle.isUpgrading()){
            throw new UpgradeException();
        }
    }


}
