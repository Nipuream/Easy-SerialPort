package com.nipuream.library.model;

import com.hikvision.communication.common.CommonUtils;
import com.hikvision.communication.common.ModelConvert;
import com.hikvision.communication.common.TMCommand;
import com.hikvision.communication.utils.Logger;
import com.hikvision.communication.utils.ParseUtil;
import com.hikvision.devicemodule.DeviceInfo;
import com.hikvision.devicemodule.outter.BusinessParamQueryO;
import com.hikvision.devicemodule.outter.BusinessRecordQueryO;
import com.hikvision.devicemodule.outter.ClockQueryO;
import com.hikvision.devicemodule.outter.EmptyCarO;
import com.hikvision.devicemodule.outter.ExitQueryListO;
import com.hikvision.devicemodule.outter.FreightParamSettingO;
import com.hikvision.devicemodule.outter.GetCarAppInfoO;
import com.hikvision.devicemodule.outter.GetDriverExtraInfoO;
import com.hikvision.devicemodule.outter.GetDriverInfoO;
import com.hikvision.devicemodule.outter.HeartBeatO;
import com.hikvision.devicemodule.outter.HeavyCarShowO;
import com.hikvision.devicemodule.outter.LocalParamQueryO;
import com.hikvision.devicemodule.outter.LocalParamSettingO;
import com.hikvision.devicemodule.outter.MeterParamQueryO;
import com.hikvision.devicemodule.outter.MeterParamSettingO;
import com.hikvision.devicemodule.outter.MeterQueryO;
import com.hikvision.devicemodule.outter.MeterUpgradeO;
import com.hikvision.devicemodule.outter.OnceBusinessEndO;
import com.hikvision.devicemodule.outter.OnceBusinessStartO;
import com.hikvision.devicemodule.outter.ParamSettingO;
import com.hikvision.devicemodule.outter.QueryDetailBusinessO;
import com.hikvision.devicemodule.outter.QueryTotalO;
import com.hikvision.devicemodule.outter.QueryListNotifyO;
import com.hikvision.devicemodule.outter.SignOutSuppleO;
import com.hikvision.devicemodule.outter.TxClockLookO;
import com.hikvision.devicemodule.outter.TxClockSettingAO;
import com.hikvision.devicemodule.outter.TxClockSettingO;
import com.hikvision.devicemodule.outter.WorkSignOutNotifyO;
import com.hikvision.devicemodule.outter.WorkSignOutO;
import com.hikvision.devicemodule.outter.WorkSignSuccessO;
import com.hikvision.devicemodule.outter.WorkSignedO;
import com.nipuream.library.common.DeviceInfo;

/**
 * Created by yanghui11 on 2020/4/10.
 *
 *  二进制数据 转化为 DeviceInfo 的派生类
 */
@SuppressWarnings("unchecked")
public class Convert <T extends DeviceInfo>{


