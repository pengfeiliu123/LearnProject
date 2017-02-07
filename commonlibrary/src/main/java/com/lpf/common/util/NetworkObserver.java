package com.lpf.common.util;

import com.lpf.common.interfaces.INetObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupengfei on 17/2/7.
 * Observer for network
 */

public class NetworkObserver {

    private static NetworkObserver instance = new NetworkObserver();

    public static NetworkObserver getInstance() {
        return instance;
    }

    private List<INetObserver> listenerList;

    private NetworkObserver() {
        listenerList = new ArrayList<INetObserver>();
    }

    public void addNetObserver(INetObserver observer) {
        if (observer != null && !listenerList.contains(observer)) {
            listenerList.add(observer);
        }
    }

    public void removeObserver(INetObserver observer) {
        if (observer != null && listenerList.contains(observer)) {
            listenerList.remove(observer);
        }
    }

    public void updateNetwork(boolean connected) {
        if (listenerList != null) {
            for (INetObserver listener : listenerList) {
                listener.updateNetState(connected);
            }
        }
    }
}
