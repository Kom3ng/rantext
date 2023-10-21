package org.abstruck.mirai.runtime;

import org.abstruck.mirai.config.Config;

public class ThreadManager {
    private static SenderThread runnableHandle = new SenderThread();
    private static Thread threadHandle = new Thread(runnableHandle);
    public static void start(){
        runnableHandle.enable();
        threadHandle.start();
    }
    public static void stop(){
        runnableHandle.disable();
        threadHandle.interrupt();
    }
}
