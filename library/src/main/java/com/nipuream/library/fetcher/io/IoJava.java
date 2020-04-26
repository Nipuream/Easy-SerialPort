package com.nipuream.library.fetcher.io;

import com.hikvision.ae.serialport.SerialPort;
import com.nipuream.library.inner.InnerFactory;
import com.nipuream.library.utils.Logger;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yanghui11 on 2020/4/16.
 *
 */
public class IoJava implements Io{

    private InnerFactory device;
    private SerialPort serialPort;
    private FileInputStream inputStream;
    private FileOutputStream outputStream;

    public IoJava(InnerFactory device){
        this.device = device;

        serialPort = device.serialPort();
        Logger.getLogger().i("INFO : serial port address : "+serialPort);

        inputStream = serialPort.getFileInputStream();
        outputStream = serialPort.getFileOutputStream();
    }

    @Override
    public int read(byte[] buf) throws IOException {
        return inputStream.read(buf);
    }

    @Override
    public int read(byte[] buf, int offset, int count) throws IOException {
        return inputStream.read(buf,offset,count);
    }

    @Override
    public void write(byte[] buf) throws IOException {
        outputStream.write(buf);
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {

        if(inputStream != null){
            inputStream.close();
        }

        if(outputStream != null){
            outputStream.close();
        }

    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
}
