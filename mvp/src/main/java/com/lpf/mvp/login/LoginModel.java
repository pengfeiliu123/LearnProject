package com.lpf.mvp.login;


import android.os.SystemClock;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liupengfei on 2017/5/12 19:10.
 */

public class LoginModel implements LoginContract.Model {

    @Override
    public void login(String userName, String password) {

    }

    @Override
    public Observable<String> rxLogin(String userName, String password) {
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(2000);
                if(true){
                    subscriber.onNext("success");
                }else{
                    subscriber.onNext("failed");
                }
                subscriber.onCompleted();
            }
        });
    }
}
