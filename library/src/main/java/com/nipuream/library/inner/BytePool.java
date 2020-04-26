package com.nipuream.library.inner;

import java.util.LinkedHashMap;

/**
 * Created by yanghui11 on 2020/4/23.
 *
 */
public class BytePool {

    private static BytePool pool;
    private static LinkedHashMap<Integer,byte[]> caches =
            new LinkedHashMap<>(100,0.75f,true);

    private BytePool(){}

    public synchronized  static byte[] getBuffer(int length){

        byte[] buffer = caches.get(length);
        if(buffer == null){
              buffer = new byte[length];
              caches.put(length,buffer);
        }
        return buffer;
    }

}
