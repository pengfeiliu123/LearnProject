package com.lpf.mvp.loadGif;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lpf.mvp.MainApplication;
import com.lpf.mvp.NGCommonConfiguration;
import com.lpf.mvp.R;
import com.lpf.mvp.base.BaseActivity;
import com.lpf.mvp.common.Util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.lpf.mvp.common.Util.ignoreTrust;

/**
 * Created by liupengfei on 2017/5/16 14:12.
 */

public class GifActivity_ extends BaseActivity<GifPresenter, GifModel> {

    @BindView(R.id.gif_list)
    ListView gifList;

    GifAdapter mAdapter;
    @BindView(R.id.positionTop)
    TextView positionTop;
    int pos = 0;
    private int showGifPos = 0;
    private int lastShowGifPos = 0;
    private int lastVisibleItemPos = 0;

    private List<Feed> datas = new ArrayList<Feed>();

//    @Override
//    public void setListData(List<Feed> datas) {
//        mAdapter = new GifAdapter(this,datas);
//        gifList.setAdapter(mAdapter);
//    }
//
//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void success() {
//
//    }
//
//    @Override
//    public void failed() {
//
//    }

    @Override
    protected int initLayout() {
        return R.layout.activity_gif;
    }

    @Override
    protected void initPresenter() {
//        mPresenter.setViewAndModel(this,mModel);
    }

    @Override
    protected void initView() {

        mAdapter = new GifAdapter(GifActivity_.this, datas);
        gifList.setAdapter(mAdapter);

//        mPresenter.loadGif();
        new FeedListLoadTask(NewsListRequest.PullAction.Enter, true).execute(NGCommonConfiguration.NEWS_SERVER + NGCommonConfiguration.NEWS_SERVER_FEEDS);

//        preloader = new FlickrListPreloader(this, 5);
//        gifList.setOnScrollListener(preloader);


        gifList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止时
                        pos = gifList.getChildAt(0).getTop();
                        positionTop.setText("显示的第一个Item在ListView中的坐标:" + pos);
                        Glide.with(MainApplication.getInstance()).resumeRequests();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //正在滚动时
                        positionTop.setText("正在滑动...");

                        Log.d("lpftag","showGifPos:"+showGifPos);
                        Log.d("lpftag","lastShowGifPos:"+lastShowGifPos);

//                        if (showGifPos != lastShowGifPos) {
//                            ImageView imageView = (ImageView) (gifList.getChildAt(lastShowGifPos)).findViewById(R.id.gif_img);
//                            Glide.with(GifActivity.this).load(datas.get(lastShowGifPos).picList[0].full).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
//                        } else {
//                            ImageView imageView = (ImageView) (gifList.getChildAt(showGifPos)).findViewById(R.id.gif_img);
//                            Glide.with(GifActivity.this).load(datas.get(showGifPos).picList[0].full).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
//                        }
                        Glide.with(MainApplication.getInstance()).resumeRequests();
                        lastShowGifPos = showGifPos;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        //手指抛动时，手指用力滑动后,手指离开屏幕ListView由于惯性继续滑动
                        positionTop.setText("漂移中...");
                        Glide.with(MainApplication.getInstance()).pauseRequests();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                showGifPos = firstVisibleItem;
//                ImageView  imageView = (ImageView) (gifList.getChildAt(firstVisibleItem)).findViewById(R.id.gif_img);
//                Glide.with(GifActivity.this).load(datas.get(firstVisibleItem).picList[0].full).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

                mAdapter.setCurrentPosition(firstVisibleItem);
                //判断滑动方向
                if (firstVisibleItem > lastVisibleItemPos) {
                    //判断是否滚动到最后一项
                    if (firstVisibleItem + visibleItemCount == totalItemCount
                            && totalItemCount > 0) {
                        Log.d("lpftag", "滚动到了最后一个item");
                    }
                    Log.d("lpftag", "上滑");
                } else if (firstVisibleItem < lastVisibleItemPos) {
                    Log.d("lpftag", "下滑");
                }
                lastVisibleItemPos = firstVisibleItem;
            }
        });
    }

