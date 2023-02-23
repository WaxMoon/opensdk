package com.hack.utils;

import static com.hack.Features.DEBUG;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ProcessUtils {
    private static String sCurProcessName;
    private static ProcessType sProcessType = ProcessType.TYPE_UNKNOWN;
    public enum ProcessType {
        TYPE_UNKNOWN,
        TYPE_CLIENT,
        TYPE_APP,
        TYPE_ASSIST,
        TYPE_SERVICE
    }

    public static ProcessType tryGetProcessType(Context context) {
        if (sProcessType == ProcessType.TYPE_UNKNOWN) {
            final String hostPkg = context.getPackageName();
            String processName = ProcessUtils.getCurProcessName(context);
            if (TextUtils.equals(processName, hostPkg)
                    || TextUtils.equals(processName, hostPkg + ":client")) {
                sProcessType = ProcessType.TYPE_CLIENT;
            } else if (TextUtils.equals(processName, hostPkg + ":core")) {
                sProcessType = ProcessType.TYPE_SERVICE;
            } else if (TextUtils.equals(processName, hostPkg + ":assist")) {
                sProcessType = ProcessType.TYPE_ASSIST;
            } else {
                sProcessType = ProcessType.TYPE_APP;
            }
        }

        return sProcessType;
    }

    public static boolean isService() {
        return sProcessType == ProcessType.TYPE_SERVICE;
    }

    public static boolean isClient() {
        return sProcessType == ProcessType.TYPE_CLIENT;
    }

    public static boolean isAssist() {
        return sProcessType == ProcessType.TYPE_ASSIST;
    }

    public static boolean isApp() {
        return sProcessType == ProcessType.TYPE_APP;
    }

    public static String getCurProcessName(Context context) {
        String procName = sCurProcessName;
        if (!TextUtils.isEmpty(procName)) {
            return procName;
        }
        try {
            int pid = Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo info : mActivityManager.getRunningAppProcesses()) {
                if (info.pid == pid) {
                    sCurProcessName = info.processName;
                    return sCurProcessName;
                }
            }
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
        }
        sCurProcessName = getProcessNameFromProc(-1);
        return sCurProcessName;
    }

    public static String getProcessNameFromProc(int pid) {
        BufferedReader cmdlineReader = null;
        if (pid == -1) {
            pid = Process.myPid();
        }
        try {
            cmdlineReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "/proc/" + pid + "/cmdline"),
                    "iso-8859-1"));
            int c;
            StringBuilder processName = new StringBuilder();
            while ((c = cmdlineReader.read()) > 0) {
                processName.append((char) c);
            }
            return processName.toString();
        } catch (Throwable e) {
            // ignore
        } finally {
            if (cmdlineReader != null) {
                try {
                    cmdlineReader.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        return null;
    }

    public static String typeToString(ProcessType type) {
        switch (type) {
            case TYPE_APP:
                return type + "#" + "TYPE_APP";
            case TYPE_CLIENT:
                return type + "#" + "TYPE_CLIENT";
            case TYPE_SERVICE:
                return type + "#" + "TYPE_SERVICE";
            case TYPE_ASSIST:
                return type + "#" + "TYPE_ASSIST";
            default:
                return null;
        }
    }
}
