package com.nipuream.library.common;

/**
 * 外设常量定义
 * Created by xufulong5 on 2017-12-12.
 */

public class DeviceConstants {


    public static String HIS_IP = "192.168.42.1";
    //    public static int HIS_PORT = 2888;
    public static int HIS_PORT = 10645;

    public static final String ACTION_SWIPING_STATE = "action.swiping.state";

    //厂商标识
    public static final int VENDOR_IDENTIFY = 41;

    public static String SERVER_IP = "192.168.42.1";//海思ip
    public static int UPLOAD_FILE_PORT = 7981;//文件上传记录仪端端口

    //增加司机信息
    public static final int DATA_TYPE_INSERT = 0;
    //更新司机信息
    public static final int DATA_TYPE_UPDATE = 1;
    //删除司机信息
    public static final int DATA_TYPE_DELETE = 2;

    //人脸注册图片，用于特征提取
    public static final int PIC_TYPE_REGISTER = 100;
    //人脸小图，用于存储与显示
    public static final int PIC_TYPE_THUM = 200;


    /************************** 终端控制命令字-end- *************************/
    /**
     * 计价器状态正常
     */
    public static final int TAXIMETER_NORMAL = 0x00;
    /**
     * 计价器限制使用（次数限制）
     */
    public static final int TAXIMETER_TIMES_LIMITED = 0x01;
    /**
     * 计价器限制使用（日期限制）
     */
    public static final int TAXIMETER_DATE_LIMITED = 0x02;
    /**
     * 计价器营运数据存储满
     */
    public static final int TAXIMETER_OPERATION_STORAGE_EXCEEDED = 0x03;
    /**
     * 计价器上下班签到/签退数据存储满
     */
    public static final int TAXIMETER_SIGN_INOUT_STORAGE_EXCEEDED = 0x04;
    /**
     * 计价器状态异常
     */
    public static final int TAXIMETER_ABNORMAL = 0x05;

    /**
     * 设备类型代码：ISU
     */
    public static final int DEVICE_TYPE_ISU = 0x00;
    /**
     * 设备类型代码：计价器
     */
    public static final int DEVICE_TYPE_TAXIMETER = 0x02;
    /**
     * 厂商标识（举栗子）
     */
    public static final int HIK_COMPANY_IDENTIFY = 0x06;


    /**************************计价器相关命令字-start- ***********************/
    /**
     * 计价器心跳
     */
    public static final int TAXIMETER_HEART_BEAT = 0x00E9;
    /**
     * 计价器状态查询
     */
    public static final int TAXIMETER_QUERY_STATUS = 0x0000;
    /**
     * 计价器运价参数查询
     */
    public static final int TAXIMETER_QUERY_FREIGHT = 0x0004;
    /**
     * 设置计价器运价参数
     */
    public static final int TAXIMETER_SET_FREIGHT = 0x0005;
    /**
     * 单次营运开始通知
     */
    public static final int TAXIMETER_SINGLE_FREIGHT_START = 0x00E7;
    /**
     * 单次营运结束通知
     */
    public static final int TAXIMETER_SINGLE_FREIGHT_STOP = 0x00E8;
    /**
     * 营运数据补传通知
     */
    public static final int TAXIMETER_FREIGHT_RETRASMIT = 0x00F2;
    /**
     * 计价器开机
     */
    public static final int TAXIMETER_BOOTCOMPLETE = 0x00E0;
    /**
     * 计价器开机成功
     */
    public static final int TAXIMETER_BOOTCOMPLETE_SUCCESS = 0x00E1;
    /**
     * 计价器关机
     */
    public static final int TAXIMETER_SHUTDOWN = 0x00E3;
    /**
     * 计价器关机成功
     */
    public static final int TAXIMETER_SHUTDOWN_SUCCESS = 0x00E4;
    /**
     * 关机当次营运数据汇总补传通知
     */
    public static final int TAXIMETER_FREIGHT_GROUP_RETRANSMIT = 0x00F1;
    /**
     * 营运记录查询
     */
    public static final int TAXIMETER_QUERY_FREIGHT_HISTORY = 0x0006;
    /**
     * 计价器永久时钟误差查询
     */
    public static final int TAXIMETER_QUERY_CLOCK_ERROR = 0x0001;
    /**
     * 计价器升级
     */
    public static final int TAXIMETER_UPGRADE_REQUEST = 0x00FF;
    /**
     * 外设升级
     */
    public static final int EXTERNAL_DEV_UPGRADE_REQUEST = 0x00FF;

