/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:Lenovo
 */
package com.lpf.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-7-8
 * @Version:1.1.0
 */
public class CookieUtil {

	public  static Map<String, String> cookieMap;

	// 获取本机Cookie
	public	static HashMap<String, String> getCookie(Context mContext) {
		cookieMap = new HashMap<String, String>();
		//The CookieSyncManager is used to synchronize the browser cookie store 
		//between RAM and permanent storage. To get the best performance, browser cookies are saved in RAM. A separate thread saves the cookies between, driven by a timer. 
//		CookieSyncManager.createInstance(mContext);
//		CookieManager cookieManager = CookieManager.getInstance();
//		String cookieStr = cookieManager.getCookie(Global.PERSONAL_LOGIN);
		String cookieStr = CookieUtil.getAPPCookie(mContext);
		if (cookieStr!=null) {
//			CookieUtil.saveAPPCookie(mContext, cookieStr);
			Log.e("c2ccookie", cookieStr);
		}else {
			Log.e("c2ccookie", "c2c cookie is null");
		}
//		String cookieStr = cookieManager.getCookie("http://www.baidu.com");
		// 如果cookie存在
		if (cookieStr != null&&!cookieStr.equals("")) {
//			System.out.println(cookieStr);
			String[] cookieVal = cookieStr.split(";");
			for (String val : cookieVal) {
				int index = val.indexOf("=");
				if (index != -1) {
					String key = val.substring(0, index);
					String value = val.substring(index + 1, val.length());
					cookieMap.put(key.trim(), value.trim());
//					System.out.println("key:" + key + " value:" + value);
				}
			}
			return (HashMap<String, String>) cookieMap;
		} else {
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * @param cookieStr
	 * @param type  类型比如 lenovoId, loginName, memberId等
	 * lenovoId:11:10045183470|loginName:21:liupf5%40lenovo%2ecom|memberId:44:c8e2d01b%2dcc15%2d11e4%2d924a%2dfa163ebedc0f|groupCode:1:7|isLenovo:4:true
	 * @return
	 * @Description:
	 */
	public static String getLenovoNameFromCookie(String cookieStr, String type) {
		cookieMap = new HashMap<String, String>();
		String typeStr = "";
		
		if (cookieStr != null) {
			Log.e("testweixin", cookieStr);
			System.out.println(cookieStr);
			String[] cookieVal = cookieStr.split(";");
			for (String val : cookieVal) {
				int index = val.indexOf("=");
				if (index != -1) {
					String key = val.substring(0, index);
					String value = val.substring(index + 1, val.length());
					cookieMap.put(key.trim(), value.trim());
					System.out.println("key:" + key + " value:" + value);
				}
			}
			
			String requestStr = cookieMap.get("cerpreg-passport");
			if(null != requestStr && requestStr.length() >0){
				String[] array = requestStr.split("\\|");
					if(array!=null && array.length>0){
						for(int i =0; i<array.length; i++){
							//否则判断是否含有lenovoId字符串
							String requestInfo = Base64Util.decode(array[i]);
							//如果不包含，则判断下一个
							if(!requestInfo.contains("lenovoId")){
								continue;
							}else{
								//如果包含，则对该字符串进行分割，提取LoginName
								String[] infos = requestInfo.split("\\|");
								if(infos!=null && infos.length>0){
									

									for(int u=0; u<infos.length;u++){
										//如果包含loginName
										if(infos[u].contains(type)){
											int begin = infos[u].lastIndexOf(":");
											String name = infos[u].substring(begin+1);
											try {
												typeStr = URLDecoder.decode(name,"utf-8");
											} catch (UnsupportedEncodingException e) {
												e.printStackTrace();
											}
										}
									}
										if (typeStr != null) {	
											return typeStr;
										}else{
											typeStr="";
										//否则未登录
									}
											
								}
							}
						}
					}
				}
			
		}
		
		return typeStr;
		
	}

	/**
	 * 将Cookie中cerpreg-passport字段解码到map中
	 * @param cookieStr
	 * @return
	 */
	public static ArrayMap<String,String> getCerpCookieMap(String cookieStr) {
		ArrayMap<String,String> cerpCookieMap = new ArrayMap<String,String>();
		cookieMap = new HashMap<String, String>();
		String cerpKey = "";
		String cerpValue = "";

		if (cookieStr != null) {
			System.out.println(cookieStr);
			String[] cookieVal = cookieStr.split(";");
			for (String val : cookieVal) {
				int index = val.indexOf("=");
				if (index != -1) {
					String key = val.substring(0, index);
					String value = val.substring(index + 1, val.length());
					cookieMap.put(key.trim(), value.trim());
					System.out.println("key:" + key + " value:" + value);
				}
			}

			String requestStr = cookieMap.get("cerpreg-passport");
			if(null != requestStr && requestStr.length() >0){
				String[] array = requestStr.split("\\|");
				if(array!=null && array.length>0){
					for(int i =0; i<array.length; i++){
						//否则判断是否含有lenovoId字符串
						String requestInfo = Base64Util.decode(array[i]);
						//如果不包含，则判断下一个
						if(!requestInfo.contains("lenovoId")){
							continue;
						}else{
							//如果包含，则对该字符串进行分割，提取LoginName
							String[] infos = requestInfo.split("\\|");
							if(infos!=null && infos.length>0){

								for(int u=0; u<infos.length;u++){
									//如果包含loginName
									int begin = infos[u].lastIndexOf(":");
									cerpKey = infos[u].substring(0,infos[u].indexOf(":"));
									String name = infos[u].substring(begin+1);
									try {
										cerpValue = URLDecoder.decode(name,"utf-8");
										cerpCookieMap.put(cerpKey,cerpValue);
									} catch (UnsupportedEncodingException e) {
										e.printStackTrace();
									}
								}
								return cerpCookieMap;
							}
						}
					}
				}
			}
		}
		return cerpCookieMap;
	}
	
	
	// 获取本机Cookie
		public	static void saveAndGetC2CCookie(Context mContext) {
			CookieSyncManager.createInstance(mContext);
			CookieManager cookieManager = CookieManager.getInstance();
			String cookieStr = cookieManager.getCookie("www.baidu.com");		// change this for use
			if (cookieStr!=null) {
				CookieUtil.saveAPPCookie(mContext, cookieStr);
				Log.e("c2ccookie", cookieStr);
			}else {
				Log.e("c2ccookie", "c2c cookie is null");
			}
		}
	
	
	public static  void saveAPPCookie(Context mContext, String cookie) {
        //保存登录信息，下次无需登录
        String PREFS_NAME = "lenovomallcookie";
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("cookie", cookie);
        editor.commit();
	}


	public static String getAPPCookie(Context mContext) {
        //保存登录信息，下次无需登录
        String PREFS_NAME = "lenovomallcookie";
        SharedPreferences cookiePrefs = mContext.getSharedPreferences(PREFS_NAME, 0);
        // Load any previously stored cookies into the store
        String storedCookieNames = cookiePrefs.getString("cookie", "");
        return storedCookieNames;
	}

	public static Boolean getCookieIsValid(Context mContext) {
        //保存登录信息，下次无需登录  
        String PREFS_NAME = "cookieisvalid";
        SharedPreferences cookiePrefs = mContext.getSharedPreferences(PREFS_NAME, 0);
        // Load any previously stored cookies into the store  
        Boolean storedCookieNames = cookiePrefs.getBoolean("value", false);
        return storedCookieNames;
	}
	
	
	public static  void deleteLocalCookie(Context mContext) {
        //保存登录信息，下次无需登录  
        //保存登录信息，下次无需登录  
        String PREFS_NAME = "lenovomallcookie";
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("cookie", "");  
        editor.commit(); 
	}
	
	
	public static String getCookieValue(String inputKey){
		cookieMap = new HashMap<String, String>();
		CookieManager cookieManager = CookieManager.getInstance();
		String cookieStr = cookieManager.getCookie("www.baidu.com");			// change this place for use
		// 如果cookie存在
		if (cookieStr != null) {
			System.out.println(cookieStr);
			String[] cookieVal = cookieStr.split(";");
			for (String val : cookieVal) {
				int index = val.indexOf("=");
				if (index != -1) {
					String key = val.substring(0, index);
					String value = val.substring(index + 1, val.length());
					cookieMap.put(key.trim(), value.trim());
					System.out.println("key:" + key + " value:" + value);
				}
			}
			return cookieMap.get(inputKey);
		} else {
			return null;
		}
	}
	
	
	
	public static String getSpecificCookieValue(String keyvalue, String cookie){
		cookieMap = new HashMap<String, String>();
//		CookieManager cookieManager = CookieManager.getInstance();
//		String cookieStr = cookieManager.getCookie(Global.PERSONAL_ALL_PAY);
		// 如果cookie存在
		if (cookie != null) {
//			System.out.println(cookie);
			String[] cookieVal = cookie.split(";");
			for (String val : cookieVal) {
				int index = val.indexOf("=");
				if (index != -1) {
					String key = val.substring(0, index);
					String value = val.substring(index + 1, val.length());
					cookieMap.put(key.trim(), value.trim());
//					System.out.println("key:" + key + " value:" + value);
				}
			}
			return cookieMap.get(keyvalue);
		} else {
			return "";
		}
	}
	
	
	
	// 获取本机Cookie
		public	static HashMap<String, String> getCookieForThinkPad(Context mContext, String urlString) {
			cookieMap = new HashMap<String, String>();
			CookieSyncManager.createInstance(mContext);
			CookieManager cookieManager = CookieManager.getInstance();
			String cookieStr;
			if (urlString.equals("")) {
				 cookieStr = cookieManager.getCookie("www.baidu.com");			//change this for use
				 CookieUtil.saveAPPCookie(mContext, cookieStr);
			}else {
				 cookieStr = cookieManager.getCookie(urlString);
				 CookieUtil.saveAPPCookie(mContext, cookieStr);
			}
//			String cookieStr = cookieManager.getCookie("http://www.baidu.com");
			// 如果cookie存在
			if (cookieStr != null) {
				System.out.println(cookieStr);
				String[] cookieVal = cookieStr.split(";");
				for (String val : cookieVal) {
					int index = val.indexOf("=");
					if (index != -1) {
						String key = val.substring(0, index);
						String value = val.substring(index + 1, val.length());
						cookieMap.put(key.trim(), value.trim());
						System.out.println("key:" + key + " value:" + value);
					}
				}
				return (HashMap<String, String>) cookieMap;
			} else {
				return null;
			}
		}
	
	// 获取本机Cookie中的自动登录的参数字符串
	public static String doAutoLogin(Context mContext) {
		cookieMap = CookieUtil.getCookie(mContext);
		String autoLoginStr = "";
		// Cookie不为空
		if (cookieMap != null) {
			// 三个关键字不为空
			if (cookieMap.get("JSESSIONID") != null && cookieMap.get("B2CUN") != null && cookieMap.get("B2CKEY") != null) {
				autoLoginStr = "JSESSIONID=" + cookieMap.get("JSESSIONID") + ";B2CUN=" + cookieMap.get("B2CUN")
						+ ";B2CKEY=" + cookieMap.get("B2CKEY");
			}
			return autoLoginStr;
		} else {
			return autoLoginStr;
		}
	}

//	public static  String getAPPCookie(Context mContext) {
//		//保存登录信息，下次无需登录
//		String PREFS_NAME = "lenovomallcookie";
//		SharedPreferences cookiePrefs = mContext.getSharedPreferences(PREFS_NAME, 0);
//		// Load any previously stored cookies into the store
//		String storedCookieNames = cookiePrefs.getString("cookie", null);
//		return storedCookieNames;
//	}
//
//	public static  void saveAPPCookie(Context mContext, String cookie) {
//		//保存登录信息，下次无需登录
//		String PREFS_NAME = "lenovomallcookie";
//		SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
//		SharedPreferences.Editor editor = settings.edit();
//		editor.putString("cookie", cookie);
//		editor.commit();
//	}
	
	//清除所有Cookie
	public static void clearAllCookie(){
//		CookieSyncManager.createInstance(mContext);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}
}
