package com.nipuream.library.convert.upgrade;

import android.text.TextUtils;

import com.nipuream.library.common.UpgradeInfo;
import com.nipuream.library.fetcher.IWriter;
import com.nipuream.library.inner.InnerFactory;
import com.nipuream.library.utils.Logger;

/**
 * Created by yanghui11 on 2020/4/9.
 */
public class UpgradeHandle implements IUpgradeHandle {

    private IWriter writer;
    private volatile boolean isUpgrading = false;
    private InnerFactory factory;
    private static final String UPGRADE_PATH = "/sdcard/Hik/JJQ_APP_7_20200423.bin";
    private UpgradeThread upgrader;
    private UpgradeInfo upgradeInfo;

    public UpgradeHandle(InnerFactory factory){
        this.factory = factory;
        if(factory == null){
            throw new RuntimeException("factory must not be null..");
        }
    }

    /**
     * 特殊字符定义
     */
    public static final int SOH = 0x01;
    public static final int EOT = 0x04;
    public static final int ACK = 0x06;
    public static final int NAK = 0x15;
    public static final int EOF = 0x1A;
    public static final int Can = 0x18;
    public static final int C = 0x43;

    @Override
    public void handle(byte[] data) {

        Logger.getLogger().i("****************** receive taximeter "+ data[0] + " ********************");
        if(upgrader != null){

            upgrader.handleMsg(data);
        } else if(!TextUtils.isEmpty(UPGRADE_PATH)){

            if(upgrader != null){
                upgrader.interrupt();
            }
            isUpgrading = true;

            upgrader = new UpgradeThread(upgradeInfo,UPGRADE_PATH,callback);
            upgrader.start();
        }
    }

    private IUpgradeCallback  callback = new IUpgradeCallback() {

        @Override
        public void result(int result) {

            Logger.getLogger().i("result : "+result);
            isUpgrading = false;
            upgrader = null;
        }

        @Override
        public void sendBufToDevice(byte[] body) {

            if(writer != null){
                writer.writeBuffer(body);
            }
        }
    };

    @Override
    public boolean isUpgrading() {
        return isUpgrading;
    }

    @Override
    public void changeUpgradeInfo(UpgradeInfo info) {
        this.upgradeInfo = info;
    }

    public interface IUpgradeCallback {

        void result(int result);

        void sendBufToDevice(byte[] body);
    }


}
