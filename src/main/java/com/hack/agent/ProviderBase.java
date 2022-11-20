package com.hack.agent;

import android.app.Application;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Bundle;

import com.hack.Features;
import com.hack.opensdk.Cmd;
import com.hack.opensdk.HackRuntime;
import com.hack.server.core.transact.TransactActivityLifecycle;

public abstract class ProviderBase extends AppAgentFileProvider {
    private static final boolean DEBUG = Features.DEBUG;
    private static final String TAG = ProviderBase.class.getSimpleName();

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        Application application = (Application) context.getApplicationContext();
        application.registerActivityLifecycleCallbacks(TransactActivityLifecycle.INSTANCE);
        HackRuntime.attachProviderInfo(info);
        super.attachInfo(context, info);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        return (Bundle) Cmd.INSTANCE().exec(getProviderCallType(), method, arg, extras);
    }

    public abstract int getProviderCallType();
}
