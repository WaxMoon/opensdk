package com.hack.opensdk;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.hack.Features;

public class HackApplication extends Application {

    private static final boolean DEBUG = Features.DEBUG;
    private static final String TAG = HackApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (DEBUG) Log.d(TAG, "attachBaseContext start");
        Context engineContext = base;
        if (TextUtils.equals(base.getPackageName(), BuildConfig.MASTER_PACKAGE)) {
            engineContext.getSharedPreferences("hack", Context.MODE_PRIVATE).edit().putString("sp.assist.pkg", BuildConfig.ASSIST_PACKAGE).commit();
        } else {
            try {
                if (engineContext.getPackageManager().getPackageInfo(BuildConfig.MASTER_PACKAGE, 0).applicationInfo.uid == Process.myUid()) {
                    engineContext = base.createPackageContext(BuildConfig.MASTER_PACKAGE, 0);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "master package not install ");
            }
        }
        HackRuntime.install(engineContext, "version", true);
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
