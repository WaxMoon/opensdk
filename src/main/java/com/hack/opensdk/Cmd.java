package com.hack.opensdk;

import com.hack.Features;
import com.hack.Slog;
import com.hack.utils.RefUtils;
import com.hack.utils.Singleton;

import java.util.Arrays;

public class Cmd {
    private static final String TAG = Cmd.class.getSimpleName();
    private static final boolean DEBUG = Features.DEBUG;

    private static final String ENGINE_CMD_CLASS = "com.core.hack.Cmd";

    private RefUtils.MethodRef mEngineExecMethod;

    public static Cmd INSTANCE() {
        return singleton.get();
    }

    private static Singleton<Cmd> singleton = new Singleton<Cmd>() {
        @Override
        protected Cmd create() {
            return new Cmd();
        }
    };

    private Cmd() {
        Class engineCmdClass = null;
        try {
            engineCmdClass = Class.forName(ENGINE_CMD_CLASS);
        } catch (ClassNotFoundException ignore) {
        }

        if (engineCmdClass == null) {
            try {
                engineCmdClass = HackRuntime.getHackClassLoader().loadClass(ENGINE_CMD_CLASS);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        mEngineExecMethod = new RefUtils.MethodRef(
                engineCmdClass,
                true, "exec",
                new Class[]{int.class, Object[].class});
    }

    public Object exec(int cmd, Object... args) {
        if (DEBUG) Slog.d(TAG, "begin exec %d %s", cmd, Arrays.toString(args));
        Object ret = mEngineExecMethod.invoke(null, cmd, args);
        if (DEBUG) Slog.d(TAG, "end exec %d %s", cmd, ret);
        return ret;
    }
}
