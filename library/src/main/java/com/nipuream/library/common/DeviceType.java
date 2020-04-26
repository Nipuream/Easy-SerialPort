package com.nipuream.library.common;

/**
 * 定义所有外设设备类型
 */
public class DeviceType {
    /**
     * ISU
     */
    public static final byte DEVICE_ISU = 0x00;
    /**
     * 通讯模块
     */
    public static final byte DEVICE_CONNECTION = 0x01;
    /**
     * 计价器
     */
    public static final byte DEVICE_METER = 0x02;
    /**
     * 出租汽车安全模块
     */
    public static final byte DEVICE_SECURITY = 0x03;
    /**
     * LED显示屏
     */
    public static final byte DEVICE_LED = 0x04;
    /**
     * 智能顶灯
     */
    public static final byte DEVICE_TOP_LIGHT = 0x05;
    /**
     * 服务评价器（后排）
     */
    public static final byte DEVICE_EVALUATOR_BACK = 0x06;
    /**
     * 摄像头
     */
    public static final byte DEVICE_CAMERA = 0x07;
    /**
     * 卫星定位设备
     */
    public static final byte DEVICE_LOCATION = 0x08;
    /**
     * 液晶（LCD）多媒体屏
     */
    public static final byte DEVICE_LCD = 0x09;
    /**
     * ISU人机交互设备
     */
    public static final byte DEVICE_GUI = 0x10;
    /**
     * 服务评价器（前排)
     */
    public static final byte DEVICE_EVALUATOR_FRONT = 0x11;
    /**
     * 语音评价器
     */
    public static final byte DEVICE_SPEECH_EVALUATOR = 0x12;
    /**
     * 与海思通信心跳
     */
    public static final byte DEVICE_HEART_BEAT_HIS = (byte) 0xff;
    /**
     * 计价器升级标识
     */
    public static final int TAXI_METER_UPGRADE = 0x00f2;
    /**
     * 安全模块升级标识
     */
    public static final int SECURE_MODULE_UPGRADE = 0x00f3;
    /**
     * 顶灯模块升级标识
     */
    public static final int TOP_LIGHT_UPGRADE = 0x00f5;
    /**
     * 与海思通信心跳
     */
    public static final int DEVICE_NEW_HEART_BEAT_HIS = 0xfeef;
}
