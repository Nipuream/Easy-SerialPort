package com.nipuream.library.common;

import java.util.Arrays;

/**
 * Created by yanghui11 on 2020/4/1.
 *
 *  往外设写的数据封装
 */
public class WriterInfo {

    /**
     * 设备类型
     */
    private int deviceType;
    /**
     * 命令字
     */
    private int commandID;
    /**
     * 发送字节
     */
    protected byte[] sendMsg;

    /**
     * 是否需要重试
     */
    private boolean needTry;

    /**
     * 重发次数
     */
    private int tryTimes ;

    /**
     * 延迟几秒发送，单位: s
     * 默认立即发送
     */
    private int delayTime;

    public WriterInfo(int deviceType, int commandID, byte[] sendMsg, boolean needTry) {
        this.deviceType = deviceType;
        this.commandID = commandID;
        this.sendMsg = sendMsg;
        this.needTry = needTry;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getCommandID() {
        return commandID;
    }

    public byte[] getSendMsg() {
        return sendMsg;
    }

    public boolean isNeedTry() {
        return needTry;
    }

    public int getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(int tryTimes) {
        this.tryTimes = tryTimes;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public boolean equals(Object obj) {

        //如果 deviceType 和 CommandID 相同就认为是同种消息
        WriterInfo info = (WriterInfo) obj;
        if(this.deviceType == info.deviceType &&
                this.commandID == info.commandID){
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {

        int result = sendMsg.hashCode();
        result = 31 * result + deviceType;
        result = 31 * result + commandID;
        result = 31 * result + (needTry ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WriterInfo{" +
                "deviceType=" + deviceType +
                ", commandID=" + commandID +
                ", sendMsg=" + Arrays.toString(sendMsg) +
                ", needTry=" + needTry +
                '}';
    }
}
