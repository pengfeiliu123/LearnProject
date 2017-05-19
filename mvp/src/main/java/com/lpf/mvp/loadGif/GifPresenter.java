package com.lpf.mvp.loadGif;

import android.widget.Toast;

import com.lpf.mvp.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liupengfei on 2017/5/16 14:10.
 */

public class GifPresenter extends GifContract.Presenter {

    @Override
    void loadGif() {
        mModel.loadGif()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Feed>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, "complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Feed> feeds) {
                        Toast.makeText(mContext, "next", Toast.LENGTH_SHORT).show();
                        mView.setListData(feeds);
                    }
                });
    }
}
