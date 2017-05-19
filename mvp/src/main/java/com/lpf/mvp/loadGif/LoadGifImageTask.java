package com.lpf.mvp.loadGif;

import android.os.AsyncTask;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lpf.mvp.MainApplication;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by liupengfei on 2017/5/18 13:24.
 */

public class LoadGifImageTask extends AsyncTask<String,Void,GifDrawable> {

    AsyncHttpClient client ;
    GifDrawable resultGif;

    @Override
    protected GifDrawable doInBackground(String... params) {
        client = new AsyncHttpClient();
        client.get(params[0], new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                try {
                    resultGif = new GifDrawable(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                holder.imageView.setImageDrawable(resultGif);
//                    progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MainApplication.getInstance(), "load failed", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(GifDrawable gifDrawable) {
        super.onPostExecute(gifDrawable);
    }
}
