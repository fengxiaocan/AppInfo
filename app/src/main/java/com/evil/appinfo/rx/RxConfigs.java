package com.evil.appinfo.rx;

import java.util.concurrent.Executor;

/**
 * Store some configurations for sync task.
 */
final class RxConfigs {
    String name;// thread name
    RxSubscribe callback;// thread callback
    long delay;// delay time
    Executor mExecutor;// thread mExecutor
    AsyncCallback asyncCallback;
}
