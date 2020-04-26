package com.nipuream.library.fetcher;

import com.nipuream.library.inner.BytePool;
import com.nipuream.library.inner.InnerFactory;
import com.nipuream.library.utils.Logger;
import com.nipuream.library.utils.ParseUtil;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yanghui11 on 2020/4/21.
 *
 *  将读出的数据放入缓存区，同时还有对粘包、断包的处理
 */
public class CacheChain {

    private InnerFactory device;
    private BlockingQueue<byte[]> queue;
    private ReadData reader = new ReadData();
    private byte[] specialCommandArray = new byte[]{0x01, 0x04, 0x06, 0x15, 0x1a, 0x18, 0x43};

    private boolean debug = false;

    public class HEADER_STATE {

        static final int NOT_FIND_HEADER = 0x01;
        static final int CUR_PACKAGE_FIND_HEADER = 0x02;
        static final int LAST_PACKAGE_FIND_HEADER = 0x03;
    }

    public CacheChain(InnerFactory device){
        this.device = device;
        queue = new LinkedBlockingQueue<>(50);
    }

    public void start(){

        device.executor().execute(reader);
    }

    public byte[] read() throws InterruptedException{

        return queue.take();
    }

    public void stop(){
        reader.setStop(true);
    }

    private class ReadData implements Runnable{

        private boolean isStop = false;

        public void setStop(boolean isStop){
            this.isStop = isStop;
        }

        @Override
        public void run() {

            if(debug){
                Logger.getLogger().i("start running ....  thread name :  "+Thread.currentThread().getName());
            }

//            byte[] buf = new byte[1024]; //read buf.
            byte[] tmp = null; //tmp buf.
            int readLen = 0;


            int flag = HEADER_STATE.NOT_FIND_HEADER;
            int start, end;
            start = end = -1;

            while (!isStop && device.io() != null){

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    readLen = device.io().available();
                    if(readLen <= 0)
                        continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {

                    byte[] buf = BytePool.getBuffer(readLen);

                    if(device.io().read(buf) < 0){

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {

                        if(debug){
                            Logger.getLogger().i("data :  "+ ParseUtil.bytesToHexString(buf));
                        }

                        byte[] specialCheck = BytePool.getBuffer(2);

                        if(buf.length >= 2){
                            System.arraycopy(buf,0,specialCheck,0,2);
                        } else if(buf.length == 1){
                            specialCheck[0] = buf[0];
                        }

                        if(flag == HEADER_STATE.NOT_FIND_HEADER
                                && checkSpecialState(specialCheck)
                                && device.upgrade() != null){

                            Logger.getLogger().i("receiver taximeter upgrade flag -> " + ParseUtil.bytesToHexString(specialCheck));
                            device.upgrade().handle(specialCheck);
                            continue;
                        }

                        for (int i = 0; i < buf.length - 1; i++) {

                            if ((buf[i]&0xff) == 0x55 && (buf[i + 1]&0xff) == 0xaa) {

                                if(flag == HEADER_STATE.LAST_PACKAGE_FIND_HEADER){

                                    start = 0; // start must be 0.
                                    end = i + 1;
                                    int len = end - start + 1;
                                    byte[] result = BytePool.getBuffer(tmp.length + len);
                                    if(debug){
                                        Logger.getLogger().i("LAST_PACKAGE_FIND_HEADER tmp.length : "+tmp.length + ", len :  "+len);
                                    }

                                    System.arraycopy(tmp,0,result,0,tmp.length);
                                    System.arraycopy(buf,0,result,tmp.length,len);

                                    try {
                                        queue.put(result);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    flag = HEADER_STATE.NOT_FIND_HEADER;
                                    continue;
                                }

                                if(flag == HEADER_STATE.NOT_FIND_HEADER){
                                    start = i;
                                    if(debug){
                                        Logger.getLogger().i("NOT_FIND_HEADER  start :  "+start);
                                    }
                                    flag = HEADER_STATE.CUR_PACKAGE_FIND_HEADER;
                                    continue;
                                }

                                if(flag == HEADER_STATE.CUR_PACKAGE_FIND_HEADER){

                                    end = i + 1; //include 55 aa.
                                    int len = end - start + 1;
                                    if(debug){
                                        Logger.getLogger().i("CUR_PACKAGE_FIND_HEADER  end : "+end + ", len : "+len);
                                    }

                                    if(len == 4){
                                        //avoid 55aa55aa.
                                        start += 2;
                                        flag = HEADER_STATE.CUR_PACKAGE_FIND_HEADER;
                                        continue;
                                    }

                                    byte[] result = BytePool.getBuffer(len);
                                    System.arraycopy(buf,start,result,0,len);
                                    try {
                                        queue.put(result);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    flag = HEADER_STATE.NOT_FIND_HEADER;
                                }
                            }
                        }

                        if(flag == HEADER_STATE.CUR_PACKAGE_FIND_HEADER){
                            int len = buf.length - start;
                            tmp = BytePool.getBuffer(len);

                            if(debug){
                                Logger.getLogger().i("CUR_PACKAGE_FIND_HEADER len : "+len);
                            }
                            System.arraycopy(buf,start,tmp,0,tmp.length);
                            flag = HEADER_STATE.LAST_PACKAGE_FIND_HEADER;
                        }
                    }
                    if(debug){
                        Logger.getLogger().i("INFO :::   queue size : "+queue.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //reset, might be not need to set.
                    readLen = 0;
                }

            }
        }
    }

    private boolean checkSpecialState(byte[] data){
        boolean result = false;
        for(byte b : specialCommandArray){
            if(data[0] == b){
                result = true;
                break;
            }
        }

        return result;
    }



}
