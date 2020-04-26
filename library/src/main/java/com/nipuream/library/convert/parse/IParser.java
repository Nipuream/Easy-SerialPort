package com.nipuream.library.convert.parse;


import com.nipuream.library.common.DeviceInfo;
import com.nipuream.library.model.IModelListener;

/**
 * Created by yanghui11 on 2020/4/2.
 *
 *  解析类通用接口，目的是将二进制的串口报文数据生成业务对象
 *  然后在通过线程池对外界回调接口 {@link IModelListener} 去分发
 *  逻辑层获取到业务代码再执行相应的业务逻辑。
 */
public interface IParser<T extends DeviceInfo> {

    T parse(byte[] data);

}
