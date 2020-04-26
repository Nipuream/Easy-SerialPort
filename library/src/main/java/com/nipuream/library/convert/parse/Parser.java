package com.nipuream.library.convert.parse;

import android.util.ArrayMap;
import com.nipuream.library.common.DeviceInfo;
import com.nipuream.library.common.ModelConvert;
import com.nipuream.library.model.Convert;
import com.nipuream.library.utils.Logger;
import com.nipuream.library.utils.ParseUtil;
import java.lang.reflect.Method;

/**
 * Created by yanghui11 on 2020/4/2.
 *
 *  1.驱使Convert 获取到DeviceInfo的派生类
 *  2.填充DeviceInfo基类属性
 *  3.得到一个完整的业务对象
 */
@SuppressWarnings("unchecked")
public class Parser<T extends DeviceInfo> implements IParser {

    private Convert convert ;
    private ArrayMap<Integer,Method> convertMethods = new ArrayMap<>();

    private boolean debug = false;

    public Parser(){
        convert = new Convert();
        collectConvertMethod();
    }

    //02009900  0064
    @Override
    public T parse(byte[] data) {

        T t = null;

        int offset = 0;
        int deviceType = data[offset++];
        int vendorId = data[offset++];
        int commID = ParseUtil.byteToInt(data,offset,2);
        offset += 2;

        if(debug){
            Logger.getLogger().i("commID = "+Integer.toHexString(commID));
        }

        Method method = convertMethods.get(commID);
        if(method != null){
            try {
                t = (T) method.invoke(convert,data,offset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            t = (T) convert.common();
        }

        t.deviceType = deviceType;
        t.vendorId = vendorId;
        t.commID = commID;
        if(debug){
            Logger.getLogger().i("deviceInfo is :"+t.toString());
        }

        return t;
    }

    private void collectConvertMethod(){

        Method[] methods = convert.getClass().getDeclaredMethods();
        if(methods != null && methods.length > 0){
            for (Method method : methods){
                ModelConvert annotation = method.getAnnotation(ModelConvert.class);
                if(annotation != null){
                    convertMethods.put(annotation.commId(),method);
                    Logger.getLogger().i("commId = "+annotation.commId() + ", method = "+method.getName() + " \n");
                }
            }
        }

    }


}
