package com.lpf.mvp.loadGif;

import com.lpf.mvp.MainApplication;
import com.lpf.mvp.common.DeviceInfo;
import com.lpf.mvp.common.PreferencesUtil;
import com.lpf.mvp.common.Util;

import java.util.Locale;

public class NewsListRequest {
    /**
     * interfaceName与channel的组合：控制拉取的频道
     * <p>
     * interfaceName	channel	说明
     * foryou_version_1_0	all	拉取foryou页
     * channel_version_1_0	[见下表]	拉取具体某个频道页
     * <p>
     * <p>
     * channel_version_1_0可用channel
     * <p>
     * channel	说明
     * 비디오	视频
     * GIF	GIF
     * 휴머	搞笑
     * 연예	娱乐
     * 만화	动漫
     * 게임	游戏
     * 스포츠	体育
     * 생활	生活
     * 사회	社会
     * <p>
     * <p>
     * action与finalId的组合：控制拉取动作
     * <p>
     * action	finalId	说明
     * 0	空字符串	刚刚进入app
     * 1	最近一次拉取列表的最后一个feedId	上拉
     * 2	最近一次拉取列表的首个feedId	下拉
     */

    public String userId; //目前没有用户系统，用空字符串
    public String uuid; //客户端生成
    public String country; //国家，用空字符串
    public String lang; // 语言，空字符串
    public int hasWiFi; // "[int] 1为处于WiFi环境，否则是0",
    public DeviceInfo deviceInfo;
    public String interfaceName; //all foryou
    public String channel; // "[string] 见下面说明",
    public int action; // "[int] 拉取方式，1为上拉，2为下拉，0为刚进入app",
    public String finalId; //[string] feedId, 配合action使用，见下面说明"

    public int appLevelPullAction = -1; // 0 : pull local 50 top, 1 : pull down plus 50;


    public void init(String channelId, PullAction action, int appLevelPullAction, String finalId) {
        userId = "";
//        uuid = PreferencesUtil.getInstance(MainApplication.getInstance()).getUuid();
        uuid = "a9f983e9-5b6a-4a15-9a46-374bf7f02b19";

        lang = Locale.getDefault().getLanguage();
        country = Locale.getDefault().getCountry();
        if (Util.isWifi(MainApplication.getInstance())) {
            hasWiFi = 1;
        } else {
            hasWiFi = 0;
        }
        deviceInfo = new DeviceInfo();
        deviceInfo.init(MainApplication.getInstance());
        if (channelId == null || channelId.equals("all")) {
            interfaceName = "foryou_version_1_0";
        } else {
            interfaceName = "channel_version_1_0";
        }
        this.channel = channelId;

        this.action = action.action();
        this.appLevelPullAction = appLevelPullAction;
        this.finalId = finalId;
    }

    public static enum PullAction {
        Enter(0),
        Pull_Up(1),
        Pull_Down(2);

        private int action;

        private PullAction(int action) {
            this.action = action;
        }

        public int action() {
            return action;
        }
    }

}
