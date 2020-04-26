package com.nipuream.library.fetcher;

import com.nipuream.library.convert.parse.IParser;
import com.nipuream.library.convert.upgrade.IUpgradeHandle;
import com.nipuream.library.fetcher.io.Io;
import com.nipuream.library.inner.Watchdog;
import com.nipuream.library.utils.Logger;
import com.nipuream.library.utils.ParseUtil;

/**
 * Created by yanghui11 on 2020/4/1.
 */
public class DeviceReader extends Thread implements Watchdog.Monitor{

    private static final String TAG = DeviceReader.class.getName();
    private CacheChain chain;
    private IParser parser;
    private IUpgradeHandle handle;
    private Io io;

    private boolean debug = false;


    private volatile boolean isStop = false;
    private  volatile  int acquires = 0;


    public DeviceReader(CacheChain chain) {
        super(TAG);
        this.chain = chain;
        Watchdog.getInstance().addMonitor(this);
    }

    @Override
    public void run() {
        super.run();


        while(!isStop && !isInterrupted() && chain != null){

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try{

                byte[] data = chain.read();
                if(debug){
                    Logger.getLogger().i("data : "+ ParseUtil.bytesToHexString(data));
                }

                if(data == null){
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                acquires ++;

                if(data.length > 0){

                    int length = ParseUtil.byteToInt(data,2,2);

                    if(length == 0){
                        continue;
                    }

                    if(data.length != (length + 7)){
                        continue;
                    }

                    if(debug){
                        Logger.getLogger().i("length : "+length);
                    }

                    byte[] buf = ParseUtil.byteTobyte(data,4,length);
                    //理应交于解析类处理 转化为业务对象
                    if(parser != null){
                        if(handle != null && !handle.isUpgrading()){ //如果在升级，则不处理消息
                            parser.parse(buf);
                        }
                    }else {
                        Logger.getLogger().w("parse is null, inject parse first !!!.");
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
         }

    }

    public void  quitQuit(){
        isStop = true;
    }



    @Override
    public int monitor() {
        int oldValue = acquires;
        acquires = 0;
        return oldValue;
    }

}