    /**
     * 设备ftp升级
     */
    public static final int FTP_UPDATE_REQUEST = 0x8BF4;

    /**
     * 设备版本号查询
     */
    public static final int GET_DEVICE_VERSION_REQUEST = 0x8BF0;

    /**
     * 设备版本号查询应答
     */
    public static final int GET_DEVICE_VERSION_RESPONSE = 0x0BF0;

    /********顶灯相关**********/
    //顶灯厂商标识
    public static final int DEVICE_IDENTIFY = 0x01;

    //ISU向顶灯发送查询
    public static final int LIGHT_QUERY = 0x0000;

    //ISU向顶灯发送复位
    public static final int LIGHT_RESET = 0x0001;

    //ISU设置顶灯波特率
    public static final int LIGHT_SET_BUADRATE = 0x0003;

    //ISU向顶灯发送升级
    public static final int LIGHT_UPGRADE = 0x00FF;

    //ISU设置顶灯运营状态
    public static final int LIGHT_OPERATE_STATE = 0x0010;

    //ISU设置顶灯星级状态
    public static final int LIGHT_STAR_STATE = 0x0011;

    //ISU设置顶灯防伪密标
    public static final int LIGHT_SHOW_PASSWORD = 0x0013;

    //ISU取消顶灯防伪密标
    public static final int LIGHT_CANCEL_PASSWORD = 0x0014;

    //ISU设置顶灯夜间工作模式
    public static final int LIGHT_NIGHT_MODE = 0x0012;

    //ISU设置顶灯夜间工作模式参数
    public static final int LIGHT_NIGHT_PARAM = 0x0020;

    /********************************************语音评价器相关*********************************************************/
    //查询设备状态
    public static final int SPEECH_EVALUATOR_QUERY_STATUS = 0x0000;
    //设备复位
    public static final int SPEECH_EVALUATOR_RESET = 0x00EE;
    //设备升级
    public static final int SPEECH_EVALUATOR_UPDATE = 0x00FF;
    //发送车辆状态
    public static final int SPEECH_EVALUATOR_CAR_STATE = 0x00DD;
    //文明用语
    public static final int SPEECH_EVALUATOR_POLITE_LANGUAGE = 0x00A0;
    //服务评价帧
    public static final int SPEECH_EVALUATOR_SERVICE_EVALUATION = 0x00A1;
    //违规判定
    public static final int SPEECH_EVALUATOR_ILLEGAL_DECISION = 0x00A2;

    /*语音评价器升级相关*/

    //升级开始
    public static final byte SPEECH_EVALUATOR_UPDATE_START = 0x03;
    //获取版本信息命令
    public static final byte SPEECH_EVALUATOR_GET_VERSION_INFO = 0x04;
    //升级准备到位
    public static final byte SPEECH_EVALUATOR_UPDATE_READY = 0x05;
    //设置波特率
    public static final byte SPEECH_EVALUATOR_SET_BUAD_RATE = 0x0B;
    //新波特率测试命令
    public static final byte SPEECH_EVALUATOR_BUAD_RATE_TEST = 0x0C;
    //发布升级区域信息
    public static final byte SPEECH_EVALUATOR_UPDATE_AREA_INFO = 0x06;
    //删除区域命令
    public static final byte SPEECH_EVALUATOR_ERASE_AREA = 0x07;
    //烧写数据
    public static final byte SPEECH_EVALUATOR_WRITE_DATA = 0x08;
    //烧写区域结束
    public static final byte SPEECH_EVALUATOR_WRITE_AREA_COMPLETE = 0x09;
    //校验区域数据
    public static final byte SPEECH_EVALUATOR_VERIFY_AREA_DATA = 0x0A;
    //读取数据
    public static final byte SPEECH_EVALUATOR_READ_DATA = 0x0D;
    //校验区域数据
    public static final byte SPEECH_EVALUATOR_UPDATE_COMPLETE = 0x0E;

