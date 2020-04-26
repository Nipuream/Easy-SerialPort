package com.nipuream.annotation;

public class PackageRule {

    @Protocol(length = 1,position = 2)
    private byte vendorId;

    @Protocol(length = 2,type = Constants.HighLowBit.HIGHT_BIT_FRONT,position = 3)
    private int commId;
}
