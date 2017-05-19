package com.lpf.mvp.loadGif;

public enum FeedType {
    /**
     * 1-图文	无图模式，纯文字内容	news_no_pic
     * 小图模式，左文右图	news_small_pic
     * 多图模式	news_multi_pic
     */
    news_default(100),
    news_no_pic(101),
    news_small_pic(102),
    news_multi_pic(103),

    /**
     * 2-图集 多图模式	    picture_multi_pic
     * 大小图	            picture_mix_pic
     * 文字 + 小图 + 角标 	picture_small_pic
     */
    picture_default(200),
    picture_multi_pic(201),
    picture_mix_pic(202),
    picture_small_pic(203),

    /**
     * 3-视频	大图模式	video_big_pic
     * 小图模式	video_small_pic
     */
    video_default(300),
    video_big_pic(301),
    video_small_pic(302),

    /**
     * 4-GIF	大图模式 有标题	gif_big_pic_with_title
     * 大图模式 无标题	        gif_big_pic_without_title
     * 文字 + 小图 + gif icon    gif_small_pic
     */
    gif_default(400),
    gif_big_pic_with_title(401),
    gif_big_pic_without_title(402),
    gif_small_pic(403),


    /**
     * 5-漫画	小图模式，左文右图	comic_small_pic_right
     * 可以考虑左图右文	comic_small_pic_left
     */
    comic_default(500),
    comic_small_pic_right(501),
    comic_small_pic_left(502),

    /**
     * 6-趣图	大图模式 有标题	funpic_big_pic_with_title
     * 大图模式 无标题	funpic_big_pic_without_title
     */
    funpic_default(600),
    funpic_big_pic_with_title(601),
    funpic_big_pic_without_title(602);

    private int type;

    FeedType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static FeedType getFeedType(int type, Feed.PictureMeta[] picList, String listStyle) {
        if (listStyle == null)
            listStyle = "";

        switch (type) {
            case Feed.Type_News: {
                switch (listStyle) {
                    case Feed.ListStyle_news_no_pic:
                        return FeedType.news_no_pic;
                    case Feed.ListStyle_news_small_pic:
                        if (picList == null || picList.length < 1)
                            return FeedType.news_no_pic;
                        return FeedType.news_small_pic;
                    case Feed.ListStyle_news_multi_pic:
                        if (picList == null || picList.length < 3)
                            return FeedType.news_default;
                        return FeedType.news_multi_pic;
                }
                return FeedType.news_default;
            }

            case Feed.Type_Picture:
                switch (listStyle) {
                    case Feed.ListStyle_picture_multi_pic:
                        if (picList == null || picList.length < 3)
                            return FeedType.picture_default;
                        return FeedType.picture_multi_pic;
                    case Feed.ListStyle_picture_mix_pic:
                        if (picList == null || picList.length < 3)
                            return FeedType.picture_default;
                        return FeedType.picture_mix_pic;
                    case Feed.ListStyle_picture_small_pic:
                        return FeedType.picture_small_pic;
                }
                return FeedType.picture_default;

            case Feed.Type_Video:
                switch (listStyle) {
                    case Feed.ListStyle_video_big_pic:
                        if (picList == null || picList.length < 1)
                            return FeedType.video_default;
                        return FeedType.video_big_pic;
                    case Feed.ListStyle_video_small_pic:
                        if (picList == null || picList.length < 1)
                            return FeedType.video_default;
                        return FeedType.video_small_pic;
                }
                return FeedType.video_default;

            case Feed.Type_Gif:
//                if (picList == null || picList.length < 1)
//                    return FeedType.gif_default;
                switch (listStyle) {
                    case Feed.ListStyle_gif_big_pic_with_title:
                        return FeedType.gif_big_pic_with_title;
                    case Feed.ListStyle_gif_big_pic_without_title:
                        return FeedType.gif_big_pic_without_title;
                    case Feed.ListStyle_gif_small_pic:
                        return FeedType.gif_small_pic;
                }
                return FeedType.gif_default;

            case Feed.Type_Comic:
//                if (picList == null || picList.length < 1)
//                    return FeedType.comic_default;
                switch (listStyle) {
                    case Feed.ListStyle_comic_small_pic_right:
                        return FeedType.comic_small_pic_right;
                    case Feed.ListStyle_comic_small_pic_left:
                        return FeedType.comic_small_pic_left;
                    case Feed.ListStyle_news_small_pic:
                        return FeedType.news_small_pic;
                }
                return FeedType.comic_default;

            case Feed.Type_Funpic:
//                if (picList == null || picList.length < 1)
//                    return FeedType.funpic_default;
                switch (listStyle) {
                    case Feed.ListStyle_funpic_big_pic_with_title:
                        return FeedType.funpic_big_pic_with_title;
                    case Feed.ListStyle_funpic_big_pic_without_title:
                        return FeedType.funpic_big_pic_without_title;
                    case Feed.ListStyle_news_small_pic:
                        return FeedType.news_small_pic;
                }
                return FeedType.funpic_default;
        }
        return FeedType.news_default;
    }
}