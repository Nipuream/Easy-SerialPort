package com.nipuream.library.fetcher.control;

import com.nipuream.library.common.WriterInfo;
import com.nipuream.library.fetcher.IWriter;
import com.nipuream.library.utils.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by yanghui11 on 2020/4/11.
 *
 */
public class Controller implements IController{

    private final Set<WriterInfo> refuseMsg = new HashSet<>();
    private IWriter writer;

    public Controller(IWriter writer){
        this.writer = writer;
    }

    @Override
    public void retransControl(WriterInfo writerInfo) {

        if(writerInfo.isNeedTry()){

            int tryTimes = writerInfo.getTryTimes();
            if(tryTimes > 0){

                writerInfo.setTryTimes(-- tryTimes);
                Logger.getLogger().w("******** Warn ********** writeInfo : "+writerInfo.toString()+"After 5 second  will send.");
                refuseMsg.add(writerInfo);
                Logger.getLogger().i("*********  Warn ********* refuseMsg size : "+ refuseMsg.size());

                writerInfo.setDelayTime(5);
                if(writer != null){
                    writer.writeMsg(writerInfo);
                }
            } else {

                Logger.getLogger().i("********** Warn ********** need retry writerInfo : "+writerInfo.toString() + ", send times have use up...");
                refuseMsg.remove(writerInfo);
            }
        }
    }

    @Override
    public WriterInfo decWriterInfo(int deviceType, int commId) {

        WriterInfo writerInfo = null;
        Iterator<WriterInfo> it = refuseMsg.iterator();
        while (it.hasNext()){
            writerInfo = it.next();
            if(writerInfo.getDeviceType() == deviceType && writerInfo.getCommandID() == commId){
                break;
            }
        }

        if(writerInfo != null){
            refuseMsg.remove(writerInfo);
        }

        return writerInfo;
    }


}
