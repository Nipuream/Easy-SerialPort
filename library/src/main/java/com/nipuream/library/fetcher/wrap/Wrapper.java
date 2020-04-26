package com.nipuream.library.fetcher.wrap;



/**
 * Created by yanghui11 on 2020/4/3.
 */
public class Wrapper implements IWrapper {

    @Override
    public byte[] wrap(int vendorIdentify, int deviceType, int command, byte[] data) {
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


    /**
     * 整型数据转换成2个字节,高位在前
     *
     * @param len 整型数据
     * @return 字节数组
     */
    private  byte[] intTo2BytesH(int len) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((len >> 8) & 0xff);
        bytes[1] = (byte) (len & 0xff);
        return bytes;
    }

}
