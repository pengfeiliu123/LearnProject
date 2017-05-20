package com.lpf.mvp.copywebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lpf.mvp.R;
import com.lpf.mvp.loadGif.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupengfei on 2017/5/16 14:16.
 */

public class GifAdapter extends BaseAdapter {

    public List<Feed> datas = new ArrayList<Feed>();
    public List<Feed> getDatas = new ArrayList<Feed>();
    private Context mContext;

    private boolean isFirstRun = true;


    private List<Feed> data;
    private int screenWidth;
    private int smallPicWidth;
    private int bigPicWidth;
    private int mixBigPicWidth;
    private float density;
    private int widthPadding = 10;

    public GifAdapter(Context context, List<Feed> list) {
        mContext = context;
        getDatas = list;

        for (int i = 0; i < getDatas.size(); i++) {
            if (getDatas.get(i).picList[0].full.endsWith(".gif")) {
                datas.add(getDatas.get(i));
            }
        }

        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        density = context.getResources().getDisplayMetrics().density;
        bigPicWidth = (int) (screenWidth - widthPadding * 2 * density);

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int currentPos;

    public void setCurrentPosition(int position) {
        currentPos = position;
    }

    public void setData(List<Feed> dataList) {
        this.datas = dataList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Feed item = datas.get(position);

//        Log.d("lpftag", "position：" + position);
        final ViewHolder holder;
        final Feed.PictureMeta imageUrl = datas.get(position).picList[0];
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gif_webview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (imageUrl.full != null && imageUrl.full.endsWith("gif")) {
            holder.imageIcon.setVisibility(View.VISIBLE);
        }

        int imageHeight = 200;
        int imageWidth = 200;

        if (item.picList != null && item.picList.length > 0) {
            imageWidth = bigPicWidth;
            imageHeight = (int) (getHWRatio(item, 0) * bigPicWidth);
            if (imageHeight > getMaxHeight() * density) {
                imageHeight = (int) (getMaxHeight() * density);
                imageWidth = (int) (imageHeight / getHWRatio(item, 0));
            }
        }

        final int coverHeight = imageHeight;
        final int coverWidth = imageWidth;

        ViewGroup.LayoutParams params = holder.imageCover.getLayoutParams();
        params.width = coverWidth;
        params.height = coverHeight;
        holder.imageCover.setLayoutParams(params);


//        Log.d("lpftag","tag:"+holder.imageIcon.getTag());
        Log.d("lpftag", "position:" + position + "-->url:" + imageUrl.full + " size:" + imageUrl.size);

        if (null != holder.imageIcon.getTag() && (imageUrl.full).equals(holder.imageIcon.getTag())) {

        } else {
            Log.d("lpftag", "load again");
            holder.imageIcon.setTag(imageUrl.for_list);


            if (imageUrl.full.endsWith("gif")) {
                if (position == 0) {

                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.imageIcon.setVisibility(View.INVISIBLE);
                    Glide.with(mContext).load(imageUrl.for_list).asBitmap()
                            .priority(Priority.HIGH).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(holder.imageCover);


                    //load webview
                    holder.imageView.loadUrl(imageUrl.full);
                    WebSettings mWebSetting = holder.imageView.getSettings();
                    mWebSetting.setUseWideViewPort(true);
                    mWebSetting.setLoadWithOverviewMode(true);
                    mWebSetting.setSupportZoom(false);
                    mWebSetting.setBuiltInZoomControls(false);
                    mWebSetting.setDisplayZoomControls(false);
                    mWebSetting.setLoadWithOverviewMode(true);

                    mWebSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    mWebSetting.setDomStorageEnabled(true); // 开启 DOM storage API 功能
                    mWebSetting.setDatabaseEnabled(true);   //开启 database storage API 功能
                    mWebSetting.setAppCacheEnabled(true);//开启 Application Caches 功能

                    holder.imageView.setWebChromeClient(new WebChromeClient() {
                        @Override
                        public void onProgressChanged(WebView view, int newProgress) {
                            super.onProgressChanged(view, newProgress);
                        }
                    });
                    holder.imageView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {

                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(coverWidth, coverHeight);
                            params.gravity = Gravity.CENTER;
                            holder.imageView.setLayoutParams(params);

                            holder.imageView.setVisibility(View.VISIBLE);
                            holder.imageCover.setVisibility(View.INVISIBLE);
                            holder.progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

//                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(coverWidth, coverHeight);
//                    params.gravity = Gravity.CENTER;
//                    holder.imageCover.setLayoutParams(params);


//                    Glide.with(mContext).load(imageUrl.full).priority(Priority.NORMAL).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                            .listener(new RequestListener<String, GlideDrawable>() {
//                                @Override
//                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                    holder.imageCover.setVisibility(View.INVISIBLE);
//                                    holder.progressBar.setVisibility(View.INVISIBLE);
//                                    return false;
//                                }
//                            }).into(holder.imageView);

                } else {
                    Glide.with(mContext).load(imageUrl.for_list).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(holder.imageCover);
                }
            }
        }

        return convertView;
    }

    protected float getHWRatio(Feed item, int index) {
        if (item.picList != null && item.picList.length > index) {
            float ratio = item.picList[index].getRatio();
            if (ratio > 0.1f)
                return ratio;
        }
        return getDefaultPicHWRatio(item, index);
    }

    protected float getDefaultPicHWRatio(Feed item, int index) {
        return 3.0f / 4;
    }

    protected int getMaxHeight() {
        return 340;
    }

    private void loadWebView() {


    }

    class ViewHolder {
        ImageView imageCover;
        //        ImageView imageView;
        WebView imageView;
        ImageView imageIcon;
        ProgressBar progressBar;

        View rootView;

        public ViewHolder(View view) {
            rootView = view;
            this.imageView = (WebView) view.findViewById(R.id.gif_img);
            this.progressBar = (ProgressBar) view.findViewById(R.id.gif_progress);
            this.imageCover = (ImageView) view.findViewById(R.id.gif_img_cover);
            this.imageIcon = (ImageView) view.findViewById(R.id.gif_icon);
        }

    }
}
