package com.hack.opensdk;

import android.app.Application;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.hack.Slog;
import com.hack.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;

public class HackRuntime {
    private static final String ENGINE_JAR_DIR = ".plugin";
    private static final String ENGINE_JAR_NAME = "hack.jar";
    private static ProviderInfo providerInfo;
    private static DexClassLoader hackClassLoader;

    public static void attachProviderInfo(ProviderInfo info) {
        HackRuntime.providerInfo = info;
    }

    public static boolean isHackProcess() {
        return HackRuntime.providerInfo != null;
    }

    public static ProviderInfo getHackProvider() {
        return providerInfo;
    }

    public static void install(Application app, String name, boolean check) {
        if (hackClassLoader != null) {
            return;
        }
        File root = new File(app.getFilesDir(), ENGINE_JAR_DIR);
        File config = new File(root, name + ".json");
        JSONObject object = readJson(config);
        File workspace = null;
        String workPath = object.optString("current");
        if (!TextUtils.isEmpty(workPath)) {
            workspace = new File(workPath);
        }
        if (check || TextUtils.isEmpty(workPath)) {
            if (!check) {
                Slog.e(HackRuntime.class.getName(), "engine load fail");
            }
            long time = object.optLong("time");
            long installTime = new File(app.getPackageCodePath()).lastModified();
            if (time != installTime) {
                if (workspace == null) {
                    workspace = new File(root, name + "-1");
                } else {
                    if (workPath.endsWith("-1")) {
                        workspace = new File(root, name + "-2");
                    } else {
                        workspace = new File(root, name + "-1");
                    }
                    FileUtils.deleteQuietly(new File(workPath));
                }
                File sdk = new File(workspace, "base.apk");
                try {
                    FileUtils.extractAsset(app, ENGINE_JAR_NAME, sdk);
                    FileUtils.extractFile(sdk, "lib/", workspace);
                    object.putOpt("current", workspace.getPath());
                    object.putOpt("time", installTime);
                    FileUtils.writeString(config, object.toString());
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
        loadEngine(workspace);
    }

    private static JSONObject readJson(File file) {
        String config = FileUtils.readString(file);
        if (config != null) {
            try {
                return new JSONObject(config);
            } catch (JSONException e) {
                return new JSONObject();
            }
        }
        return new JSONObject();
    }


    private static void loadEngine(File root) {
        File sdk = new File(root, "base.apk");
        File engineLibDir = new File(root, "lib");
        ArrayList<String> libDirs = new ArrayList<>();
        if (BuildConfig.isMasterPkg && Process.is64Bit()) {
            for (String abi : Build.SUPPORTED_64_BIT_ABIS) {
                File libDir = new File(engineLibDir, abi);
                if (libDir.exists()) libDirs.add(libDir.getAbsolutePath());
            }
        } else {
            for (String abi : Build.SUPPORTED_32_BIT_ABIS) {
                File libDir = new File(engineLibDir, abi);
                if (libDir.exists()) libDirs.add(libDir.getAbsolutePath());
            }
        }

        StringBuilder builder = new StringBuilder();

        int size = libDirs.size();
        for (int i = 0; i < size; i++) {
            builder.append(libDirs.get(i));
            if (i != (size - 1)) {
                builder.append(File.pathSeparator);
            }
        }

        String libSearchDir = builder.toString();


        hackClassLoader = new DexClassLoader(sdk.getPath(), sdk.getParent(), libSearchDir, Context.class.getClassLoader());
    }

    public static DexClassLoader getHackClassLoader() {
        return hackClassLoader;
    }

    public static void setHackClassLoader(DexClassLoader classLoader) {
        hackClassLoader = classLoader;
    }
}
