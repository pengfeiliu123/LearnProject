package com.lpf.mvp.loadGif;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.lpf.mvp.MainApplication;
import com.lpf.mvp.NGCommonConfiguration;
import com.lpf.mvp.R;
import com.lpf.mvp.base.BaseActivity;
import com.lpf.mvp.common.Util;
import com.lpf.mvp.drawable.AlxGifHelper;
import com.lpf.mvp.drawable.LoadingGifHelper;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.droidsonroids.gif.GifImageView;

import static android.R.attr.duration;
import static android.R.id.list;
import static com.lpf.mvp.R.attr.gif;
import static com.lpf.mvp.R.id.image;
import static com.lpf.mvp.R.id.progressBar;
import static com.lpf.mvp.common.Util.ignoreTrust;

/**
 * Created by liupengfei on 2017/5/16 14:12.
 */

public class GifActivity extends BaseActivity<GifPresenter, GifModel> implements GifContract.View {

    @BindView(R.id.gif_list)
    ListView gifList;

    GifAdapter mAdapter;
    @BindView(R.id.positionTop)
    TextView positionTop;
    int pos = 0;

    private List<Feed> datas = new ArrayList<Feed>();

    @Override
    public void setListData(List<Feed> listData) {
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).picList[0].full.endsWith(".gif")) {
                datas.add(listData.get(i));
            }
        }
        datas.get(0).picList[0].full = "https://d1d7glqtmsbpdn.cloudfront.net/full/3dcfe0e1e6fd7e59af1dce3d6bd20e3409aff10b.gif";
        datas.get(0).picList[0].for_list = "https://d1d7glqtmsbpdn.cloudfront.net/full/3dcfe0e1e6fd7e59af1dce3d6bd20e3409aff10b.jpg";
