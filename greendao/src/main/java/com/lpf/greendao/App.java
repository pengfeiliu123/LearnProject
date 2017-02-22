package com.lpf.greendao;

import android.app.Application;
import android.content.Context;

/**
 * Created by liupengfei on 17/2/4.
 */

public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();
    }

    public static Context getContext(){
        return mContext;
    }
}
