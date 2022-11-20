package com.hack.server.core.transact;

import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_FROM_TOKEN;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_INTENT;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_PKG;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_RESULT;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_SHELL_PKG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.hack.server.core.TransactCallback;
import com.hack.utils.IntentUtils;
import com.hack.utils.ThreadUtils;

public class OuterActivityMonitor implements TransactCallback {
    @Override
    public Bundle transact(Context context, int cmd, Bundle extras) {
        String callerPkg = extras.getString(TRANSACT_KEY_PKG);
        String callerShell = extras.getString(TRANSACT_KEY_SHELL_PKG);
        Intent intent = extras.getParcelable(TRANSACT_KEY_INTENT);
        IBinder callerActivityToken = extras.getBinder(TRANSACT_KEY_FROM_TOKEN);

        Log.d("TransactProvider", String.format("target intent not found! " +
                        "[caller: %s-%s] [fromToken: %s] [intent: %s]",
                callerPkg, callerShell, callerActivityToken, intent));


        Integer ret = startActivity(context, intent);
        if (ret != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(TRANSACT_KEY_RESULT, ret);
            return bundle;
        }
        return null;
    }

    public Integer startActivity(Context context, Intent intent) {
        if (IntentUtils.isSysLauncherHome(intent)) {
            ThreadUtils.postOnMainThread(() -> {
                //当前进程可能不在前台，startActivity可能失败，应该使用callerShell包去启动目标activity
                //所以辅包也需要注册TransactProvider
                context.startActivity(intent);
            });
            return 0;
        }
        return null;
    }
}
