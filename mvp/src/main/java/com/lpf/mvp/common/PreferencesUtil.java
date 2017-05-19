package com.lpf.mvp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.UUID;

public class PreferencesUtil {

    private static PreferencesUtil instance;

    private static SharedPreferences sharedPreferences;

    private PreferencesUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtil getInstance(Context context) {
        if (instance == null)
            instance = new PreferencesUtil(context);
        return instance;
    }

    public boolean isLoggedIn() {
        if (sharedPreferences != null && sharedPreferences.getString("user_name", null) != null
                && !"".equals(sharedPreferences.getString("user_name", ""))) {
            return true;
        }
        return false;
    }

    public String getUserName() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("user_name", "");
        }
        return "";
    }

    public boolean saveUserName(String name) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("user_name", name)
                    .commit();
        }
        return false;
    }

    public String getUserId() {
        if (sharedPreferences != null) {
            String userId = sharedPreferences.getString("userId", "");
            if (userId.equals("")) {
                userId = getUuid();
            }
            return userId;
        }
        return "";
    }

    public boolean saveUserId(String userId) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("userId", userId)
                    .commit();
        }
        return false;
    }

    public String getUuid() {
        if (sharedPreferences != null) {
            String uuid = sharedPreferences.getString("uuid", "");
            if (uuid.equals("")) {
                uuid = UUID.randomUUID().toString();
                saveUuid(uuid);
            }
            return uuid;
        }
        return "";
    }

    public boolean saveUuid(String uuid) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("uuid", uuid)
                    .commit();
        }
        return false;
    }

    public String getXLogId() {
        if (sharedPreferences != null) {
            String uuid = sharedPreferences.getString("xLogId", "");
            if (uuid.equals("")) {
                uuid = UUID.randomUUID().toString();
                saveXLogId(uuid);
            }
            return uuid;
        }
        return "";
    }

    public boolean saveXLogId(String uuid) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("xLogId", uuid).commit();
        }
        return false;
    }

    public boolean saveLastExitTime(long lastExitTime) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putLong("lastExitTime", lastExitTime).commit();
        }
        return false;
    }

    public long getLastExitTime() {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong("lastExitTime", -1);
        }
        return -1;
    }

    public String getSessionId() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("sid", "");
        }
        return "";
    }

    public boolean saveSessionId(String sid) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("sid", sid)
                    .commit();
        }
        return false;
    }

    public String getUserPhoto() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("user_photo", "");
        }
        return "";
    }

    public boolean saveUserPhoto(String uri) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("user_photo", uri)
                    .commit();
        }
        return false;
    }


    public String getCurrentChannel() {
        String userId = getUserId();
        if (sharedPreferences != null) {
            return sharedPreferences.getString("channel_" + userId, null);
        }
        return null;
    }

    public boolean saveCurrentChannel(String currentChannel) {
        String userId = getUserId();
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("channel_" + userId, currentChannel).commit();
        }
        return false;
    }

    public String getAllChannel() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("allChannel", null);
        }
        return null;
    }

    public boolean saveAllChannel(String allChannel) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("allChannel", allChannel).commit();
        }
        return false;
    }

    public String getChannelSample() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("channelSample", null);
        }
        return null;
    }

    public boolean saveChannelSample(String channelSample) {
        if (sharedPreferences != null) {
            return sharedPreferences.edit().putString("channelSample", channelSample).commit();
        }
        return false;
    }
}
