package com.lpf.mvp.test;


import android.os.Handler;
import android.util.Log;

/**
 * Created by liupengfei on 2017/5/12 16:17.
 */

public class LoginModel implements ILoginModel {

    private ILoginPresenter presenter;
    private Handler mHandler = new Handler();

    public LoginModel(ILoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void login(String name, String password) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("lpf","run:");
                presenter.loginSucceed();
            }
        },2000);
    }
}
