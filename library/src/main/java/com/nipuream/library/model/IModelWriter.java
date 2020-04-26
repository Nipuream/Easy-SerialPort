package com.nipuream.library.model;

import com.hikvision.communication.exception.ParamNullException;
import com.hikvision.communication.exception.UpgradeException;
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

/**
 * Created by yanghui11 on 2020/4/7.
 *
 */
public interface IModelWriter {

    /**
     *  设备回复计价器数据
     * @param info
     */
    void openShowResponse(OpenShowI info) throws ParamNullException,UpgradeException;

    /**
     * 计价器永久时钟误差查询
     * @param info
     */
    void meterClockQuery(ClockQueryI info) throws ParamNullException,UpgradeException;

    /**
     *  远程固件升级指令
     * @param info
     */
    void meterUpgrade(MeterUpgradeI info)throws ParamNullException,UpgradeException;

    /**
     * 计价器状态查询
     * @param info
     */
    void meterQuery(MeterQueryI info) throws ParamNullException,UpgradeException;

    /**
     * 计价器心跳指令 0x00E9
     *  业务层开启线程给计价器回复心跳，可以控制计价器的次数限制
     * @param info
     */
    void heartBeat(HeartBeatI info) throws ParamNullException,UpgradeException;

    /**
     *  进入查询界面通知  0x9909
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void queryListNotify(QueryListNotifyI info) throws ParamNullException,UpgradeException;

    /**
     *  退出查询界面  0x990A
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void exitQueryList(ExitQueryListI info) throws ParamNullException, UpgradeException;

    /**
     * 查询详细客次  0x9911
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void queryTotalRecord(DeviceInfo info) throws ParamNullException,UpgradeException;

    /**
     * 查询详细客次 0x9911
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void queryDetailBusiness(QueryDetailBusinessI info) throws ParamNullException, UpgradeException;

    /**
     * 运价参数设置-> 获取数据 0x9912
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void businessParamQuery(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     * 本机参数设置-> 获取数据 0x9913
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void localParamQuery(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     * 参数设置-> 设定键通知 0x9914
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void paramSetting(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     * 运价参数设置-> 保存 0x9915
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void freightParamSetting(FreightParamSettingI info) throws ParamNullException, UpgradeException;

    /**
     * 本地参数设置->保存 0x9916
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void localParamSetting(LocalParamSettingI info) throws ParamNullException, UpgradeException;

    /**
     * 计价器时钟查看 0x9917
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void txClockLook(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     * 计价器时钟设置->保存 0x9918
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void txClockSetting(TxClockSettingI info) throws ParamNullException, UpgradeException;

    /**
     * 计价器时钟设置->整点校时 0x9919
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void txClockSettingA(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     *  获取驾驶员基本信息 0x0020
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void getDriverInfo(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     *  获取驾驶员拓展信息 0x0021
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void getDriverExtraInfo(GetDriverExtraInfoI info) throws ParamNullException, UpgradeException;

    /**
     *  获取出租车应用信息 0x0022
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void getDriverAppInfo(GetCarAppInfoI info) throws ParamNullException, UpgradeException;

    /**
     *  上班签到回复 0x00E0
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void workSignResponse(WorkSignedI info) throws ParamNullException, UpgradeException;

    /**
     *  计价器参数查询指令 0x0004
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void meterParamQuery(DeviceInfo info) throws ParamNullException, UpgradeException;

    /**
     * 计价器参数设置指令 0x0005
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void meterParamSetting(MeterParamSettingI info) throws ParamNullException, UpgradeException;

    /**
     * 运营记录查询指令 0x0006
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void businessRecordQuery(BusinessRecordQueryI info) throws ParamNullException, UpgradeException;


    /**
     *  下班签退 0x00E3
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void workSignOut(WorkSignOutI info) throws ParamNullException, UpgradeException;

    /**
     * 签退成功通知  0x00E4
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void workSignOutNotify(WorkSignOutNotifyI info) throws ParamNullException, UpgradeException;

    /**
     *  签退数据补传 0x00F1
     *  参数按照下班签退的数据区定义内容
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void workSignOutSupple(WorkSignOutI info) throws ParamNullException,UpgradeException;

    /**
     * 单次运营开始通知  0x00E7
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void onceBusinessStart(OnceBusinessStartI info) throws ParamNullException, UpgradeException;

    /**
     * 单次运营结束数据传输 0x00E8
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void onceBusinessEnd(OnceBusinessEndI info) throws ParamNullException, UpgradeException;

    /**
     *  运营数据补传 0x00F2
     *  计价器运营数据  按“单次营运结束后”的数据区定义内容
     * @param info
     * @throws ParamNullException
     * @throws UpgradeException
     */
    void businessDataSupply(OnceBusinessEndI info) throws ParamNullException, UpgradeException;

}
