package com.nipuream.library;

import com.hikvision.ae.serialport.SerialPort;
import com.nipuream.library.convert.parse.IParser;
import com.nipuream.library.convert.parse.ParserPlus;
import com.nipuream.library.convert.upgrade.IUpgradeHandle;
import com.nipuream.library.convert.upgrade.UpgradeHandle;
import com.nipuream.library.fetcher.CacheChain;
import com.nipuream.library.fetcher.DeviceReader;
import com.nipuream.library.fetcher.DeviceWriter;
import com.nipuream.library.fetcher.IWriter;
import com.nipuream.library.fetcher.io.Io;
import com.nipuream.library.fetcher.io.IoJava;
import com.nipuream.library.inner.InnerFactory;
import com.nipuream.library.inner.Watchdog;
import com.nipuream.library.model.IModelListener;
import com.nipuream.library.model.IModelWriter;
import com.nipuream.library.model.ModelWriterImpl;
import com.nipuream.library.utils.Logger;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yanghui11 on 2020/4/1.
 *
 *  向外界提供外设操作接口，将串口报文封装成业务对象，分发到业务模块。
 *
 *  需要外界提供length,package data, 校验方式的接口实现
 *
 */
public class DeviceFactory implements InnerFactory, Watchdog.Notifier{

    /**
     *  串口操作类
     */
    private static  SerialPort mSerialPort = null;
    private String path = "/dev/ttyHSL1";
    private int baudrate = 115200;

    /**
     *  线程实例，不断从设备节点读取数据
     */
    private DeviceReader deviceReader;

    /**
     * 向设备节点写入数据的writer，不暴露给外界
     */
    private IWriter writer;

    /**
     * 串口解析类，将报文解析为业务对象
     */
    private IParser parser;

    /**
     * 升级处理类
     */
    private IUpgradeHandle handle;

    /**
     * 业务相关 writer.
     */
    private IModelWriter modelWriter;

    /**
     * 回调业务层listener
     */
    private IModelListener listener;

    /**
     * 线程池
     */
    private ExecutorService executor;

    private IPolicy policy;

    /**
     * I/O操作
     */
    private Io io;

    private CacheChain chain;


    public static class Builder {

        private IPolicy policy;

        public Builder policy(IPolicy policy){
            this.policy = policy;
            return this;
        }


        public DeviceFactory build(){
            return new DeviceFactory(this);
        }
    }


    private DeviceFactory(Builder builder){

        this.policy = builder.policy;

        executor = new ThreadPoolExecutor(2,4,60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(512),new ThreadPoolExecutor.DiscardPolicy());

        parser = new ParserPlus(this);
        handle = new UpgradeHandle(this);

        Watchdog.getInstance().addNotifier(this);
//        Watchdog.getInstance().start();
    }

    /**
     * 串口初始化，读写线程初始化
     */
    @Override
    public void init(){
        if(mSerialPort == null){

            try{
                mSerialPort = new SerialPort("test");
                try {
                    mSerialPort.openSerialPort(path,baudrate);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                io = new IoJava(this);
                chain = new CacheChain(this);
                chain.start();

                deviceReader = new DeviceReader(chain);
                deviceReader.start();

                DeviceWriter deviceWriter = new DeviceWriter(io);
                deviceWriter.start();
                writer = deviceWriter;
                modelWriter = ModelWriterImpl.createWriter(writer, handle);

                injectField(UpgradeHandle.class,"writer",handle,writer);
                injectField(DeviceReader.class,"parser",deviceReader,parser);
                injectField(DeviceReader.class,"handle",deviceReader,handle);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 外界注册了Listener，则可得到外设相关的业务对象
     * @param listener
     */
    @Override
    public void register(IModelListener listener){
        this.listener = listener;
    }

    /**
     * 外界移除对串口的监听
     */
    @Override
    public void remove() {
        this.listener = null;
    }

    /**
     * 提供 Model writer.
     * @return
     */
    @Override
    public IModelWriter writer(){
        return modelWriter;
    }

    @Override
    public void close()  {

        if(io != null){
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(writer != null){
            writer.quitQuit();
            writer = null;
        }

        if(deviceReader != null){
            deviceReader.quitQuit();
            deviceReader = null;
        }

        if(mSerialPort != null){
            try{
                mSerialPort.close();
                mSerialPort = null;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public IModelListener listener() {
        return listener;
    }

    @Override
    public ExecutorService executor() {
        return executor;
    }

    @Override
    public IWriter writer_l() {
        return writer;
    }

    @Override
    public SerialPort serialPort() {
        return mSerialPort;
    }

    @Override
    public Io io() {
        return io;
    }

    @Override
    public IUpgradeHandle upgrade() {
        return handle;
    }

    /**
     *  向目标类进行属性注入
     */
    private void  injectField(Class cls, String methodName, Object obj, Object value){

        try{
            Field field = cls.getDeclaredField(methodName);
            if(field != null){
                field.setAccessible(true);
                field.set(obj, value);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void tear() {
        Logger.getLogger().i("================ TEAR :  close port. =====================");
        close();
        Logger.getLogger().i("================  TEAR :  init again. ======================");
        init();
    }


}