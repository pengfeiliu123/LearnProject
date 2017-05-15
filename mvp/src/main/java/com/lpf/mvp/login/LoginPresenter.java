package com.lpf.mvp.login;

import android.text.TextUtils;
import android.widget.Toast;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liupengfei on 2017/5/12 19:14.
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    void login(String userName, String password) {

    }

    @Override
    void rxLogin(String userName, String password) {
        if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)){
            Toast.makeText(mContext,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
        }else{
            mView.showLoading();
            mModel.rxLogin(userName,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            mView.hideLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.hideLoading();
                        }

                        @Override
                        public void onNext(String s) {
                            mView.hideLoading();
                            if(s.equals("success")){
                                mView.success();
                            }else{
                                mView.failed();
                            }
                            Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    void clear() {

    }
}
