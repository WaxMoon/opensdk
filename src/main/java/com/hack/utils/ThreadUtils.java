package com.hack.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    private static Thread sMainThread;
    private static Handler sMainThreadHandler;
    private static volatile ThreadPoolExecutor sThreadExecutor;

    public static final int ASYNC_MAX_THREAD = 4;

    static {
        sMainThread = Looper.getMainLooper().getThread();
        sMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Returns true if the current thread is the UI thread.
     */
    public static boolean isMainThread() {
        return Thread.currentThread() == sMainThread;
    }

    /**
     * Returns a shared UI thread handler.
     */
    public static Handler getUiThreadHandler() {
        return sMainThreadHandler;
    }

    /**
     * Checks that the current thread is the UI thread. Otherwise throws an exception.
     */
    public static void ensureMainThread() {
        if (!isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
    }

    /**
     * Posts runnable in background using shared background thread pool.
     *
     * @Return A future of the task that can be monitored for updates or cancelled.
     */
    public static Future postOnBackgroundThread(Runnable runnable) {
        return getThreadExecutor().submit(runnable);
    }

    /**
     * Posts callable in background using shared background thread pool.
     *
     * @Return A future of the task that can be monitored for updates or cancelled.
     */
    public static Future postOnBackgroundThread(Callable callable) {
        return getThreadExecutor().submit(callable);
    }

    /**
     * Posts the runnable on the main thread.
     */
    public static void postOnMainThread(Runnable runnable) {
        getUiThreadHandler().post(runnable);
    }

    private static ExecutorService getThreadExecutor() {
        if (sThreadExecutor == null) {
            synchronized (ThreadUtils.class) {
                if (sThreadExecutor == null) {
                    int coreNum = Runtime.getRuntime().availableProcessors();
                    if (coreNum > ASYNC_MAX_THREAD) coreNum = ASYNC_MAX_THREAD;

                    sThreadExecutor = new ThreadPoolExecutor(coreNum, coreNum,
                            5L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>());
                    sThreadExecutor.allowCoreThreadTimeOut(true);
                }
            }
        }
        return sThreadExecutor;
    }
}
