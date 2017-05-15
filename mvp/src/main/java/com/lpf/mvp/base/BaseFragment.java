package com.lpf.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liupengfei on 2017/5/12 18:03.
 */

public abstract class BaseFragment<T extends BasePresenter, E extends IBaseModel> extends Fragment {
    private Unbinder mUnbinder;
    public Activity mActivity;
    public T mPresenter;
    public E mModel;
    public View mView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mUnbinder = ButterKnife.bind(getActivity());
        mPresenter = MvpUtil.getT(this, 0);
        mModel = MvpUtil.getT(this, 1);

        if (null != mPresenter) {
            mPresenter.mContext = mActivity;
        }
        mView =  initView(inflater,container);
        initPresenter();
        initListener();
        return mView;
    }

    protected abstract void initPresenter();

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initListener();


}
