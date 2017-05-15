package com.lpf.mvp.base;

import android.content.Context;

/**
 * Created by liupengfei on 2017/5/12 18:01.
 * T: View.class
 * E:Model.class
 */

public abstract class BasePresenter<T, E> {
    public Context mContext;
    public T mView;
    public E mModel;

    public void setViewAndModel(T view, E model) {
        this.mView = view;
        this.mModel = model;
    }
}
