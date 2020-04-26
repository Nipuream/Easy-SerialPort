package com.nipuream.library.convert.parse;

import android.util.ArrayMap;

import com.nipuream.library.common.DeviceInfo;
import com.nipuream.library.common.ModelConvert;
import com.nipuream.library.inner.InnerFactory;
import com.nipuream.library.model.IModelListener;
import com.nipuream.library.utils.Logger;

import java.lang.reflect.Method;

/**
 * Created by yanghui11 on 2020/4/2.
 *
 *  将Parser  {@link Parser} 类转化的业务对象进行分发到业务层
 */
public class ParserPlus<T extends DeviceInfo> implements IParser {

    private Parser<T> parser = new Parser<>();
    private Interceptor<T> interceptor ;
    private InnerFactory factory;
    private DispatchModel dispatch;
    private ArrayMap<Integer, Method> callbacks = new ArrayMap<>();

    public ParserPlus(InnerFactory factory){

        this.factory = factory;
        if(factory == null){
            throw  new RuntimeException("factory must not be null.");
        }

        interceptor = new Interceptor<>(factory.writer_l());
        dispatch = new DispatchModel();
    }

    @Override
    public T parse(byte[] data) {

        // parse...
        T t  =  parser.parse(data);

        // intercept...
        interceptor.intercept(t);

        // dispatch ... ^v^
        if(factory.listener() != null
                && callbacks.size() > 0){

            //switch threads, execute code in business layer.
            factory.executor().execute(dispatch.model(t));
        } else {
            if(factory.listener() != null && callbacks.size() <= 0){
                collectInvokeMethod();
            } else {
                Logger.getLogger().i("listener is null...");
            }
        }
        return t;
    }


    public void  collectInvokeMethod(){

        if(factory.listener() != null){

            Method[] methods = IModelListener.class.getDeclaredMethods();
            Logger.getLogger().i("methods : "+methods.length);

            if(methods.length > 0){
                for(Method method : methods){
                    ModelConvert convert = method.getAnnotation(ModelConvert.class);
                    if(convert != null){
                        callbacks.put(convert.commId(),method);
                        Logger.getLogger().i("commId : "+convert.commId() + ", method : "+ method.getName() + "\n");
                    }
                }
            }
        } else {
            Logger.getLogger().e("Error ::: check listener is null ?");
        }
    }

    private class DispatchModel implements Runnable{

        private DeviceInfo info;


        public DispatchModel model(DeviceInfo info){
            this.info = info;
            return this;
        }

        @Override
        public void run() {

            Method method = null;
            try {
                method = callbacks.get(info.commID);
                if(method !=null){
                    method.invoke(factory.listener(),info);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if(method !=null)
                    Logger.getLogger().e("Error :::  invoke error, method : "+method.getName());
            }
        }
    }


}
