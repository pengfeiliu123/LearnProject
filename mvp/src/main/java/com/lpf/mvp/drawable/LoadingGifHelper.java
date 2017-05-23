package com.lpf.mvp.drawable;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpf.mvp.R;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by liupengfei on 2017/5/21 10:14.
 * Loading Gifs from url and cache them.
 */

public class LoadingGifHelper {

    // for listview itemView
    public static class ProgressViews {

        public WeakReference<GifImageView> gifImageViewWeakReference;
        public WeakReference<ImageView> coverImageViewWeakReference;
        public WeakReference<ImageView> iconImageViewWeakReference;
        public WeakReference<ProgressWheel> progressWheelWeakReference;
        public WeakReference<TextView> tvProgressWeakReference;

        public int displayWidth; // gifImageView width

        public ProgressViews(WeakReference<GifImageView> gifImageViewWeakReference, WeakReference<ImageView> coverImageViewWeakReference, WeakReference<ImageView> iconImageViewWeakReference, WeakReference<ProgressWheel> progressWheelWeakReference, WeakReference<TextView> tvProgressWeakReference, int displayWidth) {
            this.gifImageViewWeakReference = gifImageViewWeakReference;
            this.coverImageViewWeakReference = coverImageViewWeakReference;
            this.iconImageViewWeakReference = iconImageViewWeakReference;
            this.progressWheelWeakReference = progressWheelWeakReference;
            this.tvProgressWeakReference = tvProgressWeakReference;
            this.displayWidth = displayWidth;
        }
    }

    // to make sure one gif just download one time, The url and imageView is a one-to-many relationship.
    // In order to prevent a memory leak, the LRU cache used in a one-to-many relationship
    public static ConcurrentHashMap<String, ArrayList<ProgressViews>> memoryCache;


