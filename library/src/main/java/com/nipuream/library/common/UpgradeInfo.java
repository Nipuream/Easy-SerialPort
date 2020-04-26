package com.nipuream.library.common;

/**
 * Created by yanghui11 on 2020/4/9.
 */

public class UpgradeInfo {

    private byte vendorId ;
    private byte hardwareVer;
    private byte softwareVer;
    private byte softwareSubVer;

    public UpgradeInfo(byte vendorId, byte hardwareVer, byte softwareVer, byte softwareSubVer) {
        this.vendorId = vendorId;
        this.hardwareVer = hardwareVer;
        this.softwareVer = softwareVer;
        this.softwareSubVer = softwareSubVer;
    }

    public byte getVendorId() {
        return vendorId;
    }

    public void setVendorId(byte vendorId) {
        this.vendorId = vendorId;
    }

    public byte getHardwareVer() {
        return hardwareVer;
    }

    public void setHardwareVer(byte hardwareVer) {
        this.hardwareVer = hardwareVer;
    }

    public byte getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(byte softwareVer) {
        this.softwareVer = softwareVer;
    }

    public byte getSoftwareSubVer() {
        return softwareSubVer;
    }

    public void setSoftwareSubVer(byte softwareSubVer) {
        this.softwareSubVer = softwareSubVer;
    }

    @Override
    public String toString() {
        return "UpgradeInfo{" +
                "vendorId=" + vendorId +
                ", hardwareVer=" + hardwareVer +
                ", softwareVer=" + softwareVer +
                ", softwareSubVer=" + softwareSubVer +
                '}';
    }
}
