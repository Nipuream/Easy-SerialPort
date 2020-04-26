package com.nipuream.library.convert.upgrade;

import com.nipuream.library.common.DeviceConstants;
import com.nipuream.library.common.UpgradeInfo;
import com.nipuream.library.utils.Logger;
import com.nipuream.library.utils.ParseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by qiujingyun on 2018/1/5.
 */

public class UpgradeThread extends Thread {

    String filePath;
    int vendorIdentify;
    byte[] version;
    private UpgradeHandle.IUpgradeCallback callback;

    int curIndex = 1, resend = 0;
    boolean isEnding = false;
    boolean isFinished = false;
    boolean isUpgrading = false;
    File file;
    byte[] curMsg;

    public static final int SOH = 0x01;
    public static final int EOT = 0x04;
    public static final int ACK = 0x06;
    public static final int NAK = 0x15;
    public static final int EOF = 0x1A;
    public static final int Can = 0x18;
    public static final int C = 0x43;
    final int FIX_LEN = 128;

    public UpgradeThread(UpgradeInfo upgradeInfo, String filePath, UpgradeHandle.IUpgradeCallback callback) {


        this.filePath = filePath;
        this.callback = callback;

        this.vendorIdentify = upgradeInfo.getVendorId();
        byte software = upgradeInfo.getSoftwareVer();
        byte subSoftware = upgradeInfo.getSoftwareSubVer();


        this.vendorIdentify = vendorIdentify;
        this.version = new byte[]{software, subSoftware};

        Logger.getLogger().d("升级文件路径：" + filePath + " version: " + ParseUtil.bytesToHexStringLog(version) + " callback: " + (callback==null) + " vendorIdentify: " + vendorIdentify);
        curIndex = 1;
    }

    @Override
    public void run() {
        super.run();
        Logger.getLogger().d("isUpgrading: " + isUpgrading);
        if (filePath != null && version != null && !isUpgrading) {
            file = new File(filePath);
            if (file == null || !file.exists()) {
                Logger.getLogger().w("file is null..");
                //ProtocolApplication.getProtocolApplication().playTtsInfo("升级文件不存在");
//                changeDevUpgradeStatus(false);
                callback = null;
                return;
            }
            isEnding = isFinished = false;
            isUpgrading = true;
            sendData(curIndex);
        }else {
            Logger.getLogger().w("run error..");
        }
    }

