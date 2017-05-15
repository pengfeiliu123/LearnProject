package com.lpf.mvp.test;

/**
 * Created by liupengfei on 2017/5/12 16:20.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginModel loginModel;
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel(this);
    }

    @Override
    public void loginToServer(String userName, String password) {
        loginView.showProgress(true);
        loginModel.login(userName,password);
    }

    @Override
    public void loginSucceed() {
        loginView.showProgress(false);
        loginView.showLoginView();
    }
}
