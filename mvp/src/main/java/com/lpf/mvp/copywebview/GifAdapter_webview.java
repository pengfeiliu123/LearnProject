package com.lpf.mvp.copywebview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lpf.mvp.R;
import com.lpf.mvp.loadGif.Feed;
import com.lpf.mvp.widget.MyWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupengfei on 2017/5/16 14:16.
 */

public class GifAdapter_webview extends BaseAdapter {

    public List<Feed> datas = new ArrayList<Feed>();
    private Context mContext;

    public List<Integer> gifViewPositions;
    public List<MyWebView> gifViews;
    public List<ImageView> gifCovers;

    private boolean isFirstRun = true;

    public GifAdapter_webview(Context context, List<Feed> list) {
        mContext = context;
        datas = list;

        gifViewPositions = new ArrayList<Integer>();
        gifViews = new ArrayList<MyWebView>();
        gifCovers = new ArrayList<ImageView>();

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

//        Log.d("lpftag","tag:"+holder.imageIcon.getTag());
//        Log.d("lpftag", "url:" + imageUrl.full+" size:"+imageUrl.size);

        Object tag = holder.imageCover.getTag();

        holder.imageView.setTag(imageUrl.full);
        holder.imageCover.setTag(imageUrl.for_list);

//        Log.d("lpftag","url:"+)

        if (holder.imageCover.getTag() != null && holder.imageCover.getTag().equals(imageUrl.for_list)) {
            Glide.with(mContext).load(imageUrl.for_list).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.imageCover);
        }

        //防止Gif图播放错位
        if (holder.imageView.getTag() != null && holder.imageView.getTag().equals(imageUrl.full)) {
            holder.imageView.loadUrl(imageUrl.full);
//            holder.imageCover.setVisibility(View.GONE);
        }


//        if (null != tag && (imageUrl.for_list.equals(tag))) {
//
//        } else {
//            holder.imageIcon.setTag(imageUrl.for_list);
//            holder.imageView.setTag(imageUrl.for_list);
//            holder.imageCover.setTag(imageUrl.for_list);
//            Log.d("lpftag","set tag ,load cover");
//            Glide.with(mContext).load(imageUrl.for_list).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(holder.imageCover);
//        }


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();


                if(holder.imageView.getTag()!=null && holder.imageView.getTag().equals(imageUrl.full)){
                    Log.d("lpftag","click this ok");
//                    Glide.with(mContext).load(imageUrl.full).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                            .into(holder.imageView);

                    holder.imageView.loadUrl(imageUrl.full);
                    holder.imageView.getSettings().setLoadWithOverviewMode(true);
                    holder.imageView.getSettings().setUseWideViewPort(true);

                    holder.imageCover.setVisibility(View.INVISIBLE);
                    holder.imageView.setVisibility(View.VISIBLE);
                }else{
                    Log.d("lpftag","click this not ok");
                }

//                gifViews.get(position).setVisibility(View.VISIBLE);
//                gifCovers.get(position).setVisibility(View.GONE);
//                if(holder.imageView.getTag().equals(imageUrl.full)){
////                    Log.d("lpftag","click tag is equal!!");
//                    Glide.with(mContext).load(imageUrl.full).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                            .into(holder.imageView);
//                    holder.imageView.setVisibility(View.VISIBLE);
//                    holder.imageCover.setVisibility(View.INVISIBLE);
//                }else{
////                    Log.d("lpftag","click tag is not equal!!");
//                }
            }
        });


        if (!gifViewPositions.contains(position)) {
            gifViewPositions.add(position);
            gifViews.add(holder.imageView);
            gifCovers.add(holder.imageCover);
        }

        return convertView;
    }

//    private void chooseCurrent(ViewHolder holder, Feed.PictureMeta imageUrl) {
//        if (null != tag && (imageUrl.for_list.equals(tag))) {
//
//        } else {
//
//        }
//
//    }

    class ViewHolder {
        ImageView imageCover;
        MyWebView imageView;
        ImageView imageIcon;
        ProgressBar progressBar;

        View rootView;

        public ViewHolder(View view) {
            rootView = view;
            this.imageView = (MyWebView) view.findViewById(R.id.gif_img);
            this.progressBar = (ProgressBar) view.findViewById(R.id.gif_progress);
            this.imageCover = (ImageView) view.findViewById(R.id.gif_img_cover);
            this.imageIcon = (ImageView) view.findViewById(R.id.gif_icon);
        }

    }


    public void resetAll() {
        for (int i = 0; i < gifViews.size(); i++) {
            gifCovers.get(i).setVisibility(View.VISIBLE);
            gifViews.get(i).setVisibility(View.GONE);

//            gifCovers.get(i).setVisibility(View.GONE);
//            gifViews.get(i).setVisibility(View.VISIBLE);

//            Glide.with(mContext).load(imageUrl.for_list).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(holder.imageCover);

//            if (i != position) {
////                Glide.with(mContext).load(datas.get(i).picList[0].for_list).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
////                        .into(gifViews.get(i));
//                gifCovers.get(position).setVisibility(View.VISIBLE);
////                gifViews.get(position).setVisibility(View.GONE);
//
//            }
        }
    }

    public void chooseCurrent(int position) {
        Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
        resetAll();

//
//        if (holder.imageView.getTag() != null && holder.imageView.getTag().equals(imageUrl.full)) {
//            Log.d("lpftag", "click this ok");
//            Glide.with(mContext).load(imageUrl.full).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(holder.imageView);
//            holder.imageCover.setVisibility(View.INVISIBLE);
//            holder.imageView.setVisibility(View.VISIBLE);
//        } else {
//            Log.d("lpftag", "click this not ok");
//        }

//                gifViews.get(position).setVisibility(View.VISIBLE);
//                gifCovers.get(position).setVisibility(View.GONE);
//                if(holder.imageView.getTag().equals(imageUrl.full)){
////                    Log.d("lpftag","click tag is equal!!");
//                    Glide.with(mContext).load(imageUrl.full).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                            .into(holder.imageView);
//                    holder.imageView.setVisibility(View.VISIBLE);
//                    holder.imageCover.setVisibility(View.INVISIBLE);
//                }else{
////                    Log.d("lpftag","click tag is not equal!!");
//                }
    }
}
