package com.nipuream.library.common;

/**
 * Created by yanghui11 on 2020/4/3.
 */

public class DeviceInfo {

    public int deviceType;
    public int vendorId;
    public int commID;

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceType=" + deviceType +
                ", vendorId=" + vendorId +
                ", commID=" + commID +
                '}';
    }
}
