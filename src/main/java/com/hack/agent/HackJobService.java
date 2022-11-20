package com.hack.agent;

import static com.hack.opensdk.CmdConstants.CMD_AGENT_JOB_SERVICE_BIND;
import static com.hack.opensdk.CmdConstants.CMD_AGENT_JOB_SERVICE_UNBIND;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hack.opensdk.Cmd;

public class HackJobService extends Service {
    private static final String TAG = "HackJobService";

    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) Cmd.INSTANCE().exec(CMD_AGENT_JOB_SERVICE_BIND, intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return (boolean) Cmd.INSTANCE().exec(CMD_AGENT_JOB_SERVICE_UNBIND, intent);
    }
}
