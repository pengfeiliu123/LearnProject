package com.lpf.mvp.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.lpf.mvp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GifViewActivity extends AppCompatActivity {

    @BindView(R.id.gifview_list)
    ListView gifViewList;

    GifViewAdapter mAdapter;

    List<Integer> datas = new ArrayList<Integer>();
    @BindView(R.id.btnStop)
    Button btnStop;
    @BindView(R.id.btnStart)
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_view);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

        datas.add(0, R.mipmap.gif1);
        datas.add(1, R.mipmap.gif2);
        datas.add(2, R.mipmap.gif3);
        datas.add(3, R.mipmap.gif4);
        datas.add(4, R.mipmap.gif5);
        datas.add(5, R.mipmap.gif6);
        datas.add(6, R.mipmap.gif7);
        datas.add(7, R.mipmap.gif8);
        datas.add(8, R.mipmap.gif9);

        mAdapter = new GifViewAdapter(this, datas);

        gifViewList.setAdapter(mAdapter);

        gifViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final GifView imageView = (GifView) view.findViewById(R.id.gif_img);
                final ImageView imageCover = (ImageView) view.findViewById(R.id.gif_img_cover);
                final ImageView imageIcon = (ImageView) view.findViewById(R.id.gif_icon);
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.gif_progress);
                imageIcon.setVisibility(View.GONE);

                progressBar.setVisibility(View.VISIBLE);

//                Glide.with(GifViewActivity.this)
//                        .load(datas.get(position))
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .into(new GlideDrawableImageViewTarget(imageView,1));

                mAdapter.resetGifView(position);
                imageView.setGifResource(datas.get(position));



//                String url = datas.get(position).picList[0].full;
//                if (url.endsWith("gif")) {
////                    Glide.with(GifActivity.this).load(datas.get(position).picList[0].full).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new GlideDrawableImageViewTarget(imageView,1));
//
//                    Glide.with(GifActivity.this).load(datas.get(position).picList[0].full).asGif().dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                            .listener(new RequestListener<String, GifDrawable>() {
//                                @Override
//                                public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
//                                    Toast.makeText(mActivity, "load exception" + e.toString(), Toast.LENGTH_SHORT).show();
//                                    progressBar.setVisibility(View.GONE);
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
////                                        // 计算动画时长
////                                        GifDrawable drawable = (GifDrawable) resource;
////                                        GifDecoder decoder = drawable.getDecoder();
////                                        for (int i = 0; i < drawable.getFrameCount(); i++) {
////                                            duration += decoder.getDelay(i);
////                                        }
////                                        //发送延时消息，通知动画结束
////                                        //以下两个参数都是 int 型，记得如上的声明
////                                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
////                                                duration);
//                                    imageCover.setVisibility(View.GONE);
//                                    progressBar.setVisibility(View.GONE);
//                                    resource.setLoopCount(2);
//                                    imageView.setImageDrawable(resource);
//                                    return false;
//                                }
//                            })
//                            .into(imageView);
//
//
//                } else if (url.endsWith("mp4")) {
//                    Toast.makeText(mActivity, "this is mp4 " + datas.get(position).picList[0].full, Toast.LENGTH_SHORT).show();
//                    Glide.with(GifActivity.this).load(datas.get(position).picList[0].for_list).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
//                } else if (url.endsWith("jpg")) {
//                    Toast.makeText(mActivity, "this is jpg " + datas.get(position).picList[0].full, Toast.LENGTH_SHORT).show();
//                    Glide.with(GifActivity.this).load(datas.get(position).picList[0].for_list).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
//                }
            }
        });
    }


    @OnClick({R.id.btnStart, R.id.btnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                mAdapter.startAll();
                break;
            case R.id.btnStop:
                mAdapter.resetGifView(0);
                break;
        }
    }
}
