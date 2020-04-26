package com.nipuream.library;


import com.nipuream.library.model.IModelListener;
import com.nipuream.library.model.IModelWriter;

/**
 * Created by yanghui11 on 2020/4/9.
 *
 *  提供给外部使用的接口类
 */
public interface IDeviceFactory {

    /**
     *  主动调用业务接口
     * @return
     */
    IModelWriter writer();

    /**
     * 关闭串口
     */
    void close();

    /**
     * 初始化串口、初始化相关变量
     */
    void init();

    /**
     * 注册业务回调接口
     * @param l
     */
    void register(IModelListener l);

    /**
     * 解绑接口
     */
    void remove();


}
