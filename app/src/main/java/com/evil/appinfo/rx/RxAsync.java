package com.evil.appinfo.rx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc ...
 */
public class RxAsync<T> {
    private ExecutorService pool;
    private RxConfigs mRxConfigs;
    private Callable<T> callable;

    RxAsync(ExecutorService pool,RxConfigs rxScheduler) {
        this.pool = pool;
        mRxConfigs = rxScheduler;
    }

    RxAsync<T> callable(Callable<T> callable) {
        this.callable = callable;
        return this;
    }

    public RxAsync<T> delay(long time){
        mRxConfigs.delay = time;
        return this;
    }

    public void subscribe(AsyncCallback<T> callback) {
        mRxConfigs.asyncCallback = callback;
        Runnable mRunnable = new RxRunnable(mRxConfigs).callable(callable);
        DelayTaskDispatcher.get().postDelay(mRxConfigs.delay,pool,mRunnable);
    }
}