    public static void displayImage(final String url,
                                    GifImageView gifImageView,
                                    ImageView coverImageView,
                                    ImageView iconImageView,
                                    final ProgressWheel progressWheel,
                                    TextView tvProgress,
                                    final int displayWidth) {

        //check cache file first
        String md5Url = getMd5(url);
        // when download undone, the file will named with suffix .tmp
        // to prevent  another request for same url to show the undone image.
        String path = gifImageView.getContext().getCacheDir().getAbsolutePath() + "/" + md5Url;
        Log.i("lpftag", "gif cache path" + path);

        final File cacheFile = new File(path);
        if (cacheFile.exists()) {
            Log.i("lpftag", "this image has cache");
            //load local cache image
            if (displayCacheImage(cacheFile, gifImageView, coverImageView, iconImageView, displayWidth)) {
                if (progressWheel != null) {
                    progressWheel.setVisibility(View.GONE);
                }
                if (tvProgress != null) {
                    tvProgress.setVisibility(View.GONE);
                }
                return;
            }
        }

        // use weak references to prevent memory leak.
        final WeakReference<GifImageView> gifImageViewRf = new WeakReference<GifImageView>(gifImageView);
        final WeakReference<ImageView> coverImageViewRf = new WeakReference<ImageView>(coverImageView);
        final WeakReference<ImageView> iconImageViewRf = new WeakReference<ImageView>(iconImageView);
        final WeakReference<ProgressWheel> progressWheelRf = new WeakReference<ProgressWheel>(progressWheel);
        final WeakReference<TextView> tvProgressWheelRf = new WeakReference<TextView>(tvProgress);

        // when loading gifImage, placeholder image.
        gifImageView.setImageResource(R.mipmap.loading);
        if (memoryCache != null && memoryCache.get(url) != null) {
            Log.i("lpftag", "another imageView had loaded this gif" + url);
            //borrow the old loading progress, no need to new one download task
            memoryCache.get(url).add(new ProgressViews(gifImageViewRf, coverImageViewRf, iconImageViewRf, progressWheelRf, tvProgressWheelRf, displayWidth));
            return;
        }

        if (memoryCache == null) {
            memoryCache = new ConcurrentHashMap<>();
        }

        // url and gifImageView in a one-to-many relationship
        if (memoryCache.get(url) == null) {
            memoryCache.put(url, new ArrayList<ProgressViews>());
        }
        // add this url to cache , prevent repeat
        memoryCache.get(url).add(new ProgressViews(gifImageViewRf, coverImageViewRf, iconImageViewRf, progressWheelRf, tvProgressWheelRf, displayWidth));

        // init Progress for starting to load one gif image
        ProgressWheel progressWheelGet = progressWheelRf.get();
        final TextView tvProgressWheelGet = tvProgressWheelRf.get();
        if (progressWheelGet != null) {
//            progressWheelGet.setVisibility(View.VISIBLE);
            progressWheel.setProgress(0);
            if (tvProgressWheelGet == null) {
                return;
            } else {
//                tvProgressWheelGet.setVisibility(View.VISIBLE);
                tvProgressWheelGet.setText("0%");
            }
        }

        startDownLoad(url, new File(cacheFile.getAbsolutePath() + ".tmp"), new DownLoadTask() {

            @Override
            void onStart() {
                Log.i("lpftag", "start downloading gif ...");
            }

            @Override
            void onLoading(long total, long current) {
                // update progress value
                int progressValue = 0;
                // if cannot get gif size, total = -1
                if (total > 0) {
                    progressValue = (int) (current * 100 / total);
                }

                ArrayList<ProgressViews> views = memoryCache.get(url);
                if (views == null) {
                    return;
                }
//                Log.i("lpftag", "all request this url number:" + views.size());

                for (ProgressViews vs : views) {
//                    if(vs.iconImageViewWeakReference.get().getVisibility() == View.VISIBLE){
//                        return;
//                    }
                    String tag = vs.gifImageViewWeakReference.get().getTag().toString();

                    ProgressWheel progressWheelRf = vs.progressWheelWeakReference.get();
                    if (progressWheelRf != null) {
                        progressWheelRf.setProgress((float) progressValue / 100f);
                        // if cannot get gif size ,loading always
                        if (total == -1) {
                            progressWheelRf.setProgress(20);
                        }

                        TextView tvProgressRf = vs.tvProgressWeakReference.get();
                        if (tvProgressRf != null) {
                            tvProgressRf.setText(progressValue + "%");
//                            Log.i("lpftag","progressValue:"+progressValue+"%");
                        }
                        // show the first frame until download done
                        getFirstPicOfGif(new File(cacheFile.getAbsolutePath() + ".tmp"), vs.gifImageViewWeakReference.get());
                    }
                }
            }

            @Override
            void onSuccess(File file) {
                if (file == null)
                    return;

                String path = file.getAbsolutePath();
                if (path == null || path.length() < 5)
                    return;

                File downloadFile = new File(path);
                File renameFile = new File(path.substring(0, path.length() - 4));
                if (path.endsWith(".tmp")) {
                    downloadFile.renameTo(renameFile); // remove .tmp suffix
                }
                Log.i("lpftag", "download gif succeed, path->" + path + ", after rename ->" + renameFile.getAbsolutePath());

                if (memoryCache == null)
                    return;

                ArrayList<ProgressViews> viewArray = memoryCache.get(url);
                if (viewArray == null && viewArray.size() == 0)
                    return;
                for (ProgressViews vs : viewArray) {
                    // update progress for views which request this url
                    GifImageView gifImageViewRf = vs.gifImageViewWeakReference.get();
                    ImageView coverImageViewRf = vs.coverImageViewWeakReference.get();
                    ImageView iconImageViewRf = vs.iconImageViewWeakReference.get();
                    if (gifImageViewRf != null) {
                        displayCacheImage(renameFile, gifImageViewRf, coverImageViewRf, iconImageViewRf, displayWidth);
                    }
                    //update progress value
                    ProgressWheel progressWheelRf = vs.progressWheelWeakReference.get();
                    TextView tvProgressWheelRf = vs.tvProgressWeakReference.get();
                    if (progressWheelRf != null) {
                        progressWheelRf.setVisibility(View.GONE);
                    }
                    if (tvProgressWheelRf != null) {
                        tvProgressWheelRf.setVisibility(View.GONE);
                    }
                }
                Log.i("lpftag", url + " , this imageview has loaded , all request ->" + viewArray.size());
                memoryCache.remove(url); // clear cache after this url has been shown.
            }

            @Override
            void onFailure(Throwable e) {
                Log.e("lpftag", "download image exception:", e);
                TextView tvProgress = tvProgressWheelRf.get();
                ProgressWheel progressWheel = progressWheelRf.get();
                if (progressWheel != null) {
                    progressWheel.setVisibility(View.GONE);
                }
                if (tvProgress != null) {
                    tvProgress.setVisibility(View.GONE);
                }
                if (memoryCache != null) {
                    memoryCache.remove(url);    //remove failure url
                }
            }
        });
    }

