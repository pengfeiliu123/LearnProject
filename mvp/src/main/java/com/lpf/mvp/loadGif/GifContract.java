package com.lpf.mvp.loadGif;

import com.lpf.mvp.base.BasePresenter;
import com.lpf.mvp.base.IBaseModel;
import com.lpf.mvp.base.IBaseView;

import java.util.List;

/**
 * Created by liupengfei on 2017/5/16 13:56.
 */

public class GifContract {

    interface Model extends IBaseModel{
        rx.Observable<List<Feed>> loadGif();

    }

    interface View extends IBaseView{
        void success();
        void failed();
        void setListData(List<Feed> datas);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        abstract void loadGif();
    }
}
