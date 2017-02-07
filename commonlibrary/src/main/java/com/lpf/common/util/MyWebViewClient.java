package com.lpf.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
//	private CustomProgressDialog dialog;
	private Activity mActivity;
	public MyWebViewClient(Activity activity){
		mActivity = activity;
	}
	
//	protected void showDialog(){
//		if(dialog==null){
//			dialog = new CustomProgressDialog(mActivity, R.style.Translucent_NoTitle);
//			dialog.setCancelable(true);
//		}
//		if(!dialog.isShowing() && !mActivity.isDestroyed()){
//			dialog.show();
//		}
//	}
//	public void closeDialog(){
//		if(dialog!=null&& dialog.isShowing() && !mActivity.isDestroyed()) {
//			dialog.dismiss();
//		}
//	}
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
//		if(mActivity!=null){
//			showDialog();
//		}
	}
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
//		if(mActivity!=null){
//			closeDialog();
//		}
	}
	
//	@Override
//	public void doUpdateVisitedHistory(WebView view, String url,
//			boolean isReload) {
//		view.loadUrl(url);
//		super.doUpdateVisitedHistory(view, url, isReload);
//	}
	
//	@Override
//	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//		System.out.println("111111");
//		return false;
//	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (url.startsWith("tel:")) {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri
					.parse(url));
			mActivity.startActivity(intent);
			return true;
		}else if(url.startsWith("mailto:")){
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri
					.parse(url));
			mActivity.startActivity(intent);
			return true;
		}
//		else if(url.startsWith("http://") || url.startsWith("file://") || url.startsWith("https://")){
//			view.loadUrl(url);
//		}
		return false;
	}
	@Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);		
		view.loadUrl("file:///android_asset/networkerror/index.html");
		view.addJavascriptInterface(new NetworkErrorJsInterface(view,failingUrl), "webviewInterface");
    }
	class NetworkErrorJsInterface{
		WebView view;
		String failingUrl;
		public NetworkErrorJsInterface(WebView view, String failingUrl){
			this.view=view;
			this.failingUrl=failingUrl;
		}		
		@JavascriptInterface
		public void refresh(){
			view.loadUrl(failingUrl);
		}
		@JavascriptInterface
		public void setNetwork(){
			Intent intent=null;
            //判断手机系统的版本  即API大于10 就是3.0或以上版本 
            if(android.os.Build.VERSION.SDK_INT>10){
//                intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            }else{
                intent = new Intent();
                ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            mActivity.startActivity(intent);						
		}		
	}
	
	
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){

        //handler.cancel(); // Android默认的处理方式
        handler.proceed();  // 接受所有网站的证书

    }
	
}
