package com.lpf.common.receiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;

import com.lpf.common.util.NetworkObserver;
import com.lpf.common.util.NetworkUtil;

/**
 * Created by liupengfei on 17/2/7.
 * receive and change network state
 */

public class NetReceiver extends BroadcastReceiver {

    public static final int NET_CONNECTED = 1;
    public static final int NET_DISCONNECTED = 0;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == NET_CONNECTED) {
                NetworkObserver.getInstance().updateNetwork(true);
            } else {
                NetworkObserver.getInstance().updateNetwork(false);
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isConnected = NetworkUtil.isNetworkConnected(context);
            if (isConnected) {
                handler.sendEmptyMessage(NET_CONNECTED);
            } else {
                handler.sendEmptyMessage(NET_DISCONNECTED);
            }
        }
    }
}
