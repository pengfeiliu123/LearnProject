package com.lpf.mvp;

public class NGCommonConfiguration {

    public static final String APPLICATION_NAME = "NewsStation-Beta";

    public static final String API_VERSION = "v1";

    //    public static final String DEVELOPER_KEY = "AIzaSyC5g_j2XcDKYggaMUdJN1Rli6Xm-bka3no"; // mx player beta
    public static final String DEVELOPER_KEY = "AIzaSyCy-ISbxq3lVxnjC0b88nQeqthdXSyd0nw"; //

    // keys
    public static final String ADJUST_TOKEN = "o603wduhp98g";

    public static final String FLURRY_KEY = "9RQMZH4M9WV4BXZFM75R";

    public static String NEWS_SERVER = "";
    public static final String NEWS_SERVER_FEEDS = API_VERSION + "/feeds";
    public static final String NEWS_FEED = API_VERSION + "/feed/%s?iswifi=%s&recm=%s";
    public static final String NEWS_SESSION = API_VERSION + "/session";

    public static final String NEWS_SERVER_LOG = API_VERSION + "/log";
    public static final String NEWS_SERVER_FEED_ACTION = API_VERSION + "/feed/action";

    public static final String NEWS_SERVER_USER_FAV = API_VERSION + "/user/fav";


    public static final String NEWS_SERVER_COMMENT_GET = API_VERSION + "/comment/";
    public static final String NEWS_SERVER_COMMENT_ACTION = API_VERSION + "/comment/action";
    public static final String NEWS_SERVER_REPORT = API_VERSION + "/inform";

    public static final String NEWS_SERVER_NOTIFICATION = API_VERSION + "/user/pushtoken";

    public static final String NEWS_CHANNEL = API_VERSION + "/user/subscribe";

    // broadcast
    public static final String BROADCAST_COMMENT_ACTION_HAPPENED = "COMMENT_ACTION_HAPPENED";
    public static final String BROADCAST_COMMENT_DELETED = "COMMENT_ACTION_DELETED";


    // limitation
    public static final int COMMON_READ_TIME_OUT = 5;
    public static final int COMMON_CONNECT_TIME_OUT = 5;
    public static final int COMMON_WRITE_TIME_OUT = 5;

    public static final String USER_TERMS = "http://videonews.strikingly.com/#terms-and-service";
    public static final String PRIVACY_TERMS = "http://videonews.strikingly.com/#privacy-policy";
}
