package com.nipuream.library.inner;


import com.hikvision.ae.serialport.SerialPort;
import com.nipuream.library.IDeviceFactory;
import com.nipuream.library.convert.upgrade.IUpgradeHandle;
import com.nipuream.library.fetcher.IWriter;
import com.nipuream.library.fetcher.io.Io;
import com.nipuream.library.model.IModelListener;

import java.util.concurrent.ExecutorService;

/**
 * Created by yanghui11 on 2020/4/9.
 *
 *  提供给module 内部使用的 接口类
 */
public interface InnerFactory extends IDeviceFactory {

    /**
     * 业务回调接口
     * @return
     */
    IModelListener listener();

    /**
     *  提供线程池对象
     * @return
     */
    ExecutorService executor();

    /**
     * @return
     */
    IWriter writer_l();

    /**
     * 获取串口实例对象
     * @return
     */
    SerialPort serialPort();

    /**
     * 获取io
     * @return
     */
    Io io();

    /**
     * 升级相关
     * @return
     */
    IUpgradeHandle upgrade();

}
