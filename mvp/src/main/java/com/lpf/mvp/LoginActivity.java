package com.lpf.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lpf.mvp.test.ILoginPresenter;
import com.lpf.mvp.test.ILoginView;
import com.lpf.mvp.test.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.tv_huidiao)
    TextView tvHuidiao;

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);

        initViews();
    }

    private void initViews() {
        tvHuidiao.setText("click me to login");
        tvHuidiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.loginToServer("lpf","123");
            }
        });
    }

    @Override
    public void showProgress(boolean enable) {

    }

    @Override
    public void showLoginView() {
        tvHuidiao.setText("loginSuccess");
    }
}
