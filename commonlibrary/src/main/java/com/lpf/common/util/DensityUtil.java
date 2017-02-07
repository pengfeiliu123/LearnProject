package com.lpf.common.util;

import android.content.Context;

/**
 * Created by liupengfei on 17/2/7.
 */

public class DensityUtil {


    // dp -> px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // px -> dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
