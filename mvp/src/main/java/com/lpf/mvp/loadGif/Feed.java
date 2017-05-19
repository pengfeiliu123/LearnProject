package com.lpf.mvp.loadGif;

import android.text.TextUtils;
import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nick on 2017/1/19.
 */

public class Feed implements Serializable {
    /**
     * 1-图文	无图模式，纯文字内容	news_no_pic
     * 小图模式，左文右图	news_small_pic
     * 多图模式	news_multi_pic
     */
    public static final String ListStyle_news_no_pic = "news_no_pic";
    public static final String ListStyle_news_small_pic = "news_small_pic";
    public static final String ListStyle_news_multi_pic = "news_multi_pic";

    /**
     * 2-图集	多图模式	picture_multi_pic
     * 大小图	picture_mix_pic
     */
    public static final String ListStyle_picture_multi_pic = "picture_multi_pic";
    public static final String ListStyle_picture_mix_pic = "picture_mix_pic";
    public static final String ListStyle_picture_small_pic = "picture_small_pic";

    /**
     * 3-视频	大图模式	video_big_pic
     * 小图模式	video_small_pic
     */
    public static final String ListStyle_video_big_pic = "video_big_pic";
    public static final String ListStyle_video_small_pic = "video_small_pic";

    /**
     * 4-GIF	大图模式 有标题	gif_big_pic_with_title
     * 大图模式 无标题	gif_big_pic_without_title
     * 文字 + 小图 + gif icon    gif_small_pic
     */
    public static final String ListStyle_gif_big_pic_with_title = "gif_big_pic_with_title";
    public static final String ListStyle_gif_big_pic_without_title = "gif_big_pic_without_title";
    public static final String ListStyle_gif_small_pic = "gif_small_pic";

    /**
     * 5-漫画	小图模式，左文右图	comic_small_pic_right
     * 可以考虑左图右文	comic_small_pic_left
     */
    public static final String ListStyle_comic_small_pic_right = "comic_small_pic_right";
    public static final String ListStyle_comic_small_pic_left = "comic_small_pic_left";

    /**
     * 6-趣图	大图模式 有标题	funpic_big_pic_with_title
     * 大图模式 无标题	funpic_big_pic_without_title
     */
    public static final String ListStyle_funpic_big_pic_with_title = "funpic_big_pic_with_title";
    public static final String ListStyle_funpic_big_pic_without_title = "funpic_big_pic_without_title";

    public static final int Type_News = 1;
    public static final int Type_Picture = 2;
    public static final int Type_Video = 3;
    public static final int Type_Gif = 4;
    public static final int Type_Comic = 5;
    public static final int Type_Funpic = 6;

    public String id;// "id": "[string] 文档资源ID",
    public String jsonString;
    public String html;
    public String createTime; // create time in local

    public String source; // [string] 文档抓取来源名称",
    public String srcURL; // "[string] 抓取来源URL",

    public int shareType; // [int] 分享URL的类型。0表示SmartView，1表示SourceView"
    public String shareURL; // "[string] 分享出去的URL"

    public int commentCount;
    public int playCount; // "[int] 浏览计数，未实现",
    public int iconId; // "[int] 小图标ID，未实现",
    public int time; // "[int] 资源发布时间，UNIX秒",
    public String title; // "[string] 标题",
    public PictureMeta[] picList; // "[array<string>] 图片列表",
    public int copyright; //"[int] 0表示无版权问题，非0则详情页需要用抓取来源URL",
    public int type; // "[int] 文档资源类型，见下面说明"
    public String listStyle; // "[string] 列表页展示样式，如果是空字符串，客户端用默认样式展示"
    public int imageCount; // "[int] 对于图集类型表示实际包含多少张图片，其他类型默认为0",
    public String duration; // [string] 对于视频类型表示视频持续时间，其他类型默认为空字符串

    public int upCount;
    public int downCount;
    public boolean isUp;
    public boolean isDown;
    public boolean isFav;

    public boolean enable;
    public boolean viewed; // not from server, local only
    public int smartType; // "[int] 是否可以显现smart view: 0-可选择性显示 1-强制显示 2-禁止显示"
    public String smallFlowFlag;

    public List<Comment> commentList;

    public boolean hasCopyRight() {
        return copyright != 0;
    }

    public FeedType getFeedType() {
        return FeedType.getFeedType(type, picList, listStyle);
    }

    public static class PictureMeta implements Serializable {
        public String for_list; // "[string] 列表页头图",
        public String full; // "[string] 原始图",
        public String size; // "[string] 尺寸："123,456"，如果不能提供尺寸信息，该字段为空字符串"

        public float ratio;

        public float getRatio() {
            if (ratio > 0)
                return ratio;

            if (!TextUtils.isEmpty(size)) {
                String[] split = size.split(",");
                if (split.length == 2) {
                    ratio = Float.parseFloat(split[1]) / Float.parseFloat(split[0]);
                }
            }

            return ratio;
        }
    }
}
