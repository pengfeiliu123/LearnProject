package com.lpf.mvp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by liupengfei on 2017/5/18 14:01.
 */

public class MyWebView extends WebView {


    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_UP){
//            getParent().getParent().requestDisallowInterceptTouchEvent(false);
//        }else{
            getParent().getParent().requestDisallowInterceptTouchEvent(false);
//        }
        return super.onTouchEvent(event);
    }
}
