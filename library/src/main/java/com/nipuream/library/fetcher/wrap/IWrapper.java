package com.nipuream.library.fetcher.wrap;


/**
 * Created by yanghui11 on 2020/4/3.
 */
public interface IWrapper {


    /**
     * 将数据组装成 外设需要的数据
     * @param vendorId
     * @param deviceType
     * @param commonID
     * @param data
     */
    byte[] wrap(int vendorId, int deviceType, int commonID, byte[] data);

}
