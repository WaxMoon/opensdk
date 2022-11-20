package com.hack.server.core.transact;

import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_PKG;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_PROCESS;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_SPACE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.hack.opensdk.CmdConstants;
import com.hack.server.core.TransactCallback;

public class ProcessBindCallback implements TransactCallback {
    @Override
    public final Bundle transact(Context context, int cmd, Bundle extras) {
        if (cmd != CmdConstants.TRANSACT_CMD_PROCESS_BINDED) {
            return null;
        }
        int space = extras.getInt(TRANSACT_KEY_SPACE);
        String pkg = extras.getString(TRANSACT_KEY_PKG);
        String process = extras.getString(TRANSACT_KEY_PROCESS);
        onBindProcess(process, pkg, space);
        return null;
    }

    /**
     * @param process
     * @param pkg
     * @param space
     */
    protected void onBindProcess(String process, String pkg, int space) {
        Log.d("TransactProvider", String.format("agent process bind complete! " + "[space: %d] [pkg: %s] [process: %s]", space, pkg, process));
    }
}
