package com.lpf.mvp.drawable;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liupengfei on 2017/5/21 09:57.
 * init ThreadPool
 */

public abstract class LoadingMultiTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT + 3;
    private static final int KEEP_ALIVE = 10;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "LoadingAsyncTask#" + mCount.getAndIncrement());
        }
    };

    public static Executor mThread_Pool_Executor = null;

    // init Thread Pool
    public static void initThreadPool(){
        mThread_Pool_Executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                sPoolWorkQueue,
                sThreadFactory);
    }

    // execute
    public void executeDependSdk(Params... params) {
        if (mThread_Pool_Executor == null) {
            initThreadPool();
        }
        super.executeOnExecutor(mThread_Pool_Executor, params);
    }

}
