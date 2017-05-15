package com.lpf.mvp.test;

/**
 * Created by liupengfei on 2017/5/12 16:17.
 */

public interface ILoginPresenter {
    void loginToServer(String userName,String password);
    void loginSucceed();
}