//        datas = listData;
        mAdapter = new GifAdapter(this, datas);

        gifList.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void success() {

    }

    @Override
    public void failed() {

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_gif;
    }

    @Override
    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    public int firstVisible = 0, visibleCount = 0, totalCount = 0;

    @Override
    protected void initView() {

        mPresenter.loadGif();


        gifList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止时
                        pos = gifList.getChildAt(0).getTop();
                        positionTop.setText("显示的第一个Item在ListView中的坐标:" + pos);
                        Glide.with(MainApplication.getInstance()).resumeRequests();

                        autoPlayGif(view);

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //正在滚动时
                        positionTop.setText("正在滑动...");
//
                        Glide.with(MainApplication.getInstance()).resumeRequests();

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        //手指抛动时，手指用力滑动后,手指离开屏幕ListView由于惯性继续滑动
                        positionTop.setText("漂移中...");
                        Glide.with(MainApplication.getInstance()).pauseRequests();
                        resetAllItemsStatus(view);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
                totalCount = totalItemCount;

            }
        });
    }

    private void autoPlayGif(final AbsListView view) {

        Log.d("lpftag", "firstVisibleItem:" + firstVisible + "-->visibleItemCount：" + visibleCount);

        resetAllItemsStatus(view);


        boolean isLastLoadComplete = false;
        if (firstVisible + visibleCount == totalCount) {
            final View bottomChildView = view.getChildAt(view.getChildCount() - 1);
            if (bottomChildView != null && bottomChildView.getBottom() == view.getHeight()) {
                isLastLoadComplete = true;
            }
        }
        Log.d("lpftag", "isLastLoadComplete:" + isLastLoadComplete);

        if (isLastLoadComplete) {
            final View bottomChildView = view.getChildAt(view.getChildCount() - 1);
            if (view != null && bottomChildView != null && bottomChildView.findViewById(R.id.gif_img) != null) {
                final GifImageView imageView = (GifImageView) bottomChildView.findViewById(R.id.gif_img);
                final ProgressWheel imageProgress = (ProgressWheel) bottomChildView.findViewById(R.id.gif_progress);
                final ImageView imageIcon = (ImageView) bottomChildView.findViewById(R.id.gif_icon);
                final ImageView imageCover = (ImageView) bottomChildView.findViewById(R.id.gif_img_cover);
                final TextView tvProgress = (TextView) bottomChildView.findViewById(R.id.gif_progress_value);

                imageProgress.setVisibility(View.VISIBLE);
                imageCover.setVisibility(View.VISIBLE);
                imageIcon.setVisibility(View.INVISIBLE);
                tvProgress.setVisibility(View.VISIBLE);

                LoadingGifHelper.displayImage(datas.get(datas.size() - 1).picList[0].full,
                        imageView,
                        imageCover,
                        imageIcon,
                        imageProgress,
                        tvProgress,
                        700
                );
            }
        } else {
            //加载中间的第一个
            boolean isRunning = false;
            for (int i = 0; i < visibleCount; i++) {
                if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.gif_img) != null) {
                    final GifImageView imageView = (GifImageView) view.getChildAt(i).findViewById(R.id.gif_img);
                    final ImageView imageCover = (ImageView) view.getChildAt(i).findViewById(R.id.gif_img_cover);
                    final ProgressWheel imageProgress = (ProgressWheel) view.getChildAt(i).findViewById(R.id.gif_progress);
                    final ImageView imageIcon = (ImageView) view.getChildAt(i).findViewById(R.id.gif_icon);
                    final TextView tvProgress = (TextView)view.getChildAt(i).findViewById(R.id.gif_progress_value);
                    Rect rect = new Rect();
                    imageView.getLocalVisibleRect(rect);
                    int height = imageView.getHeight();
                    Log.d("lpftag", "i=" + i + "====" + "height=" + height + "===rect.top:" + rect.top + "===rect.bottom:" + rect.bottom);
                    if (rect.top == 0 && rect.bottom == height) {
                        Log.d("lpftag", "full url:" + datas.get(firstVisible + i).picList[0].full);
                        imageCover.setVisibility(View.VISIBLE);
                        imageProgress.setVisibility(View.VISIBLE);
                        imageIcon.setVisibility(View.INVISIBLE);
                        tvProgress.setVisibility(View.VISIBLE);

                        LoadingGifHelper.displayImage(datas.get(firstVisible + i).picList[0].full,
                                imageView,
                                imageCover,
                                imageIcon,
                                imageProgress,
                                tvProgress,
                                700
                        );
                        isRunning = true;
                        return;
                    }
                }
            }
            //如果都没有合适位置，默认运行第一张（两张图大小拼在一起比屏幕还高）
            if (!isRunning) {
                Log.d("lpftag", "默认运行第一张");
                if (view != null && view.getChildAt(0) != null && view.getChildAt(0).findViewById(R.id.gif_img) != null) {
                    final GifImageView imageView = (GifImageView) view.getChildAt(0).findViewById(R.id.gif_img);
                    final ProgressWheel imageProgress = (ProgressWheel) view.getChildAt(0).findViewById(R.id.gif_progress);
                    final ImageView imageIcon = (ImageView) view.getChildAt(0).findViewById(R.id.gif_icon);
                    final ImageView imageCover = (ImageView) view.getChildAt(0).findViewById(R.id.gif_img_cover);
                    final TextView tvProgress = (TextView) view.getChildAt(0).findViewById(R.id.gif_progress_value);

                    imageProgress.setVisibility(View.VISIBLE);
                    imageIcon.setVisibility(View.INVISIBLE);
                    imageCover.setVisibility(View.VISIBLE);
                    tvProgress.setVisibility(View.VISIBLE);

                    LoadingGifHelper.displayImage(datas.get(firstVisible).picList[0].full,
                            imageView,
                            imageCover,
                            imageIcon,
                            imageProgress,
                            tvProgress,
                            700
                    );
                }
            }
        }
    }

    //reset all items
    private void resetAllItemsStatus(AbsListView view) {
        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.gif_img) != null) {
                GifImageView imageView = (GifImageView) view.getChildAt(i).findViewById(R.id.gif_img);
                ImageView imageCover = (ImageView) view.getChildAt(i).findViewById(R.id.gif_img_cover);
                final ProgressWheel imageProgress = (ProgressWheel) view.getChildAt(i).findViewById(R.id.gif_progress);
                final ImageView imageIcon = (ImageView) view.getChildAt(i).findViewById(R.id.gif_icon);
                final TextView tvProgress = (TextView) view.getChildAt(i).findViewById(R.id.gif_progress_value);

                imageCover.setVisibility(View.VISIBLE);
                imageIcon.setVisibility(View.VISIBLE);

                imageView.setVisibility(View.INVISIBLE);
                tvProgress.setVisibility(View.INVISIBLE);
                imageProgress.setVisibility(View.INVISIBLE);

            }
        }
    }

    @Override
    protected void initListener() {
    }

}