//    private class FlickrListPreloader extends ListPreloader<Feed> {
//        private final Context context;
//        private int[] photoDimens = null;
//        public FlickrListPreloader(Context context, int maxPreload) {
//            super(maxPreload);
//            this.context = context;
//        }
//        public boolean isDimensSet() {
//            return photoDimens != null;
//        }
//        public void setDimens(int width, int height) {
//            if (photoDimens == null) {
//                photoDimens = new int[] { width, height };
//            }
//        }
//        @Override
//        protected int[] getDimensions(Feed item) {
//            return photoDimens;
//        }
//        @Override
//        protected List<Feed> getItems(int start, int end) {
//            return datas.subList(start, end);
//        }
//        @Override
//        protected Glide.Request getRequest(Feed item) {
//            return Glide.with(context)
//                    .using(new FlickrModelLoader(GifActivity.this, urlCache))
//                    .load(item)
//                    .centerCrop();
//        }
//    }

    @Override
    protected void initListener() {


    }

    class FeedListLoadTask extends AsyncTask<String, Integer, List<Feed>> {

        public FeedListLoadTask(NewsListRequest.PullAction pullAction, boolean isFirstRequest) {
//            this.pullAction = pullAction;
//            this.isFirstRequest = isFirstRequest;
        }

        @Override
        protected List<Feed> doInBackground(String... params) {
            NewsListRequest newsListRequest = new NewsListRequest();
            newsListRequest.init("GIF", NewsListRequest.PullAction.Enter, -1, "");
            RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), Util.toJsonPretty(newsListRequest));
            Request.Builder requestBuilder = new Request.Builder().url(params[0]).post(postBody);
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
//                    String responseStr = "[{\"id\":\"a0066800ccb2882ecc6e950231275cc4\",\"source\":\"가생이닷컴\",\"srcURL\":\"http://www.gasengi.com/main/board.php?bo_table=humor03&wr_id=983892&page=1\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"손 안떼고 한줄로 그리기\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/b57e22949423669b129043c1445bd9e6815f0683.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/b57e22949423669b129043c1445bd9e6815f0683.gif\",\"size\":\"299,217\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/a0066800ccb2882ecc6e950231275cc4?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"20a23e074c11525df45badeb59b68e61\",\"source\":\"루리웹\",\"srcURL\":\"http://bbs.ruliweb.com/hobby/board/300310/read/2508250\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"[잡담]호풀이\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/103535f7cf48223e4b6a991c243bf1f0c1abe112.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/103535f7cf48223e4b6a991c243bf1f0c1abe112.gif\",\"size\":\"480,300\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/20a23e074c11525df45badeb59b68e61?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"b3b19178c339056d558c8b139e2b8d91\",\"source\":\"PGR21\",\"srcURL\":\"http://www.pgr21.com/pb/pb.php?id=spoent&no=6892\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"[스포츠] [해축] 5년전 오늘 카일 워커.gif (17MB)\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/625a41f628436cf48938e07dcb4435839a39c157.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/625a41f628436cf48938e07dcb4435839a39c157.gif\",\"size\":\"478,266\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/b3b19178c339056d558c8b139e2b8d91?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"fc7272a485cd01abdb3427163fcb666d\",\"source\":\"오늘의유머\",\"srcURL\":\"http://www.todayhumor.co.kr/board/view.php?table=bestofbest&no=331874&s_no=331874&page=1\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"현재 날씨에 대한 ㄹㄹㅇ 유저의 평가\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/f07f8265121c30767f3ef5394f461d5e9088e639.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/f07f8265121c30767f3ef5394f461d5e9088e639.gif\",\"size\":\"480,480\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/fc7272a485cd01abdb3427163fcb666d?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"891e0768477b9a3d84ea84928f534d9e\",\"source\":\"개드립\",\"srcURL\":\"http://www.dogdrip.net/126558638\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"정력에 좋은 음식들\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/0e5d574b9db1a30d8ebdc15efe3bf2fb12b76d78.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/0e5d574b9db1a30d8ebdc15efe3bf2fb12b76d78.gif\",\"size\":\"305,320\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/891e0768477b9a3d84ea84928f534d9e?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"aaed12348ba5c97ab666c9b9ffa6a771\",\"source\":\"오늘의유머\",\"srcURL\":\"http://www.todayhumor.co.kr/board/view.php?table=humorbest&no=1422505&s_no=1422505&page=1\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"문재인 \\\"공공급식, 복지시설 확대! 어런이집·학교 GMO 식재료 퇴출\\\"\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/3028790b50849aacf2c540ab9d9af0711bed5934.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/3028790b50849aacf2c540ab9d9af0711bed5934.gif\",\"size\":\"270,270\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/aaed12348ba5c97ab666c9b9ffa6a771?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"23e1da4860a65b23626d59068a2f8e44\",\"source\":\"JJALBOX\",\"srcURL\":\"http://jjalbox.com/?act=jjalbox&v=4925&type=&"lpftag"=&m=jjalbox\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"야이 축구 키퍼 바람 반전 골인 황당 뭐냐 어이 어이없네 축구경기 골키퍼 멘붕\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/c2142d3f1520cbc0eb7f543c85bbc40024ad31cb.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/c2142d3f1520cbc0eb7f543c85bbc40024ad31cb.gif\",\"size\":\"320,194\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/23e1da4860a65b23626d59068a2f8e44?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"350e65ede0dccf45a09761f1a8f0b30b\",\"source\":\"오늘의유머\",\"srcURL\":\"http://www.todayhumor.co.kr/board/view.php?table=sisa&no=911414&s_no=13383597&kind=total&page=2\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"진주여성 509명 문재인 후보 지지선언\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/643de2522d6ef237b2c1373759ae69349502684f.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/643de2522d6ef237b2c1373759ae69349502684f.gif\",\"size\":\"270,270\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/350e65ede0dccf45a09761f1a8f0b30b?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"07200bf935fbeb79504f7454f4793a18\",\"source\":\"JJALBOX\",\"srcURL\":\"http://jjalbox.com/?act=jjalbox&v=1877&type=&"lpftag"=&m=jjalbox\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"멘붕 좌절  스펀지 밥  개거품\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/515fb7cb987a28b59f227053a18dea6534da6951.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/515fb7cb987a28b59f227053a18dea6534da6951.gif\",\"size\":\"400,216\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/07200bf935fbeb79504f7454f4793a18?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"00d80f16555983e4bcd8e1b72af284b0\",\"source\":\"루리웹\",\"srcURL\":\"http://bbs.ruliweb.com/hobby/board/300100/read/30570158?page=2\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"[인물/패션]P&I 이은혜,김다나\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/ab57f4e2990a88169c6e68175efaa7347bfdff9b.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/ab57f4e2990a88169c6e68175efaa7347bfdff9b.gif\",\"size\":\"393,698\"},{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/d69786f5ff256dd5611c7f75d93e4f2444e5875b.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/d69786f5ff256dd5611c7f75d93e4f2444e5875b.gif\",\"size\":\"532,299\"},{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/f0f85358ae1cc8724929a47a8560a5025e56777d.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/f0f85358ae1cc8724929a47a8560a5025e56777d.gif\",\"size\":\"450,800\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/00d80f16555983e4bcd8e1b72af284b0?s=1\",\"shareType\":0,\"imageCount\":5,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"d3b4ac65f0c43d6b010da6c459817ced\",\"source\":\"가생이닷컴\",\"srcURL\":\"http://www.gasengi.com/main/board.php?bo_table=humor03&wr_id=980006&page=1\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"한방으로 정리\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/ab5583732df2c7703590ec8a2870e8a1767bcbdd.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/ab5583732df2c7703590ec8a2870e8a1767bcbdd.gif\",\"size\":\"399,204\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/d3b4ac65f0c43d6b010da6c459817ced?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"},{\"id\":\"514905cb687418da96bc7fcedae5d263\",\"source\":\"PGR21\",\"srcURL\":\"http://www.pgr21.com/pb/pb.php?id=humor&no=305432&page=2\",\"playCount\":0,\"iconId\":0,\"time\":1494919573,\"title\":\"[유머] 극한 알바\",\"picList\":[{\"for_list\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/9cfce0f63165e33edc8cf30a73b80f29d1829c6c.jpg\",\"full\":\"https://d1d7glqtmsbpdn.cloudfront.net/full/9cfce0f63165e33edc8cf30a73b80f29d1829c6c.gif\",\"size\":\"270,181\"}],\"copyright\":0,\"type\":4,\"listStyle\":\"gif_big_pic_with_title\",\"duration\":\"\",\"enable\":true,\"shareURL\":\"https://news-web-s"lpftag"ing.newsstationtech.com/v1/feed/514905cb687418da96bc7fcedae5d263?s=1\",\"shareType\":0,\"imageCount\":1,\"smartType\":3,\"smallFlowFlag\":\"\"}]";
                    Gson gson = new Gson();
                    Feed[] feeds = gson.fromJson(responseStr, Feed[].class);
                    List<Feed> newFeedList = Arrays.asList(feeds);
                    return newFeedList;
                }
                Log.d("test", "code:" + response.code());
            } catch (UnknownHostException e) {
//                isConnectionError = true;
//                if (cachedFeeds != null && !cachedFeeds.isEmpty()) {
//                    publishProgress(1001);
//                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Feed> feeds) {

            super.onPostExecute(feeds);
            datas = feeds;
//            mAdapter = new GifAdapter(GifActivity.this, datas);
//            gifList.setAdapter(mAdapter);
            mAdapter.setData(datas);
            mAdapter.notifyDataSetChanged();
//            ImageView  imageView = (ImageView)gifList.getChildAt(0).findViewById(R.id.gif_img);
//            Glide.with(GifActivity.this).load(datas.get(showGifPos).picList[0].full).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        }
    }

//    private FlickrListPreloader preloader;

}