    @ModelConvert(commId = TMCommand.EMPTY_CAR_SHOW)
    public T emptyShow(byte[] data,int offset){
        EmptyCarO t = new EmptyCarO();

        int price = 0;
        try{
            price = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
        } catch (Exception e){
            e.printStackTrace();
        }

        t.setPrice(price);
        offset += 2;

        int mile = 0;
        try{
            mile = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        } catch (Exception e){
            e.printStackTrace();
        }

        t.setMile(mile);
        offset += 4;

        t.setTime(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;
        t.setClock(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        int money = 0;
        try{
            money = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        }catch (Exception e){
            e.printStackTrace();
        }
        t.setMoney(money);

        offset += 4;
        t.setState(ParseUtil.byteToInt(data,offset,4));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.HEAVY_CAR_SHOW)
    public T heavyCarShow(byte[] data,int offset){

        HeavyCarShowO t = new HeavyCarShowO();

        int price = data[offset++];
        int kValue = data[offset++];
        t.setPrice(price);
        t.setkValue(kValue);

        int mile = ParseUtil.byteToInt(data,offset,4);
        t.setMile(mile);
        offset += 4;


        t.setTime(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        t.setCurTime(ParseUtil.bcd2Str(data,offset,2));
        offset+=2;

        int money = ParseUtil.byteToInt(data,offset,4);
        t.setMoney(money);
        offset += 4;

        t.setState(ParseUtil.byteToInt(data,offset,4));
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.METER_CLOCK_QUERY)
    public T meterClockQuery(byte[] data, int offset){

        ClockQueryO t = new ClockQueryO();
        t.setResult(data[offset]);
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.METER_UPGRADE)
    public T meterUpgrade(byte[] data, int offset){

        MeterUpgradeO t  = new MeterUpgradeO();
        t.setResult(data[offset]);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.METER_QUERY)
    public T meterQuery(byte[] data, int offset){

        MeterQueryO t = new MeterQueryO();
        try{
            t.setIsuCode(ParseUtil.bcd2Str(data,offset,5));
            offset += 5;
            t.setHardwareCode(data[offset++]);
            t.setSoftWareCode(data[offset++]);
            t.setSoftWareSubCode(data[offset++]);
            t.setDevState(data[offset++]);
            t.setMeterState(data[offset++]);
            t.setCarNumber(ParseUtil.byteToString(data,offset,6));
            offset += 6;
            t.setBussineId(ParseUtil.byteToString(data,offset,16));
            offset += 16;
            t.setCertId(ParseUtil.byteToString(data,offset,19));
            offset += 19;
            t.setBussineNum(data[offset]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.HEART_BEAT)
    public T heartBeat(byte[] data, int offset){

        HeartBeatO t = new HeartBeatO();
        t.setMeterState(data[offset++]);
        t.setBussinessId(ParseUtil.byteToString(data,offset,16));
        offset += 16;
        t.setCertId(ParseUtil.byteToString(data,offset,19));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.QUERY_LIST_NOTITY)
    public T queryListNotify(byte[] data, int offset){

        QueryListNotifyO t = new QueryListNotifyO();
        t.setState(data[offset++]);

        int kValue = ParseUtil.byteToInt(data,offset,2);
        t.setkValue(kValue);
        return (T) t;
    }


    @ModelConvert(commId = TMCommand.BACK_QUERY_NOTITY)
    public T exitQueryList(byte[] data, int offset){

        ExitQueryListO t = new ExitQueryListO();
        t.setResponse(data[offset]);
        return (T) t;
    }


    @ModelConvert(commId = TMCommand.TOTAL_QUERY)
    public T queryTotalBusiness(byte[] data, int offset){

        QueryTotalO t = new QueryTotalO();

        int totalRunMile = 0;
        try{
            totalRunMile = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        } catch (Exception e){
            e.printStackTrace();
        }
        t.setTotalRunMile(totalRunMile);

        offset += 4;

        int totalBusinessMile = 0 ;
        try{
            totalBusinessMile = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        }catch (Exception e){
            e.printStackTrace();
        }
        t.setTotalBusinessMile(totalBusinessMile);

        offset += 4;

        t.setTotalTime(ParseUtil.byteToString(data,offset,5));

        offset += 5;

        int totalBusinessMoney = 0;
        try{
            totalBusinessMoney = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        } catch (Exception e){
            e.printStackTrace();
        }
        t.setTotalBusinessMoney(totalBusinessMoney);

        offset += 4;

        int businessNum = 0;
        try{
            businessNum = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        } catch (Exception e){
            e.printStackTrace();
        }
        t.setTotalBusinessNum(businessNum);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.QUERY_DETAIL_RECORD)
    public T queryDetailBusiness(byte[] data, int offset){

        QueryDetailBusinessO t = new QueryDetailBusinessO();
        t.setBusinessStartTime(ParseUtil.bcd2Str(data,offset,6));
        offset += 6;
        t.setBusinessEndTime(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        int businessMile = 0;
        try{
            businessMile = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        }catch (Exception e){
            e.printStackTrace();
        }

        t.setBusinessMile(businessMile);
        offset += 4;

        t.setBusinessTime(Integer.toHexString(ParseUtil.byteToInt(data,offset,3)));
        offset += 3;

        int businessMoney = 0;
        try{
            businessMoney = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        }catch (Exception e){
            e.printStackTrace();
        }
        t.setBusinessMoney(businessMoney);

        offset += 4;

        int price = 0;
        try{
            price = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
        }catch (Exception e){
            e.printStackTrace();
        }

        t.setPrice(price);
        offset += 2;

        int recordSerial = 0;
        try{
            recordSerial = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,4)));
        }catch (Exception e){
            e.printStackTrace();
        }

        t.setRecordSerial(recordSerial);

        return (T) t;
    }


    @ModelConvert(commId = TMCommand.PARAM_QUERY_GET )
    public T businessParamQuery(byte[] data, int offset){

        BusinessParamQueryO t = new BusinessParamQueryO();

        int lightRoundPrice = 0;
        try{
            lightRoundPrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setLightRoundPrice(lightRoundPrice);
        } catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int blackRoundPrice = 0;
        try{
            blackRoundPrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setBlackRoundPrice(blackRoundPrice);
        } catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int lightOnePrice = 0;
        try{
            lightOnePrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setLightOnePrice(lightOnePrice);
        } catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int blackOnePrice = 0;
        try{
            blackOnePrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setBlackOnePrice(blackOnePrice);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int lightStartPrice = 0;
        try{
            lightStartPrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setLightStartPrice(lightStartPrice);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int blackStartPrice = 0;
        try{
            blackStartPrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setBlackStartPrice(blackStartPrice);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int continueMile = 0;
        try{
            continueMile = Integer.parseInt(Integer.toHexString(data[offset++]));
            t.setContinueMile(continueMile);
        }catch (Exception e){
            e.printStackTrace();
        }

        int startMile = 0;
        try{
            startMile = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setStartMile(startMile);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int oneMile = 0;
        try{
            oneMile = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setOneMile(oneMile);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int lightWaitTime = 0;
        try{
            lightWaitTime = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setLightWaitPrice(lightWaitTime);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int blackWaitTime = 0;
        try{
            blackWaitTime = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setBlackWaitPrice(blackWaitTime);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int freeWaitPrice = 0;
        try{
            freeWaitPrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setFreeWaitPrice(freeWaitPrice);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        int addPrice = 0;
        try{
            addPrice = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
            t.setAddPrice(addPrice);
        }catch (Exception e){
            e.printStackTrace();
        }

        offset += 2;

        t.setBlackStartTime(ParseUtil.bcd2Str(data,offset,2));
        offset +=2;
        t.setBlackEndTime(ParseUtil.bcd2Str(data,offset,2));
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.LOCAL_PARAM_SET_GET)
    public T localParamQuery(byte[] data, int offset){

        LocalParamQueryO t = new LocalParamQueryO();

        int kValue = 0;
        try{
            kValue = Integer.parseInt(Integer.toHexString(ParseUtil.byteToInt(data,offset,2)));
        }catch (Exception e){
            e.printStackTrace();
        }
        t.setkValue(kValue);
        offset += 2;

        t.setPhoneNum(ParseUtil.byteToString(data,offset, 12));
        offset += 12;

        t.setCarNumber(ParseUtil.byteToString(data,offset,7));
        offset += 7;

        t.setBusinessCode(ParseUtil.byteToString(data,offset,12));
        offset += 12;

        t.setBusinessNo(ParseUtil.byteToString(data,offset,6));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.PARAM_SET_SAVE)
    public T freightParamSetting(byte[] data, int offset){

        FreightParamSettingO t = new FreightParamSettingO();
        t.setResponse(data[offset]);

        return (T) t;
    }


    @ModelConvert(commId = TMCommand.PARAM_SET_NOTITY)
    public T paramSetting(byte[] data, int offset){

        ParamSettingO t = new ParamSettingO();
        t.setResponse(data[offset]);
        return (T) t;
    }


    @ModelConvert(commId = TMCommand.LOCAL_PARAM_SET_SAVE)
    public T localParamSetting(byte[] data, int offset){

        LocalParamSettingO t = new LocalParamSettingO();
        t.setResponse(data[offset]);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.CLOCK_QUERY)
    public T txClockLook(byte[] data, int offset){

        TxClockLookO t = new TxClockLookO();
        String time = ParseUtil.bcd2Str(data,offset,7);
        t.setTxTime(time);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.CLOCK_SET_SAVE)
    public T txClockSetting(byte[] data, int offset){

        TxClockSettingO t = new TxClockSettingO();
        t.setResponse(data[offset]);
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.CLOCK_SET_CALIBRATION)
    public T txClockSettingA(byte[] data, int offset){

        TxClockSettingAO t = new TxClockSettingAO();
        t.setFlag(data[offset++]);
        t.setTime(ParseUtil.bcd2Str(data,offset,7));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.GET_DRIVER_INFO)
    public T getDriverInfo(byte[] data, int offset){

        GetDriverInfoO t = new GetDriverInfoO();

        try{

            t.setIcState(data[offset++]);
            t.setIcontryVer(data[offset++]);
            t.setIlocalVer(data[offset++]);

            byte[] name = new byte[12];
            System.arraycopy(data,offset,name,0,12);
            t.setName(ParseUtil.asciiToStr_l(name));
            offset += 12;

            byte[] address = new byte[48];
            System.arraycopy(data,offset,address,0,48);
            t.setAddress(ParseUtil.asciiToStr_l(address));
            offset += 48;

            byte[] businessCode = new byte[18];
            System.arraycopy(data,offset,businessCode,0,18);
            t.setBusinessCode(ParseUtil.asciiToStr_l(businessCode));
            offset += 18;

            byte[] businessType = new byte[48];
            System.arraycopy(data,offset,businessType,0,48);
            t.setBusinessType(ParseUtil.asciiToStr_l(businessType));
            offset += 48;

            byte[] partment = new byte[48];
            System.arraycopy(data,offset,partment,0,48);
            t.setCapture(ParseUtil.asciiToStr_l(partment));
            offset += 48;

            t.setDate(ParseUtil.bcd2Str(data,offset,4));
            offset += 4;

            byte[] cardNumber = new byte[6];
            System.arraycopy(data,offset,cardNumber,0,6);
            t.setCardNumber(ParseUtil.bytesToHexString(cardNumber));
            offset += 6;

            byte[] result = new byte[2];
            System.arraycopy(data,offset,result,0,2);
            String resultStr = ParseUtil.bytesToHexString(result);
            t.setResult(CommonUtils.getResult(resultStr));

        }catch (Exception e){
            e.printStackTrace();
        }

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.GET_DRIVER_EXTRA_INFO)
    public T getDriverExtraInfo(byte[] data, int offset){

        GetDriverExtraInfoO t = new GetDriverExtraInfoO();
        int lastLen = data.length - offset ;
        byte[] content = new byte[lastLen  - 2];
        System.arraycopy(data,offset,content,0,content.length);
        t.setContent(content);
        offset += content.length;

        Logger.getLogger().i("offset : "+ offset);

        byte[] resultBuf = new byte[2];
        System.arraycopy(data,offset,resultBuf,0,2);
        Logger.getLogger().i("resultBuf : "+ParseUtil.bytesToHexString(resultBuf));

        t.setResult(CommonUtils.getResult(ParseUtil.bytesToHexString(resultBuf)));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.GET_CAR_APP_INFO)
    public T getCarAppInfo(byte[] data, int offset){

        GetCarAppInfoO t = new GetCarAppInfoO();
        int lastLen = data.length - offset - 1;
        byte[] content = new byte[lastLen - 3 - 2];
        System.arraycopy(data,offset,content,0,content.length);
        offset += content.length;

        byte[] resultBuf = new byte[2];
        System.arraycopy(data,offset,resultBuf,0,2);
        t.setResult(CommonUtils.getResult(ParseUtil.bytesToHexString(resultBuf)));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.SIGNED_SUCCESSFUL)
    public T workSignSuccess(byte[] data, int offset){

        WorkSignSuccessO t  = new WorkSignSuccessO();

        byte[] businessId = new byte[16];
        System.arraycopy(businessId,0,data,offset,16);
        t.setBusinessId(ParseUtil.asciiToStr_l(businessId));
        offset += 16;

        byte[] certId = new byte[19];
        System.arraycopy(certId,0,data,offset,19);
        t.setCertId(ParseUtil.asciiToStr_l(certId));
        offset += 19;

        byte[] carNumber = new byte[6];
        System.arraycopy(carNumber,0,data,offset,6);
        t.setCarNumber(ParseUtil.asciiToStr_l(carNumber));
        offset += 6;

        t.setSignTime(ParseUtil.bcd2Str(data,offset,6));
        offset += 6;


        byte[] businessNumberBuf = new byte[4];
        System.arraycopy(data,offset,businessNumberBuf,0,4);
        int businessNumber = ParseUtil.byteToInt(ParseUtil.sortToByte(businessNumberBuf),0,4);
        t.setBusinessNumber(businessNumber);
        offset += 4;

        byte result = data[offset];
        t.setResult(result);

        return (T) t;
    }


    @ModelConvert(commId = TMCommand.WORK_SIGNED)
    public T workSign(byte[] data, int offset){

        WorkSignedO t = new WorkSignedO();
        t.setIdenty(data[offset]);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.METER_PARAM_QUERY)
    public T meterParamQuery(byte[] data, int offset){

        MeterParamQueryO t = new MeterParamQueryO();

        t.setParamStartTime(ParseUtil.bcd2Str(data,offset,5));
        offset += 5;
        t.setLightRoundPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setBlackRoundPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setLightOnePrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setBlackOnePrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setLightsecemptyPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setBlackSecEmptyPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setLightStartPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setBlackStartPrice(ParseUtil.bcd2Str(data, offset, 2));
        offset += 2;
        t.setContinueMile(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setStartMile(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setOneMile(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setSecEmptyMile(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setLightWaitPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setBlackWaitPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setFreeWaitTime(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setAddPriceTime(ParseUtil.bcd2Str(data,offset,2));
        offset +=2;
        t.setBlackStartTime(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        t.setBlackEndTime(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;
        byte[] rfu = new byte[22];
        System.arraycopy(data,offset,rfu,0,22);
        t.setRfu(rfu);
        offset += 22;
        byte[] vendorCustomParam = new byte[64];
        System.arraycopy(data,offset,vendorCustomParam,0,64);
        t.setVendorCustomParam(vendorCustomParam);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.Meter_PARAM_SETTING)
    public T meterParamSetting(byte[] data, int offset){

        MeterParamSettingO t = new MeterParamSettingO();
        t.setResult(data[offset++]);

        t.setStartTime(ParseUtil.bcd2Str(data,offset,5));
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.BUSINESS_RECORD_QUERY)
    public T meterRecordQuery(byte[] data, int offset){

        BusinessRecordQueryO t = new BusinessRecordQueryO();

        int lastLen = data.length - offset - 1;
        byte[] content = new byte[lastLen - 3];
        System.arraycopy(data,offset,content,0,content.length);

        t.setContent(content);
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.WORK_SIGNED_OUT)
    public T worksignOut(byte[] data, int offset){

        WorkSignOutO t = new WorkSignOutO();

        t.setSignoutFlag(Integer.valueOf(String.valueOf(data[offset]),16).byteValue());
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.WORK_SIGNED_OUT_NOTIRY)
    public T workSignOutNotify(byte[] data,int offset){

        WorkSignOutNotifyO t = new WorkSignOutNotifyO();

        //经营许可证号
        byte[] businessId = new byte[16];
        System.arraycopy(data,offset,businessId,0,16);
        t.setBusinessId(ParseUtil.ascii2String(businessId));
        offset += 16;

        //从业资格证号
        byte[] certId = new byte[19];
        System.arraycopy(data,offset,certId,0,19);
        t.setCertId(ParseUtil.ascii2String(certId));
        offset += 19;

        //车牌号
        byte[] carNumber = new byte[6];
        System.arraycopy(data,offset,carNumber,0,6);
        t.setCarNumber(ParseUtil.ascii2String(carNumber));
        offset += 6;

        //计价器K值
        t.setkValue(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        //当班签到时间
        t.setSignInTime(ParseUtil.bcd2Str(data,offset,6));
        offset += 6;

        //当班签退时间
        t.setSignOutTime(ParseUtil.bcd2Str(data,offset,6));
        offset += 6;

        //当班里程
        t.setMile(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        //当班营运里程
        t.setBusinessMile(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        //车次
        t.setCarNo(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        //计时时间
        t.setCountTime(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        //总计金额
        t.setTotalMoney(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        //卡收金额
        t.setCardMoney(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        //卡次
        t.setCardNo(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        //班间里程
        t.setWorkMile(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        //总计里程
        t.setTotalMile(ParseUtil.bcd2Str(data,offset,4));
        offset += 4;

        //总运营里程
        t.setTotalBusinessMile(ParseUtil.bcd2Str(data,offset,4));
        offset += 4;

        //单价
        t.setPrice(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        int businessNum = 0;
        try{
            businessNum = Integer.valueOf(String.valueOf(ParseUtil.byteToInt(data,offset,4)),16);
        }catch (Exception e){
            e.printStackTrace();
        }
        t.setTotalBusinessNum(businessNum);

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.SIGN_OUT_DATA_SUPPLEMENT)
    public T signoutSupple(byte[] data, int offset){

        SignOutSuppleO t = new SignOutSuppleO();
        t.setResult(data[offset]);
        return (T) t;
    }

    @ModelConvert(commId = TMCommand.ONCE_BUSINESS_START_NOTIFY)
    public T onceBusinessStart(byte[] data, int offset){

        OnceBusinessStartO t = new OnceBusinessStartO();
        t.setEnterHeavyCarTime(ParseUtil.bcd2Str(data,offset,7));

        return (T) t;
    }

    @ModelConvert(commId = TMCommand.ONCE_BUSINESS_END_NOTIFY)
    public T onceBusinessEnd(byte[] data, int offset){

        OnceBusinessEndO t = new OnceBusinessEndO();

        t.setCarNumber(ParseUtil.byteToString(data,offset,6));
        offset += 6;

        t.setBusinessId(ParseUtil.byteToString(data,offset,16));
        offset += 16;

        t.setCertId(ParseUtil.byteToString(data,offset,19));
        offset += 19;

        t.setOnCarTime(ParseUtil.bcd2Str(data,offset,5));
        offset += 5;

        t.setOffCarTime(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        t.setBusinessMile(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        t.setEmptyMile(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        t.setExtraMoney(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        t.setWaitTime(ParseUtil.bcd2Str(data,offset,2));
        offset += 2;

        t.setTradeMoney(ParseUtil.bcd2Str(data,offset,3));
        offset += 3;

        t.setCurCarNo(ParseUtil.byteToInt(data,offset,4));
        offset += 4;

        t.setTradeType(data[offset++]);


        int lastLen = data.length - offset - 1;

        byte[] tradedata = new byte[lastLen];
        System.arraycopy(data,offset,tradedata,0,lastLen);
        t.setTradeData(tradedata);

        return (T) t;
    }


    @ModelConvert(commId = TMCommand.BUSINESS_DATA_SUPPLE)
    public T businessDataSupple(byte[] data, int offset){

        T t = onceBusinessEnd(data,offset);
        return t;
    }

    public T common(){
        return (T) new DeviceInfo();
    }

}
