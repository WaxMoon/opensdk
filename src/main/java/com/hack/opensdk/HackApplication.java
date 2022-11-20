package com.hack.opensdk;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hack.Features;

public class HackApplication extends Application {

    private static final boolean DEBUG = Features.DEBUG;
    private static final String TAG = HackApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (DEBUG) Log.d(TAG, "attachBaseContext start");
        HackRuntime.install(this, "version", true);
        Cmd.INSTANCE().exec(CmdConstants.CMD_APPLICATION_ATTACHBASE, this, base);
        if (DEBUG) Log.d(TAG, "attachBaseContext end");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) Log.d(TAG, "onCreate start");
        Cmd.INSTANCE().exec(CmdConstants.CMD_APPLICATION_ONCREATE);
        if (DEBUG) Log.d(TAG, "onCreate end");
    }
}
