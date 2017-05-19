package com.lpf.mvp.common;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Locale;
import java.util.Map;

import static android.provider.Settings.Secure.getString;

public class DeviceInfo {

    public String androidId;
//    public String advertiseId;
    public String locale;
    public String packageName;
//    public String versionCode;
//    public String versionName;
    public String networkType;
    public String networkSubType;

    public Map<String, String> params;

    public void init(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkType = cm.getActiveNetworkInfo().getTypeName();
            networkSubType = cm.getActiveNetworkInfo().getSubtypeName();
            if ((networkSubType != null) && networkSubType.equals(""))
                networkSubType = null;
        } catch (Exception e) {
            //ignore
        }
        locale = Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
//        packageName = context.getPackageName();
        try {
//            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
//            versionCode = packageInfo.versionCode + "";
//            versionName = packageInfo.versionName;
        } catch (Exception e) {
            //ignore
        }
        try {
            androidId = getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//            advertiseId = AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
        } catch (Exception e) {
            //ignore
//            advertiseId = "";
        }
    }

}
