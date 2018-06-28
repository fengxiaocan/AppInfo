package com.evil.appinfo.rx;

import java.util.concurrent.Executor;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc ...
 */
class RxCounter<T> {
    long schedulerNumber = 0;//计划数目
    long completeNumber = 0;//完成数目
    RxAcceptor<T> rxAcceptor;
    private boolean isSyncComplete;
    private Executor mCompleteExecutor;

    public RxCounter(
            RxAcceptor<T> rxAcceptor,boolean isSyncComplete,Executor executor
    )
    {
        this.rxAcceptor = rxAcceptor;
        this.isSyncComplete = isSyncComplete;
        this.mCompleteExecutor = executor;
    }

    public synchronized void scheduler() {
        this.schedulerNumber++;
    }

    public synchronized void complete() {
        this.completeNumber++;
        if (isSyncComplete) {
            //非相同的线程,
            if (completeNumber >= schedulerNumber) {
                if (rxAcceptor != null) {
                    mCompleteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            rxAcceptor.onComplete();
                        }
                    });
                }
            }
        }
    }
}
