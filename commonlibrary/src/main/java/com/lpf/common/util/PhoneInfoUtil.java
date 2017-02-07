package com.lpf.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class PhoneInfoUtil {

	public static TelephonyManager getManager(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return manager;
	}

	/**
	 * 获取IMEI号，IESI号，手机型号
	 */
	public static String getImei(Context context) {

		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTm.getDeviceId();
	}

	/**
	 * 手机型号
	 * */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * // 手机品牌
	 *
	 * @return
	 */
	public static String getBrand() {
		return Build.BRAND;
	}

	/**
	 * 
	 * @param context
	 * @return 获取androidID这个是可以改变的。
	 */
	public static String getAndroidID(Context context) {
		return android.provider.Settings.System.getString(
				context.getContentResolver(), "android_id");
	}

	/**
	 * 获取手机终端WIFI MAC地址
	 * 
	 * @param context
	 *            应用上下文
	 * @return String 手机终端WIFI MAC地址
	 */
	public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wifi.getConnectionInfo().getMacAddress();
	}

	/**
	 * 获取设备ID,例如手机的MEID号(CDMA),或者IMEI号(GSM)
	 * 
	 * @param context
	 *            应用上下文
	 * @return 返回设备ID
	 */
	public static String getDeviceID(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 获取手机的android版本号
	 * @return
	 */
	public static String getAndroidVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
	}
	
	public static String getWifiNumber(Context context){
		try  
		{  
		    List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
		              
		    for (NetworkInterface networkInterface : networkInterfaces)
		    {  
		        String displayName = networkInterface.getDisplayName();
		    }  
		}  
		catch (SocketException e)
		{  
		    e.printStackTrace();  
		}  
		return "";
	}
	
	//判断是否是平板
	public static boolean isTablet(Context context) {
	     return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	//获取Dpi
	public static int getDensityDpi(Context context){
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    return metrics.densityDpi; 
	}
	
	public static int getPixelsWidth(Context context){
		DisplayMetrics metrics=new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}
	
	public static int getPixelsHeight(Context context){
		DisplayMetrics metrics=new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
	
	public static int getPixelsById(Context context, int id){
		 return (int) ((Activity)context).getResources().getDimension(id);
	}
}
