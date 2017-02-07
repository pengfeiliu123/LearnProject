package com.lpf.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liupf5 on 2016/2/26.
 */
public class ToastUtil {

    public static boolean debug = false;

    /**
     * 用于正常显示Toast
     * @param mContext
     * @param msg
     */
    public static void shortShow(Context mContext, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void longShow(Context mContext, String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 用于调试显示Toast
     * @param mContext
     * @param msg
     */
    public static void dshortShow(Context mContext, String msg) {
        if (debug) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void dlongShow(Context mContext, String msg) {
        if (debug) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }
}
