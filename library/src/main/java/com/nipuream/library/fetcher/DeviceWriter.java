package com.nipuream.library.fetcher;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.nipuream.library.common.TMCommand;
import com.nipuream.library.common.WriterInfo;
import com.nipuream.library.fetcher.control.Controller;
import com.nipuream.library.fetcher.control.IController;
import com.nipuream.library.fetcher.io.Io;
import com.nipuream.library.fetcher.wrap.IWrapper;
import com.nipuream.library.fetcher.wrap.Wrapper;
import com.nipuream.library.inner.Watchdog;
import com.nipuream.library.utils.Logger;
import com.nipuream.library.utils.ParseUtil;

import java.io.IOException;

/**
 * Created by yanghui11 on 2020/4/1.
 *
 */
public class DeviceWriter extends HandlerThread implements IWriter, Watchdog.Monitor{

    private static final String TAG = DeviceWriter.class.getName();
    private Handler handler ;
    private IWrapper wrapper;
    private IController controller;
    private volatile int acquires = 0;
    private Io io;

    public class MSG_ID {

        //发送消息，buf需要外壳封装
        public  static final int WRITE_MSG = 1;
        //往串口写裸数据
        public static final int WRITE_BUFFER = 2;
        //检测堵塞
        public static final int CHECK_BLOCK = 3;
    }

    public DeviceWriter(Io io) {
        super(TAG);
        this.io = io;
        wrapper = new Wrapper();
        controller = new Controller(this);
        Watchdog.getInstance().addMonitor(this);
    }


    @Override
    public synchronized void start() {
        super.start();
        handler = new Handler(getLooper(),callback);
        handler.sendMessageDelayed(handler.obtainMessage(MSG_ID.CHECK_BLOCK), 1000 * 10);
    }

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            acquires ++;
            final int msgID = msg.what;
            if(msgID == MSG_ID.WRITE_MSG){

                WriterInfo writerInfo = (WriterInfo) msg.obj;
                if(writerInfo != null){

                    byte[] data = wrapper.wrap(TMCommand.VENDOR_ID,writerInfo.getDeviceType(),writerInfo.getCommandID(),writerInfo.getSendMsg());
                    if(io != null){
                        try {
                            Logger.getLogger().i("write data :  "+ ParseUtil.bytesToHexString(data));
                            io.write(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {

                            if(controller != null){
                                controller.retransControl(writerInfo);
                            }
                        }
                    }
                }
            } else if(msgID == MSG_ID.WRITE_BUFFER){

                byte[] buf = (byte[]) msg.obj;

                if(io != null){
                    Logger.getLogger().i("write buffer : "+ParseUtil.bytesToHexString(buf));
                    try {
                        io.write(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if(msgID == MSG_ID.CHECK_BLOCK){
                acquires ++;
                Message checkMsg = handler.obtainMessage(MSG_ID.CHECK_BLOCK);
                handler.sendMessageDelayed(checkMsg,1000 * 10);
            }
            return false;
        }
    };

    @Override
    public void writeMsg(WriterInfo info){

        if(info == null){
            Logger.getLogger().i("write msg is null.");
            return ;
        }

        int delayTime = info.getDelayTime() * 1000;
        Message msg = handler.obtainMessage(MSG_ID.WRITE_MSG,info);
        handler.sendMessageDelayed(msg,delayTime);
    }

    @Override
    public void writeBuffer(byte[] buf) {

        if(buf == null){
            Logger.getLogger().i("write buffer is null.");
            return ;
        }

        handler.obtainMessage(MSG_ID.WRITE_BUFFER,buf).sendToTarget();
    }

    @Override
    public void removeMsg(int deviceType, int commId){

        Logger.getLogger().i("remove msg  , deviceType : "+ deviceType
                + ", commandID : "+ commId);

        if(controller != null){
            WriterInfo writerInfo = controller.decWriterInfo(deviceType,commId);
            if(writerInfo != null){
                handler.removeMessages(MSG_ID.WRITE_MSG, writerInfo);
            }
        }
    }

    @Override
    public void quitQuit() {
        boolean quitResult = quitSafely();
        Logger.getLogger().i("quit result : "+ quitResult);
    }

    @Override
    public int monitor() {

        int oldValue = acquires;
        acquires = 0;
        return oldValue;
    }

}