    /*语音评价器升级状态*/
    
    //成功
    public static final int SPEECH_UPGRADE_RESULT_SUCCESS = 0x00;
    //存储设备故障
    public static final int SPEECH_UPGRADE_RESULT_STORAGE_FAILURE = 0x01;
    //升级文件名不合法
    public static final int SPEECH_UPGRADE_RESULT_FILE_NAME_ILLEGAL = 0x02;
    //已经是最新版本
    public static final int SPEECH_UPGRADE_RESULT_NO_NEED = 0x03;
    //FTP连接失败
    public static final int SPEECH_UPGRADE_RESULT_FTP_CONNECT_FAIL= 0x04;
    //FTP登录失败
    public static final int SPEECH_UPGRADE_RESULT_FTP_LOGIN_FAIL = 0x05;
    //升级文件错误
    public static final int SPEECH_UPGRADE_RESULT_FILE_FAIL = 0x06;
    //FTP服务器错误
    public static final int SPEECH_UPGRADE_RESULT_FTP_SERVER_FAIL = 0x07;
    //升级文件校验失败
    public static final int SPEECH_UPGRADE_RESULT_FILE_VERIFY_FAIL = 0x08;
    //当前有任务正在进行
    public static final int SPEECH_UPGRADE_RESULT_MISSION_IN_PROGRESS = 0x09;
    //未知错误
    public static final int SPEECH_UPGRADE_RESULT_UNKNOW_ERROR = 0x0A;
    //ACC OFF不升级
    public static final int SPEECH_UPGRADE_RESULT_ACC_OFF= 0x0B;
    //FTP获取升级文件错误
    public static final int SPEECH_UPGRADE_RESULT_FTP_GET_FILE_ERROR = 0x0C;
    /********************************************语音评价器相关*********************************************************/


    /**********安全模块相关**********/
    /**
     * 查询设备运行状态
     */
    public static final int QUERY_DEVICE_STATUS = 0x0000;
    /**
     * 设备复位
     */
    public static final int DEVICE_RESET = 0x0001;
    /**
     * 打开射频
     */
    public static final int OPEN_RFID = 0x0015;
    /**
     * 关闭射频
     */
    public static final int CLOSE_RFID = 0x0016;
    /**
     * 获取驾驶员基本信息
     */
    public static final int GET_DRIVER_BASE_INFO = 0x0020;
    /**
     * 获取驾驶员扩展信息
     */
    public static final int GET_DRIVER_EXTENSION_INFO = 0x0021;

    /**
     * 获取驾驶员扩展信息
     */
    public static final int GET_DRIVER_EXTENSION_INFO_1 = 0x1021;
    /**
     * 获取驾驶员扩展信息
     */
    public static final int GET_DRIVER_EXTENSION_INFO_2 = 0x0121;

    public static final int GET_DRIVER_EXTENSION_INFO_EF04 = 0xef04;

    public static final int GET_DRIVER_EXTENSION_INFO_EF05 = 0xef05;

    public static final int GET_DRIVER_EXTENSION_INFO_EF06 = 0xef06;

    public static final int GET_DRIVER_EXTENSION_INFO_EF08 = 0xef08;

    public static final int GET_DRIVER_EXTENSION_INFO_EF0B = 0xef0b;

    /**
     * 获取出租车应用信息
     */
    public static final int GET_TAXI_INFO = 0x0022;
    /**
     * 获取解密报文
     */
    public static final int GET_DECRPT_MESSAGE = 0x0040;

