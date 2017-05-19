package com.lpf.mvp.loadGif;

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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.lpf.mvp.R;
import com.lpf.mvp.widget.GifView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.tag;
import static android.media.CamcorderProfile.get;
import static com.lpf.mvp.R.attr.gif;

/**
 * Created by liupengfei on 2017/5/16 14:16.
 */

public class GifAdapter extends BaseAdapter {

    public List<Feed> datas = new ArrayList<Feed>();
    public List<Feed> getDatas = new ArrayList<Feed>();
    private Context mContext;

    private boolean isFirstRun = true;

    public GifAdapter(Context context, List<Feed> list) {
        mContext = context;
        getDatas = list;

        for (int i =0; i<getDatas.size();i++){
            if(getDatas.get(i).picList[0].full.endsWith(".gif")){
                datas.add(getDatas.get(i));
            }
        }

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gif, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (imageUrl.full != null && imageUrl.full.endsWith("gif")) {
            holder.imageIcon.setVisibility(View.VISIBLE);
        }

//        Log.d("lpftag","tag:"+holder.imageIcon.getTag());
        Log.d("lpftag", "position:"+position+"-->url:" + imageUrl.full+" size:"+imageUrl.size);

        if(null!= holder.imageIcon.getTag() && (imageUrl.full).equals(holder.imageIcon.getTag())){

        }else{
            Log.d("lpftag","load again");
            holder.imageIcon.setTag(imageUrl.for_list);
            if(imageUrl.full.endsWith("gif")){
                if(position == 0){
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.imageIcon.setVisibility(View.INVISIBLE);
                    Glide.with(mContext).load(imageUrl.for_list).priority(Priority.HIGH).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageCover);
                    Glide.with(mContext).load(imageUrl.full).priority(Priority.NORMAL).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.imageCover.setVisibility(View.INVISIBLE);
                                    holder.progressBar.setVisibility(View.INVISIBLE);
                                    return false;
                                }
                            }).into(holder.imageView);
                }else{
                    Glide.with(mContext).load(imageUrl.for_list).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageCover);
                }
            }
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imageCover;
        ImageView imageView;
        ImageView imageIcon;
        ProgressBar progressBar;

        View rootView;

        public ViewHolder(View view) {
            rootView = view;
            this.imageView = (ImageView) view.findViewById(R.id.gif_img);
            this.progressBar = (ProgressBar) view.findViewById(R.id.gif_progress);
            this.imageCover = (ImageView) view.findViewById(R.id.gif_img_cover);
            this.imageIcon = (ImageView) view.findViewById(R.id.gif_icon);
        }

    }
}
