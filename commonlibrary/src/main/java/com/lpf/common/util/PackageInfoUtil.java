package com.lpf.common.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by liupengfei on 17/2/7.
 */

public class PackageInfoUtil {

    // get package name
    public static String getPackageName(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
    }

    // get version name
    public static String getVersionName(Context context)throws PackageManager.NameNotFoundException{
        return context.getPackageManager().getPackageArchiveInfo(context.getPackageName(),0).versionName;
    }

    // get version code
    public static int getVersionCode(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageArchiveInfo(context.getPackageName(), 0).versionCode;
    }

}