    public static boolean displayCacheImage(File localFile, GifImageView gifImageView, ImageView coverImage, ImageView iconImageView, int displayWidth) {
        if (localFile == null || gifImageView == null) {
            return false;
        }
        Log.i("lpftag", "prepare to load cache gif file" + localFile.getAbsolutePath() + "->displayWidth" + displayWidth);
        GifDrawable gifDrawable;
        try {
            gifDrawable = new GifDrawable(localFile);
            int raw_height = gifDrawable.getIntrinsicHeight();
            int raw_width = gifDrawable.getIntrinsicWidth();
            Log.i("lpftag", "origin image height:" + raw_height + " width:" + raw_width);
            // scale or not
            gifImageView.setImageDrawable(gifDrawable);
            if (iconImageView.getVisibility() == View.INVISIBLE) {
                gifImageView.setVisibility(View.VISIBLE);
                coverImage.setVisibility(View.INVISIBLE);
            }
            Log.d("lpftag", "show current file name:" + localFile.getName());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // show the first frame of the image
    public static void getFirstPicOfGif(File gifFile, GifImageView gifImageView) {
        if (gifImageView == null) {
            return;
        }
        // if first pic has been shown
        if (gifImageView.getTag(R.style.AppTheme) instanceof Integer) {
            return;
        }

        try {
            GifDrawable gifFromFile = new GifDrawable(gifFile);
            boolean canSeekForward = gifFromFile.canSeekForward();
            if (!canSeekForward) {
                Log.i("lpftag", "is able to show first frame" + canSeekForward);
                return;
            }

            gifFromFile.seekToFrame(0);
            gifFromFile.pause();                        // stop at first frame
            gifImageView.setVisibility(View.VISIBLE);
            gifImageView.setImageDrawable(gifFromFile);
            gifImageView.setTag(R.style.AppTheme, 1);    // tag for first frame has been shown

        } catch (IOException e) {
            Log.e("lpftag", "get gif exception", e);
        }

    }

    // get md5 for the url
    public static String getMd5(String str) {
        if (str == null || str.length() < 1) {
            return "no_image.gif";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            StringBuilder sb = new StringBuilder(40);
            for (byte x : bs) {
                if ((x & 0xff) >> 4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x&0xff));
                }
            }
            if(sb.length()<24){
                return sb.toString();
            }else{
                return sb.toString().substring(8,24);   // to improve disk rearch speed
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("lpftag","MD5 Encryption failed");
            return "no_image.gif";
        }
    }


    public static abstract class DownLoadTask {
        abstract void onStart();

        abstract void onLoading(long total, long current);

        abstract void onSuccess(File target);

        abstract void onFailure(Throwable e);

        boolean isCanceled;
    }

    public static void startDownLoad(final String url, final File targetFile, final DownLoadTask task) {
        final Handler handler = new Handler();
        new LoadingMultiTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                task.onStart();
                downloadToStream(url, targetFile, task, handler);
                return null;
            }
        }.executeDependSdk();
    }

    public static long downloadToStream(String uri, final File targetFile, final DownLoadTask task, Handler handler) {
        if (task == null || task.isCanceled)
            return -1;

        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutPutStream = null;

        long result = -1;
        long fileLen = 0;
        long currentCount = 0;

        try {
            try {
                final URL url = new URL(uri);
                outputStream = new FileOutputStream(targetFile);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setReadTimeout(10000);

                final int responseCode = httpURLConnection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    result = httpURLConnection.getExpiration();
                    result = result < System.currentTimeMillis() ? System.currentTimeMillis() + 40000 : result;
                    fileLen = httpURLConnection.getContentLength(); // get gif size from content-lenght in header
                } else {
                    Log.e("lpftag", "downloadToStream->response=>" + responseCode);
                    return -1;
                }
            } catch (final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        task.onFailure(e);
                    }
                });
                return -1;
            }

            if (task.isCanceled)
                return -1;

            byte[] buffer = new byte[4096];
            int len = 0;
            bufferedOutPutStream = new BufferedOutputStream(outputStream);
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutPutStream.write(buffer, 0, len);
                currentCount += len;
                if (task.isCanceled)
                    return -1;

                final long finalFileLen = fileLen;
                final long fileCurrCount = currentCount;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        task.onLoading(finalFileLen, fileCurrCount);
                    }
                });
            }

            // loading finished
            bufferedOutPutStream.flush();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // load success
                    task.onSuccess(targetFile);
                }
            });
        } catch (Throwable e) {
            result = -1;
            task.onFailure(e);
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (final Throwable e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            task.onFailure(e);
                        }
                    });
                }
            }
            //todo
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedOutPutStream != null) {
                try {
                    bufferedOutPutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


}
