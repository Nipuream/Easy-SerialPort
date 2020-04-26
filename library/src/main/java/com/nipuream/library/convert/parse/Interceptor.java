package com.nipuream.library.convert.parse;


import com.nipuream.library.common.DeviceInfo;
import com.nipuream.library.common.DeviceType;
import com.nipuream.library.common.TMCommand;
import com.nipuream.library.fetcher.IWriter;

/**
 * Created by yanghui11 on 2020/4/13.
 *
 *  现在没有过多的逻辑，只有从 DeviceWriter 线程中移除消息
 *  有的消息 设备发送给计价器，当计价器不回应，设备端需要尝试再次发送
 *  当DeviceReader 读到计价器对此消息的回复，只需要将重复发送的消息移除即可
 */
public class Interceptor<T extends DeviceInfo> {

    private IWriter writer ;

    public Interceptor(IWriter writer){
        this.writer = writer;
    }

    void intercept(T t){

        if(t != null && writer != null){
            if(t.deviceType == DeviceType.DEVICE_METER && t.commID == TMCommand.BACK_QUERY_NOTITY){
                writer.removeMsg(t.deviceType,t.commID);
            }
        }
    }

}
