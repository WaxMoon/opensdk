package com.hack.server.core.transact;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.hack.Features;
import com.hack.opensdk.CmdConstants;
import com.hack.opensdk.HackApi;

public class TransactActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = TransactActivityLifecycle.class.getSimpleName();
    public static final TransactActivityLifecycle INSTANCE = new TransactActivityLifecycle();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (Features.DEBUG) {
            Log.e(TAG, "onActivityCreated: " + activity.getLocalClassName());
        }
        if (activity.isTaskRoot()) {
            fixTaskDescription(activity);
        }

    }

    private boolean fixTaskDescription(Activity activity) {
        int space = HackApi.getRuntimeProperty(CmdConstants.RUNTIME_PROPERTIES_SPACE, 0);
        ActivityInfo info = HackApi.getActivityInfo(new ComponentName(activity.getPackageName(), activity.getClass().getName()), 0, space);
        if (info == null) {
            Log.e(TAG, "fixTaskDescription: fail");
            return false;
        }
        PackageManager pm = activity.getPackageManager();
        Drawable data = info.loadIcon(pm);
        String label = String.valueOf(info.loadLabel(pm));
        if (space != 0) {
            label += "(" + (space + 1) + ")";
        }
        Bitmap icon;
        if (data instanceof BitmapDrawable) {
            icon = ((BitmapDrawable) data).getBitmap();
        } else {
            icon = Bitmap.createBitmap(data.getIntrinsicWidth(), data.getIntrinsicHeight(), data.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(icon);
            data.setBounds(0, 0, data.getIntrinsicWidth(), data.getIntrinsicHeight());
            data.draw(canvas);
        }
        activity.setTaskDescription(new ActivityManager.TaskDescription(label, icon));
        return true;
    }


    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
