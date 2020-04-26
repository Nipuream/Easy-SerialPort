package com.nipuream.library.model;


import com.nipuream.library.common.ModelConvert;

/**
 * Created by yanghui11 on 2020/4/3.
 */
public interface IModelListener {

     /**
      * 计价器心跳指令 0x00E9
      * @param info
      */
     @ModelConvert(commId = TMCommand.HEART_BEAT)
     void heartBeat(HeartBeatO info) ;

     /**
      * 计价器空车显示命令 0x9900
      *  安卓板收到命令后立即刷新显示，不需要给计价器返回应答
      *  安卓开机收到空车指令自检5秒钟
      * @param carInfo
      */
     @ModelConvert(commId = TMCommand.EMPTY_CAR_SHOW)
     void emptyCarShow(EmptyCarO carInfo);

     /**
      * 计价器重车显示命令 0x9901
      *  安卓板收到该命令，立即刷新显示，不需要给计价器回复
      *  重车下不能开机自检，不能查询，按下IC键后显示K值，再次按下IC键不显示K值
      * @param info
      */
     @ModelConvert(commId = TMCommand.HEAVY_CAR_SHOW)
     void heavyCarShow(HeavyCarShowO info);

     /**
      *  关闭显示  0x9903
      *  安卓板收到该命令后，立即黑屏，同事要给计价器应答
      * @param deviceInfo
      */
     @ModelConvert(commId = TMCommand.CLOSE_SHOW)
     void closeShow(DeviceInfo deviceInfo);

     /**
      * 开启显示 0x9904
      * 安卓板收到该命令后，立即开启屏幕，同时给计价器应答
      */
     @ModelConvert(commId = TMCommand.OPEN_SHOW)
     void openShow(DeviceInfo deviceInfo);

     /**
      *  进入查询界面通知 0x9909
      */
     @ModelConvert(commId = TMCommand.QUERY_LIST_NOTITY)
     void queryListNotify(QueryListNotifyO info);

     /**
      * 退出查询界面  0x990A
      * @param info
      */
     @ModelConvert(commId = TMCommand.BACK_QUERY_NOTITY)
     void exitQueryList(ExitQueryListO info);

     /**
      * 查询总累计  0x9910
      * @param info
      */
     @ModelConvert(commId = TMCommand.TOTAL_QUERY)
     void queryTotalBusiness(QueryTotalO info);

     /**
      * 查询详细客次 0x9911
      * @param info
      */
     @ModelConvert(commId = TMCommand.QUERY_DETAIL_RECORD)
     void queryDetailBusiness(QueryDetailBusinessO info);

     /**
      *  运价参数设置->获取数据0x9912
      * @param info
      */
     @ModelConvert(commId = TMCommand.PARAM_QUERY_GET)
     void businessParamQuery(BusinessParamQueryO info);

     /**
      * 本机参数设置->获取数据 0x9913
      * @param info
      */
     @ModelConvert(commId = TMCommand.LOCAL_PARAM_SET_GET)
     void localParamQuery(LocalParamQueryO info);

     /**
      *  参数设置->设定键通知 0x9914
      * @param info
      */
     @ModelConvert(commId = TMCommand.PARAM_SET_NOTITY)
     void paramSetting(ParamSettingO info);

     /**
      *  运价参数设置->保存 0x9915
      * @param info
      */
     @ModelConvert(commId = TMCommand.PARAM_SET_SAVE)
     void freightParamSetting(FreightParamSettingO info);

     /**
      *  本机参数设置->保存 0x9916
      * @param info
      */
     @ModelConvert(commId = TMCommand.LOCAL_PARAM_SET_SAVE)
     void localParamSetting(LocalParamSettingO info);

     /**
      *  计价器时钟查看-> 0x9917
      * @param info
      */
     @ModelConvert(commId = TMCommand.CLOCK_QUERY)
     void txClockLook(TxClockLookO info);

