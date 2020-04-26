package com.nipuream.library.common;

/**
 * Created by yanghui11 on 2020/4/2.
 */

public class TMCommand {

    public static final int VENDOR_ID = 0x00;

    /**
     * 计价器空车显示命令
     */
    public static final int EMPTY_CAR_SHOW = 0x9900;

    /**
     * 计价器重车显示命令
     */
    public static final int HEAVY_CAR_SHOW = 0x9901;

    /**
     * 关闭显示
     */
    public static final int CLOSE_SHOW = 0x9903;

    /**
     * 开启显示
     */
    public static final int OPEN_SHOW = 0x9904;

    /**
     * 进入查询界面通知
     */
    public static final int QUERY_LIST_NOTITY = 0x9909;

    /**
     * 退出查询界面
     */
    public static final int BACK_QUERY_NOTITY = 0x990A;

    /**
     * 查询总累计
     */
    public static final int TOTAL_QUERY = 0x9910;

    /**
     * 查询详细客次
     */
    public static final int QUERY_DETAIL_RECORD = 0x9911;

    /**
     * 运价参数设置-获取数据
     */
    public static final int PARAM_QUERY_GET = 0x9912;

    /**
     * 本机参数设置-获取数据
     */
    public static final int LOCAL_PARAM_SET_GET = 0x9913;

    /**
     * 参数设置-设定键通知
     */
    public static final int PARAM_SET_NOTITY = 0x9914;

    /**
     * 运价参数设置-保存
     */
    public static final int PARAM_SET_SAVE = 0x9915;

    /**
     * 本机参数设置-保存
     */
    public static final int LOCAL_PARAM_SET_SAVE = 0x9916;

    /**
     * 计价器时钟查看
     */
    public static final int CLOCK_QUERY = 0x9917;

    /**
     * 计价器时钟设置-保存
     */
    public static final int CLOCK_SET_SAVE = 0x9918;

    /**
     *  计价器时钟设置-整点校时
     */
    public static final int CLOCK_SET_CALIBRATION = 0x9919;

    /**
     *  获取驾驶员基本信息
     */
    public static final int GET_DRIVER_INFO = 0x0020;

    /**
     * 获取驾驶员拓展信息
     */
    public static final int GET_DRIVER_EXTRA_INFO = 0x0021;

    /**
     * 获取出租车应用信息
     */
    public static final int GET_CAR_APP_INFO = 0x0022;

    /**
     *  上班签到  0x00E0
     */
    public static final int WORK_SIGNED = 0x00E0;

    /**
     *  计价器通知ISU签到成功  0x00E1
     */
    public static final int SIGNED_SUCCESSFUL = 0x00E1;

    /**
     *  计价器状态查询
     */
    public static final int METER_QUERY = 0x0000;

    /**
     * 计价器永久时钟误差查询指令
     */
    public static final int METER_CLOCK_QUERY = 0x0001;

    /**
     * 计价器参数查询指令
     */
    public static final int METER_PARAM_QUERY = 0x0004;

    /**
     * 计价器参数设置指令
     */
    public static final int Meter_PARAM_SETTING = 0x0005;

    /**
     *  营运记录查询
     */
    public static final int BUSINESS_RECORD_QUERY = 0x0006;

    /**
     *  下班签退
     */
    public static final int WORK_SIGNED_OUT = 0x00E3;

    /**
     * 签退成功通知
     */
    public static final int WORK_SIGNED_OUT_NOTIRY = 0x00E4;

    /**
     *  签退数据补传
     */
    public static final int SIGN_OUT_DATA_SUPPLEMENT = 0x00F1;

    /**
     * 单次运营开始通知
     */
    public static final int ONCE_BUSINESS_START_NOTIFY = 0x00E7;

    /**
     * 单次运营结束后数据传输
     */
    public static final int ONCE_BUSINESS_END_NOTIFY = 0x00E8;

    /**
     *  营运数据补传
     */
    public static final int BUSINESS_DATA_SUPPLE = 0x00F2;

    /**
     * 远程固件升级指令
     */
    public static final int METER_UPGRADE = 0x00FF;

    /**
     * 计价器心跳包
     */
    public static final int HEART_BEAT = 0x00E9;


}
