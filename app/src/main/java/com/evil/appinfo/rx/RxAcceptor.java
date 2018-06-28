package com.evil.appinfo.rx;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc ...
 */
public interface RxAcceptor<T> {
    void onStart();

    void onNext(T t);

    void onError(Throwable t);

    void onComplete();
}