     /**
      *  计价器时钟设置->保存 0x9918
      * @param info
      */
     @ModelConvert(commId = TMCommand.CLOCK_SET_SAVE)
     void txClockSetting(TxClockSettingO info);

     /**
      * 计价器时钟设置-> 整点校时
      * @param info
      */
     @ModelConvert(commId = TMCommand.CLOCK_SET_CALIBRATION)
     void txClockSettingA(TxClockSettingAO info);

     /**
      *  获取驾驶员基本信息 0x0020
      * @param info
      */
     @ModelConvert(commId = TMCommand.GET_DRIVER_INFO)
     void getDriverInfo(GetDriverInfoO info);

     /**
      * 获取驾驶员拓展信息 0x0021
      * @param info
      */
     @ModelConvert(commId = TMCommand.GET_DRIVER_EXTRA_INFO)
     void getDriverExtraInfo(GetDriverExtraInfoO info);

     /**
      * 获取出租车应用信息 0x0022
      * @param info
      */
     @ModelConvert(commId = TMCommand.GET_CAR_APP_INFO)
     void getCarAppInfo(GetCarAppInfoO info);

     /**
      *  上班签到，按IC键触发  0x00E0
      * @param info
      */
     @ModelConvert(commId =  TMCommand.WORK_SIGNED)
     void worksign(WorkSignedO info);

     /**
      * 签到成功，计价器通知  0x00E1
      * @param info
      */
     @ModelConvert(commId = TMCommand.SIGNED_SUCCESSFUL)
     void worksignSuccess(WorkSignSuccessO info);

     /**
      * 计价器参数查询  0x0004
      * @param info
      */
     @ModelConvert(commId = TMCommand.METER_PARAM_QUERY)
     void meterParamQuery(MeterParamQueryO info);

     /**
      *  计价器参数设置指令 0x0005
      * @param info
      */
     @ModelConvert(commId = TMCommand.Meter_PARAM_SETTING)
     void meterParamSetting(MeterParamSettingO info);

     /**
      *  运营记录查询指令 0x0006
      * @param info
      */
     @ModelConvert(commId = TMCommand.BUSINESS_RECORD_QUERY)
     void businessRecordQuery(BusinessRecordQueryO info);

     /**
      *  下班签退 通知 0x00E3
      * @param info
      */
     @ModelConvert(commId = TMCommand.WORK_SIGNED_OUT)
     void workSignOut(WorkSignOutO info);

     /**
      *  签退成功 通知  0x00E4
      * @param info
      */
     @ModelConvert(commId = TMCommand.WORK_SIGNED_OUT_NOTIRY)
     void workSignOutNotify(WorkSignOutNotifyO info);

     /**
      * 签退数据补传 0x00F1
      * @param info
      */
     @ModelConvert(commId = TMCommand.SIGN_OUT_DATA_SUPPLEMENT)
     void signOutSupple(SignOutSuppleO info);

     /**
      * 单次运营开始通知 0x00E7
      * @param info
      */
     @ModelConvert(commId = TMCommand.ONCE_BUSINESS_START_NOTIFY)
     void onceBusinessStart(OnceBusinessStartO info);

     /**
      *  单次运营结束后数据传输 0x00E8
      * @param info
      */
     @ModelConvert(commId = TMCommand.ONCE_BUSINESS_END_NOTIFY)
     void onceBusinessEnd(OnceBusinessEndO info);

     /**
      * 营运数据补传 0x00F2
      *  计价器营运数据 按“单次营运结束后”的数据区定义内容
      * @param info
      */
     @ModelConvert(commId = TMCommand.BUSINESS_DATA_SUPPLE)
     void businessDataSupple(OnceBusinessEndO info);

     /**
      *  远程固件升级指令
      * @param info
      */
     @ModelConvert(commId = TMCommand.METER_UPGRADE)
     void meterUpgrade(MeterUpgradeO info);

     /**
      * 计价器状态查询
      * @param info
      */
     @ModelConvert(commId = TMCommand.METER_QUERY)
     void meterQuery(MeterQueryO info);



}
