package com.lpf.leanclouddemo;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by liupengfei on 17/2/4.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
        AVOSCloud.initialize(this, "A3EiG9L31X4eaimRiAmqyMqB-gzGzoHsz", "ycYn10UOlMo3l4sE7eeq7b0X");
    }
}
