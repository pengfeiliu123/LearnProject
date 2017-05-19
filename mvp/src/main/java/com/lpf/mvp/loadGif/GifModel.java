package com.lpf.mvp.loadGif;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.lpf.mvp.NGCommonConfiguration;
import com.lpf.mvp.common.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

import static com.lpf.mvp.common.Util.ignoreTrust;

/**
 * Created by liupengfei on 2017/5/16 14:08.
 */

public class GifModel implements GifContract.Model {
    @Override
    public Observable<List<Feed>> loadGif() {
        return
                Observable.create(new Observable.OnSubscribe<List<Feed>>() {
                    @Override
                    public void call(Subscriber<? super List<Feed>> subscriber) {

                        NewsListRequest newsListRequest = new NewsListRequest();
                        newsListRequest.init("GIF", NewsListRequest.PullAction.Enter, -1, "");
                        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), Util.toJsonPretty(newsListRequest));
                        Request.Builder requestBuilder = new Request.Builder().url(NGCommonConfiguration.NEWS_SERVER + NGCommonConfiguration.NEWS_SERVER_FEEDS).post(postBody);
                        requestBuilder = Util.addHeader(requestBuilder);
                        Request request = requestBuilder.build();
                        try {
                            OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                                    .readTimeout(NGCommonConfiguration.COMMON_READ_TIME_OUT, TimeUnit.SECONDS)
                                    .connectTimeout(NGCommonConfiguration.COMMON_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                                    .writeTimeout(NGCommonConfiguration.COMMON_WRITE_TIME_OUT, TimeUnit.SECONDS);
                            builder = ignoreTrust(builder);
                            Log.d("test", Util.toJsonPretty(newsListRequest));
                            OkHttpClient okHttpClient = builder.build();
                            Call call = okHttpClient.newCall(request);
                            Response response = call.execute();
                            if (response.code() == 200) {
                                String responseStr = response.body().string();
                                Gson gson = new Gson();
                                Feed[] feeds = gson.fromJson(responseStr, Feed[].class);
                                List<Feed> newFeedList = Arrays.asList(feeds);
                                subscriber.onNext(newFeedList);
                            }
                            Log.d("test", "code:" + response.code());
                        } catch (java.net.UnknownHostException e) {
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        subscriber.onCompleted();
                    }
                });
    }
}
