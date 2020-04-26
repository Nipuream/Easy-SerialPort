package com.nipuream.library.common;


/**
 * Created by yanghui11 on 2020/4/2.
 */

public class CommonUtils {

    /**
     * @param deviceType 设备类型
     * @param command    命令字
     * @param data       数据体
     * @return 功能+内容
     * 起始位 结束位 0x55 0xAA
     * 起始位  |  包长度  |  设备类型  |  厂商标识  |  命令字  |  数据区  |  校验码  |  结束位
     * 2byte  |  2byte  |  1byte    |  1byte    |  2byte |  N bytes |  1byte  |  2byte
     */
    public static byte[] getRequest(int vendorIdentify, int deviceType, int command, byte[] data) {
        int length;
        if (data == null) {
            length = 0;
        } else {
            length = data.length;
        }
        byte[] request = new byte[2 + 2 + 1 + 1 + 2 + 1 + 2 + length];
        request[0] = (byte) 0x55;
        request[1] = (byte) 0xAA;
        System.arraycopy(intTo2BytesH(1 + 1 + 2 + length), 0, request, 2, 2);
        request[4] = (byte) (deviceType & 0xff);
        request[5] = (byte) (vendorIdentify & 0xff);
        //默认标准命令字
        System.arraycopy(intTo2BytesH(command), 0, request, 6, 2);
        if (data != null) {
            System.arraycopy(data, 0, request, 8, length);
        }
        int checksum = request[2] ^ request[3];
        for (int i = 4; i < 8 + length; i++) {
            checksum = checksum ^ request[i];
        }
        request[request.length - 3] = (byte) (checksum & 0xff);
        request[request.length - 2] = (byte) 0x55;
        request[request.length - 1] = (byte) 0xAA;
        return request;
    }


    public static int getResult(String hexValue){

        int result = 5;

        switch (hexValue){
            case "9000":
                result = 0;
                break;
            case "FF0E":
                result = 1;
                break;
            case "FF0F":
                result = 2;
                break;
            case "FF10":
                result = 3;
                break;
            case "FF15":
                result = 4;
                break;
            case "FF16":
                result = 5;
                break;
            default:
                break;
        }

        return result;
    }


    /**
     * 整型数据转换成2个字节,高位在前
     *
     * @param len 整型数据
     * @return 字节数组
     */
    private static byte[] intTo2BytesH(int len) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((len >> 8) & 0xff);
        bytes[1] = (byte) (len & 0xff);
        return bytes;
    }

}
