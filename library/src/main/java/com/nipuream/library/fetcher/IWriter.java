package com.nipuream.library.fetcher;


import com.nipuream.library.common.WriterInfo;

/**
 * Created by yanghui11 on 2020/4/3.
 */

public interface IWriter {

    /**
     * 向外设写入数据
     * @param info
     */
    void writeMsg(WriterInfo info);

    /**
     * 往外设写裸数据
     * @param buf
     */
    void writeBuffer(byte[] buf);

    /**
     *  移除消息数据
     * @param devceType
     * @param commId
     */
    void removeMsg(int devceType, int commId);

    /**
     * 停止运行
     */
    void quitQuit();

}