    /**
     * 安全模块升级
     */
    public static final int UPGRADE_REQUEST = 0x00FF;

    /**
     * 顶灯营运状态 报警>停运>载客（重车）>电召>空车
     * 0x00：空车；
     * 0x01：载客；
     * 0x02：停运；
     * 0x03：电召；
     * 0x04：报警；
     * 0x05：显示防伪密标；
     * 0x06：换班
     */
    public final static byte OPERATION_EMPTY = 0x00;
    public final static byte OPERATION_HEAVY = 0x01;
    public final static byte OPERATION_STOP = 0x02;
    public final static byte OPERATION_CALL = 0x03;
    public final static byte OPERATION_ALARM = 0x04;
    public final static byte OPERATION_PASSWORD_SHOW = 0x05;
    public final static byte OPERATION_CHANGE_GUARD = 0x06;

    /**
     * 顶灯星级
     * 0x00：未评定；
     * 0x01：一星；
     * 0x02：二星；
     * 0x03：三星；
     * 0x04：四星；
     * 0x05：五星
     */
    public static final byte STAR_UNKNOW = 0x00;
    public static final byte STAR_ONE = 0x01;
    public static final byte STAR_TWO = 0x02;
    public static final byte STAR_THREE = 0x03;
    public static final byte STAR_FOUR = 0x04;
    public static final byte STAR_FIVE = 0x05;

    /**
     * 计价器状态
     * 0x00：设备正常；
     * 0x01：设备限制使用（次数限制）；
     * 0x02：设备限制使用（日期限制）；
     * 0x04：营运数据存储满；
     * 0x08：上下班签到签退信息满；
     * 其他：设备异常
     */
    public static final byte STATUS_NORMAL = 0x00;
    public static final byte STATUS_DATE_LIMIT = 0x01;
    public static final byte STATUS_TIMES_LIMIT = 0x02;
    public static final byte STATUS_FREIGHT_FULL = 0x04;
    public static final byte STATUS_SIGN_IN_OUT_FULL = 0x08;
    public static final byte STATUS_ABNOMAL = 0x05;

    /**
     * 服务评价
     * 0x00 没有评价
     * 0x01 非常满意
     * 0x02 满意
     * 0x03 不满意
     */
    public static final byte NO_EVALUATION = 0x00;
    public static final byte HIGHLY_SATISFACTION = 0x01;
    public static final byte SATISFACTION = 0x02;
    public static final byte DISSATISFACTION = 0x03;

    /**
     * 全程录音
     */
    public static final int CONTINUE_RECORD = 0x01;
    /**
     * 翻牌录音
     */
    public static final int SWITCH_LOAD_RECORD = 0x02;

    /**********升级相关************/
    //软件版本一致，无需升级
    public static final int UPGRADE_RESULT_NO_NEED = 0;
    //升级成功
    public static final int UPGRADE_RESULT_OK = 1;
    //升级失败
    public static final int UPGRADE_RESULT_ERROR = 2;
    //厂商标识不一致
    public static final int UPGRADE_RESULT_IDENTITY_ERROR = 3;
    //硬件版本不一致
    public static final int UPGRADE_RESULT_VERSION_ERROR = 4;
    //下载升级文件失败
    public static final int UPGRADE_RESULT_DOWNLOAD_ERROR = 5;
    //服务器主动取消升级
    public static final int UPGRADE_RESULT_CENTER_CANCEL = 6;
    //升级文件错误，设备取消升级
    public static final int UPGRADE_RESULT_DEVICE_CANCEL = 7;

    //空车：AA 01 BB 51 54 D5 0B 07 63 01 63 BF D5 B3 B5 FF
    public static final byte[] TO_UNLOAD_STATUS = new byte[]{(byte) 0xAA, 0x01, (byte) 0xBB, 0x51, 0x54, (byte) 0xD5, 0x0B, 0x07, 0x63, 0x01, 0x63
            , (byte) 0xBF, (byte) 0xD5, (byte) 0xB3, (byte) 0xB5, (byte) 0xFF};

}
