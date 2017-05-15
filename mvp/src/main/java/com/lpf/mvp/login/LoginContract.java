package com.lpf.mvp.login;

import com.lpf.mvp.base.BasePresenter;
import com.lpf.mvp.base.IBaseModel;
import com.lpf.mvp.base.IBaseView;

/**
 * Created by liupengfei on 2017/5/12 18:57.
 * contains: Model,View,Presenter.
 */

public class LoginContract {

    interface Model extends IBaseModel{
        void login(String userName,String password);
        rx.Observable<String> rxLogin(String userName, String password);
    }

    interface View extends IBaseView{
        void success();
        void failed();
        void clear();
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        abstract void login(String userName,String password);
        abstract void rxLogin(String userName,String password);
        abstract void clear();
    }
}
