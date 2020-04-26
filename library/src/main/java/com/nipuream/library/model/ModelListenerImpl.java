package com.nipuream.library.model;

import com.hikvision.devicemodule.DeviceInfo;
import com.hikvision.devicemodule.outter.BusinessParamQueryO;
import com.hikvision.devicemodule.outter.BusinessRecordQueryO;
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

/**
 * Created by yanghui11 on 2020/4/7.
 *
 *  空实现，业务层使用此类实例监听通信层的回复
 *  这样的好处是
 *  1. 添加接口，可以避免业务层编译报错
 *  2. 业务层根据需要重写方法，不需要实现所有的接口。
 *  3. 可以执行一些过滤操作
 */
public class ModelListenerImpl implements IModelListener{

    @Override
    public void heartBeat(HeartBeatO info) {

    }

    @Override
    public void emptyCarShow(EmptyCarO carInfo) {

    }

    @Override
    public void heavyCarShow(HeavyCarShowO info) {

    }

    @Override
    public void closeShow(DeviceInfo deviceInfo) {

    }

    @Override
    public void openShow(DeviceInfo info) {

    }

    @Override
    public void queryListNotify(QueryListNotifyO info) {

    }

    @Override
    public void exitQueryList(ExitQueryListO info) {

    }

    @Override
    public void queryTotalBusiness(QueryTotalO info) {

    }

    @Override
    public void queryDetailBusiness(QueryDetailBusinessO info) {

    }

    @Override
    public void businessParamQuery(BusinessParamQueryO info) {

    }

    @Override
    public void localParamQuery(LocalParamQueryO info) {

    }

    @Override
    public void paramSetting(ParamSettingO info) {

    }

    @Override
    public void freightParamSetting(FreightParamSettingO info) {

    }

    @Override
    public void localParamSetting(LocalParamSettingO info) {

    }

    @Override
    public void txClockLook(TxClockLookO info) {

    }

    @Override
    public void txClockSetting(TxClockSettingO info) {

    }

    @Override
    public void txClockSettingA(TxClockSettingAO info) {

    }

    @Override
    public void getDriverInfo(GetDriverInfoO info) {

    }

    @Override
    public void getDriverExtraInfo(GetDriverExtraInfoO info) {

    }

    @Override
    public void getCarAppInfo(GetCarAppInfoO info) {

    }

    @Override
    public void worksign(WorkSignedO info) {

    }

    @Override
    public void worksignSuccess(WorkSignSuccessO info) {

    }

    @Override
    public void meterParamQuery(MeterParamQueryO info) {

    }

    @Override
    public void meterParamSetting(MeterParamSettingO info) {

    }

    @Override
    public void businessRecordQuery(BusinessRecordQueryO info) {

    }

    @Override
    public void workSignOut(WorkSignOutO info) {

    }

    @Override
    public void workSignOutNotify(WorkSignOutNotifyO info) {

    }

    @Override
    public void signOutSupple(SignOutSuppleO info) {

    }

    @Override
    public void onceBusinessStart(OnceBusinessStartO info) {

    }

    @Override
    public void onceBusinessEnd(OnceBusinessEndO info) {

    }

    @Override
    public void businessDataSupple(OnceBusinessEndO info) {

    }

    @Override
    public void meterUpgrade(MeterUpgradeO info) {

    }

    @Override
    public void meterQuery(MeterQueryO info) {

    }

}
