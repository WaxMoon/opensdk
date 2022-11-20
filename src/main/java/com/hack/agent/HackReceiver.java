package com.hack.agent;

import static com.hack.opensdk.CmdConstants.CMD_AGENT_INTENT_SENDER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hack.opensdk.Cmd;

public class HackReceiver extends BroadcastReceiver {

    private static final String TAG = HackReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Cmd.INSTANCE().exec(CMD_AGENT_INTENT_SENDER, context, intent);
    }
}