    public void sendData(int index) {
        Logger.getLogger().d("开始升级...index: " + index + " isUpgrading: " + isUpgrading + " filePath: " + filePath);
        if (isUpgrading) {
            if (file != null && file.exists() && file.isFile()) {
                int readLen = FIX_LEN * (index - 1);
                if (readLen <= file.length()) {
                    int count, currentPos = 0;
                    byte[] data = new byte[FIX_LEN];
                    FileInputStream inputStream = null;

                    try {
                        inputStream = new FileInputStream(file);
                        inputStream.skip(readLen);
                        while ((count = inputStream.read(data, currentPos, FIX_LEN - currentPos)) > 0) {
                            Logger.getLogger().d("读到数据：" + ParseUtil.bytesToHexStringLog(data));
                            currentPos += count;
                            if (currentPos != FIX_LEN) {
                                continue;
                            }
                        }
                        if (currentPos == 0) {
                            sendMsg(new byte[]{EOT});
                            isEnding = true;
                            return;
                        } else if (currentPos > 0 && currentPos < FIX_LEN) {
                            for (int i = currentPos; i < FIX_LEN; i++) {
                                data[i] = EOF;
                            }
                        }
                        byte[] dataWrapper = new byte[1 + 1 + 1 + 128 + 2];
                        dataWrapper[0] = SOH;
                        dataWrapper[1] = (byte) (curIndex & 0xff);
                        dataWrapper[2] = (byte) (0xff - (byte) (curIndex & 0xff));
                        System.arraycopy(data, 0, dataWrapper, 3, FIX_LEN);
                        byte[] crc = ParseUtil.intTo2Bytes(ParseUtil.CalCrc(data, FIX_LEN));
                        System.arraycopy(crc, 0, dataWrapper, dataWrapper.length - 2, 2);
                        sendMsg(dataWrapper);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
                    } finally {
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    sendMsg(new byte[]{EOT});
                    isEnding = true;
                    return;
                }
                if (index == 1) {
                    playTts();
                }
            }else {
//                changeDevUpgradeStatus(false);
                //sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
                //ProtocolApplication.getProtocolApplication().playTtsInfo("升级文件不存在，升级失败");
                Logger.getLogger().d("升级文件不存在，升级失败");
                sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
            }
        }
    }

    private void sendResult(int result) {
        Logger.getLogger().d("升级完成，回复升级结果：" + result);
        isUpgrading = false;
//        changeDevUpgradeStatus(false);
        if (result != DeviceConstants.UPGRADE_RESULT_OK) {
            //TODO 语音播放升级成功
        }else {
            //TODO 语音播放升级失败
        }
        if (callback != null){
            callback.result(result);
        }
        interrupt();
    }

//    private void changeDevUpgradeStatus(boolean isWaitForUpgrade) {
//        LogUtils.d(TAG, "changeDevUpgradeStatus: " + isWaitForUpgrade);
//        if (deviceType == DeviceType.DEVICE_SECURITY) {
//            SecureModuleMessageHandler.setIsWaitForUpgrade(isWaitForUpgrade);
//        }else if (deviceType == DeviceType.DEVICE_TOP_LIGHT) {
//            //TopLightMessageHandler.setIsWaitForUpgrade(isWaitForUpgrade);
//        }
//    }

    /**
     * 收到外设回复升级结果的消息
     * @param receiveMsg
     */
    public void handleMsg(byte[] receiveMsg) {
        Logger.getLogger().d("升级线程收到外设回复的指令结果：" + receiveMsg ==null?"":ParseUtil.bytesToHexStringLog(receiveMsg));
        if (receiveMsg == null || receiveMsg.length <= 0 || !isUpgrading)
            return;
        //BaseApplication.getInstance().removeCallback(runnable);
        if (ACK == (receiveMsg[0] & 0xff)) {
            resend = 0;
            if (!isEnding) {
                curIndex++;
                sendData(curIndex);
            }else if (!isFinished) {
                isFinished = true;

                sendResult(DeviceConstants.UPGRADE_RESULT_OK);

//                changeDevUpgradeStatus(false);
//                TaxiApplication.getInstance().postRunnableDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        LogUtils.d(TAG, "查询升级后版本是否改变？");
//                        ExternalDeviceRequestBean requestBean = new ExternalDeviceRequestBean();
//                        requestBean.setDeviceType(deviceType);
//                        if (DeviceType.DEVICE_METER == deviceType) {
//                            requestBean.setCommand(DeviceConstants.TAXIMETER_QUERY_STATUS);
//                        }else if (DeviceType.DEVICE_SECURITY == deviceType) {
//                            requestBean.setCommand(DeviceConstants.QUERY_DEVICE_STATUS);
//                        }else if (DeviceType.DEVICE_TOP_LIGHT == deviceType) {
//                            requestBean.setCommand(DeviceConstants.LIGHT_QUERY);
//                        }
//                        ExternalDeviceFactory.getInstance().sendMsgToDevice(requestBean,
//                                new IMessageCallback(){
//                                    @Override
//                                    public void onReceiveMsg(int deviceType, int commandID, ExternalDeviceRequestBean deviceRequestBean) {
//                                        //断连，查不到版本
//                                        if (deviceRequestBean != null && !deviceRequestBean.getSendResultSucc()) {
//                                            sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
//                                            return;
//                                        }
//                                        LogUtils.d(TAG, "升级线程收到返回结果...." + ParseUtil.bytesToHexStringLog(deviceRequestBean.getBody()));
//                                        LogUtils.d(TAG, "version...." + ParseUtil.bytesToHexStringLog(version));
//                                        if (Arrays.equals(version, Arrays.copyOfRange(deviceRequestBean.getBody(), 5, 8))) {
//                                            if (callback != null) {
//                                                LogUtils.d(TAG, "升级完成，查询到版本号OK");
//                                                sendResult(DeviceConstants.UPGRADE_RESULT_OK);
//                                            }
//                                        }else if (callback != null) {
//                                            LogUtils.d(TAG, "升级完成，查询到版本号ERROR");
//                                            sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
//                                        }
//                                    }
//                                });
//                        TaxiApplication.getInstance().postRunnableDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                changeDevUpgradeStatus(false);
//                            }
//                        },3000);
//                    }
//                }, 20 * 1000);
//            }
            }else if (NAK == (receiveMsg[0] & 0xff)) {
                resend = 0;
                sendData(curIndex);
            }else if (Can == (receiveMsg[0] & 0xff) && callback != null) {
                sendResult(DeviceConstants.UPGRADE_RESULT_DEVICE_CANCEL);
                if(file != null && file.exists()) {
                    boolean ret = file.delete();
                    Logger.getLogger().d("删除文件，返回：" + ret);
                }
            }
        }
    }

    /**
     * 发送消息给外设，有三次重发机制
     * @param msg
     */
    private void sendMsg(byte[] msg) {
//        int sendDeviceType, vendorIdentify;
//        if (DeviceType.DEVICE_SECURITY == deviceType) {
//            sendDeviceType = DeviceType.SECURE_MODULE_UPGRADE;
//            vendorIdentify = SpUtil.getXmlInstance().getSecureVendorIdentify();
//        }else if (DeviceType.DEVICE_TOP_LIGHT == deviceType) {
//            sendDeviceType = DeviceType.TOP_LIGHT_UPGRADE;
//            vendorIdentify = SpUtil.getXmlInstance().getTopLightVendorIdentify();
//        }else if (DeviceType.DEVICE_METER == deviceType) {
//            sendDeviceType = DeviceType.TAXI_METER_UPGRADE;
//            vendorIdentify = SpUtil.getXmlInstance().getMeterVendorIdentify();
//        }else {
//            return;
//        }
//        byte[] mArray = IsuMessageWrapper.getRequest(vendorIdentify, sendDeviceType, sendDeviceType & 0xFF, msg);
//        LogUtils.d(TAG,"一个数据包读取完毕，准备写入： "+ deviceType+"==="+ sendDeviceType+ "当前数据长度： "+ mArray.length);
//        ExternalDeviceFactory.getInstance().sendMsgToDevice(new ExternalDeviceRequestBean(deviceType, sendDeviceType
//                ,msg)
//                , new IMessageCallback() {
//                    @Override
//                    public void onReceiveMsg(int deviceType, int commandID, ExternalDeviceRequestBean requestBean) {
//                        //断连了
//                        if (requestBean != null && !requestBean.getSendResultSucc()) {
//                            sendResult(DeviceConstants.UPGRADE_RESULT_ERROR);
//                        }
//                    }
//                });

        if(callback != null){
            callback.sendBufToDevice(msg);
        }

        curMsg = msg;
    }

    @Override
    public void interrupt() {
        Logger.getLogger().d("upgrade thread interrupt");
        // BaseApplication.getInstance().removeCallback(runnable);
        isUpgrading = false;
//        changeDevUpgradeStatus(false);
        callback = null;
        super.interrupt();
    }

    private void playTts() {
        Logger.getLogger().i("############## taximeter start upgrading #################");
//        if (DeviceType.DEVICE_SECURITY == deviceType) {
//            TaxiApplication.getInstance().playLocalTtsInfo("安全模块开始升级");
//        }else if (DeviceType.DEVICE_TOP_LIGHT == deviceType) {
//            TaxiApplication.getInstance().playLocalTtsInfo("顶灯开始升级");
//        }else if (DeviceType.DEVICE_METER == deviceType) {
//            TaxiApplication.getInstance().playLocalTtsInfo("计价器开始升级");
//        }
    }
}
