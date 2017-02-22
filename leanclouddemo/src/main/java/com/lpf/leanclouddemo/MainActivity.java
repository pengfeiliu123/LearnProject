package com.lpf.leanclouddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avos.avoscloud.AVObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AVAnalytics.trackAppOpened(getIntent());

        AVObject testObject = new AVObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
