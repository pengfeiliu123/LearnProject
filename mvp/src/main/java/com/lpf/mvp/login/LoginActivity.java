package com.lpf.mvp.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lpf.mvp.R;
import com.lpf.mvp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel>
        implements View.OnClickListener, LoginContract.View {

    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fragment)
    FrameLayout fragment;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentTest fragmentTest = new FragmentTest();
        transaction.replace(R.id.fragment, fragmentTest);
        transaction.commit();
    }

    @Override
    protected void initListener() {
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                mPresenter.rxLogin("lpf", "123");
                break;
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void success() {
        Toast.makeText(mActivity, "success 啦", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed() {
        Toast.makeText(mActivity, "failed 啦", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clear() {
        userName.setText("");
        password.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
