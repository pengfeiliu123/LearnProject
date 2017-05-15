package com.lpf.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lpf.mvp.test.BaseModel;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liupengfei on 2017/5/12 18:03.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends IBaseModel> extends AppCompatActivity {
    private Unbinder mUnbinder;
    public Context mActivity;
    public T mPresenter;
    public E mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        mUnbinder = ButterKnife.bind(BaseActivity.this);
        mActivity = this;

        mPresenter = MvpUtil.getT(this, 0);
        mModel = MvpUtil.getT(this, 1);

        if (null != mPresenter) {
            mPresenter.mContext = this;
        }
        initView();
        initListener();
        initPresenter();
    }

    protected abstract int initLayout();

    protected abstract void initPresenter();

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }
}
