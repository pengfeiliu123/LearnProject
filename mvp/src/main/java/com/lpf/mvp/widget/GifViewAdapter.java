package com.lpf.mvp.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lpf.mvp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liupengfei on 2017/5/16 14:16.
 */

public class GifViewAdapter extends BaseAdapter {

    public List<Integer> datas = new ArrayList<Integer>();
    public List<Integer> gifViewPositions;
    public List<GifView> gifViews;
    private Context mContext;

    public GifViewAdapter(Context context, List<Integer> list) {
        mContext = context;
        datas = list;
        gifViews = new ArrayList<GifView>();
        gifViewPositions = new ArrayList<Integer>();

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

    public void setData(List<Integer> dataList) {
        this.datas = dataList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.d("lpftag", "position：" + position);
        final ViewHolder holder;
//        Integer.PictureMeta imageUrl = datas.get(position).picList[0];

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gifview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!gifViewPositions.contains(position)) {
            gifViewPositions.add(position);
            gifViews.add(holder.imageView);
        }

//        if(imageUrl.full!=null && imageUrl.full.endsWith("gif")){
//            holder.imageIcon.setVisibility(View.VISIBLE);
//        }

//        Log.d("lpftag","tag:"+holder.imageIcon.getTag());
//        Log.d("lpftag", "url:" + imageUrl.full+" size:"+imageUrl.size);

//        if (null != holder.imageIcon.getTag() && (imageUrl.for_list.equals(holder.imageIcon.getTag()))) {

//        } else {
//            holder.imageIcon.setTag(imageUrl.for_list);
        Glide.with(mContext).load(datas.get(position)).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageCover);
//        }


        return convertView;
    }

    class ViewHolder {
        ImageView imageCover;
        GifView imageView;
        ImageView imageIcon;
        ProgressBar progressBar;
        View rootView;

        public ViewHolder(View view) {
            rootView = view;
            this.imageView = (GifView) view.findViewById(R.id.gif_img);
            this.progressBar = (ProgressBar) view.findViewById(R.id.gif_progress);
            this.imageCover = (ImageView) view.findViewById(R.id.gif_img_cover);
            this.imageIcon = (ImageView) view.findViewById(R.id.gif_icon);
        }

    }
    public void resetGifView(int position) {
        for (int i = 0; i < gifViews.size(); i++) {

            if(i!=position){
                if(gifViews.get(i).isPlaying()){
                    gifViews.get(i).pause();
                }
            }else{
                if(gifViews.get(i).isPaused()){
                    gifViews.get(i).play();
                }
            }
        }
    }

    public void startAll(){
        for (int i = 0; i < gifViews.size(); i++) {
            if(gifViews.get(i).isPaused()){
                gifViews.get(i).play();
            }else{
                gifViews.get(i).setGifResource(datas.get(i));
            }
        }
    }
}
