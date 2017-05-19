package com.lpf.mvp;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by liupengfei on 2017/5/16 14:48.
 */

public class MainApplication extends android.app.Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        NGCommonConfiguration.NEWS_SERVER = getResources().getString(R.string.news_server_url);

        ViewTarget.setTagId(R.id.glide_tag_id);
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
