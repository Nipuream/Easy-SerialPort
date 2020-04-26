package com.nipuream.library.inner;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanghui11 on 2020/4/9.
 *
 */
public class Watchdog extends  Thread{

    private List<Monitor> monitors = new ArrayList<>();

    private static Watchdog instance;
    private volatile  boolean isStop = false;
    private static final String TAG = Watchdog.class.getName();

    private Notifier notifier ;

    public static synchronized Watchdog getInstance(){

        if(instance == null){
            instance = new Watchdog();
        }
        return instance;
    }

    public void addMonitor(Monitor monitor){
        monitors.add(monitor);
    }

    public void addNotifier(Notifier notifier){
        this.notifier = notifier;
    }

    @Override
    public void run() {
        super.run();

        while (!isStop){

            synchronized (Watchdog.class){
                boolean blocking = false;
                if(monitors.size() > 0){
                    for(Monitor monitor : monitors){
                        int value = monitor.monitor();
                        Log.i(TAG,"value : "+value);
                        if(value <= 0){
                            blocking = true;
                            break;
                        }
                    }
                }

                if(blocking){

                    Log.i(TAG,"===============  TEAR DOWN =============");
                    if(notifier != null){
                        monitors.clear();
                        notifier.tear();
                    }
                } else {
                    Log.i(TAG,"============ All of the threads are running ====================");
                }
            }

            try {
                sleep(1000 * 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public interface Monitor {

        int monitor();
    }

    public interface Notifier {
        void tear();
    }

}
