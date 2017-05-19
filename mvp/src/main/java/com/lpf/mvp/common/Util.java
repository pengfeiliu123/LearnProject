package com.lpf.mvp.common;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lpf.mvp.BuildConfig;
import com.lpf.mvp.MainApplication;
import com.lpf.mvp.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public final class Util {

    private Util() {
    }

    public static void closeSilently(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void clearDir(String dir, boolean deleteDir) {
        File file = new File(dir);
        if (!file.exists())
            return;

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();
                continue;
            }

            if (files[i].isDirectory()) {
                clearDir(files[i].getAbsolutePath(), true);
            }
        }

        if (deleteDir)
            file.delete();
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobile(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public static String toJsonPretty(Object obj) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }

    public static String doubleToStringNoDecimal(double d) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###");
        return formatter.format(d);
    }

    public static String formatTime(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return format.format(time);
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static boolean post(String url, String postStr) {
        HttpURLConnection conn = null;
        try {
            Log.d("test", "post:" + postStr);
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setConnectTimeout(15 * 1000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postStr);
            writer.flush();
            writer.close();
            os.close();

            Log.d("test", "post:" + conn.getResponseCode());
            if (conn.getResponseCode() == 200)
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return false;
    }

    public static OkHttpClient.Builder ignoreTrust(OkHttpClient.Builder builder) throws Exception {
        return builder;
//        final X509TrustManager trustManager = new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//            }
//
//            @Override
//            public X509Certificate[] getAcceptedIssuers() {
//                return new X509Certificate[0];
//            }
//        };
//
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
//        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//        return builder
//                .sslSocketFactory(sslSocketFactory, trustManager)
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                });

    }

    public static Request.Builder addHeader(Request.Builder builder) {
        String uuid = PreferencesUtil.getInstance(MainApplication.getInstance()).getUuid();
        String headerStr = "anonym=" + uuid;
        String sid = PreferencesUtil.getInstance(MainApplication.getInstance()).getSessionId();
        if (!TextUtils.isEmpty(sid))
            headerStr += ";sid=" + sid;
        String xLogId = PreferencesUtil.getInstance(MainApplication.getInstance()).getXLogId();
        String country = Locale.getDefault().getCountry();
        String lang = Locale.getDefault().getLanguage();
        Log.d("test", "header:" + headerStr);
        return builder.header("Authorization", headerStr).header("X-Log-Id", xLogId)
                .header("X-App-Version", BuildConfig.VERSION_NAME).header("X-Country", country).header("X-Lang", lang).header("X-Package-Name", MainApplication.getInstance().getPackageName());
    }

//    public static String createPublishedAtString(Context context, long publishedAt) {
//        DateTime now = DateTime.now();
//        long elapseSeconds = now.getMillis() / 1000 - publishedAt;
//        if (elapseSeconds < 0) {
//            elapseSeconds = 1;
//        }
//
//        if (elapseSeconds < 60 * 60) {//minutes
//            int minutes = (int) (elapseSeconds / 60);
//            if (minutes < 5) {
//                return context.getResources().getString(R.string.publish_in_5_minutes);
//            } else {
//                return String.valueOf(minutes) + context.getResources().getString(R.string.publish_minutes);
//            }
//        } else if (elapseSeconds < 60 * 60 * 24) {//hours
//            int hours = (int) (elapseSeconds / (60 * 60));
//            if (hours < 2) {
//                return String.valueOf(hours) + context.getResources().getString(R.string.publish_hour);
//            } else {
//                return String.valueOf(hours) + context.getResources().getString(R.string.publish_hours);
//            }
//        } else if (elapseSeconds < 60 * 60 * 24 * 7 * 2) {//days
//            int days = (int) (elapseSeconds / (60 * 60 * 24));
//            if (days < 2) {
//                return String.valueOf(days) + context.getResources().getString(R.string.publish_day);
//            } else {
//                return String.valueOf(days) + context.getResources().getString(R.string.publish_days);
//            }
//        } else if (elapseSeconds < 60 * 60 * 24 * 7 * 5) {//weeks
//            int weeks = (int) (elapseSeconds / (60 * 60 * 24 * 7));
//            if (weeks < 2) {
//                return String.valueOf(weeks) + context.getResources().getString(R.string.publish_week);
//            } else {
//                return String.valueOf(weeks) + context.getResources().getString(R.string.publish_weeks);
//            }
//        } else {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(publishedAt * 1000L);
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//
//            int yearNow = now.year().get();
//            int monthNow = now.monthOfYear().get();
//            if (year == yearNow || (yearNow == year + 1 && monthNow < month)) {//in 12 months
//                int months = monthNow - month;
//                if (months < 0) {
//                    months += 12;
//                }
//
//                if (months < 2) {
//                    return String.valueOf(months) + context.getResources().getString(R.string.publish_months);
//                } else {
//                    return String.valueOf(months) + context.getResources().getString(R.string.publish_months);
//                }
//            } else {//years
//                int years = yearNow - year;
//                if (years < 2) {
//                    return String.valueOf(years) + context.getResources().getString(R.string.publish_year);
//                } else {
//                    return String.valueOf(years) + context.getResources().getString(R.string.publish_years);
//                }
//            }
//        }
//    }

//    public static void toastCenter(Context context, String text) {
//        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }
//
//    public static void toastCenter(Context context, int resId) {
//        Toast toast = Toast.makeText(context, context.getResources().getString(resId), Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }

//    public static String formatVoteCount(Context context, int count) {
//        if (count > 999) {
//            return context.getResources().getString(R.string.comment_count_999);
//        } else if (count < 0) {
//            return "0";
//        } else {
//            return String.valueOf(count);
//        }
//    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return 0;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

}
