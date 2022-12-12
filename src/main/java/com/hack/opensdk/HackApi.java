package com.hack.opensdk;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.hack.Slog;
import com.hack.server.core.TransactCallback;
import com.hack.server.core.TransactRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class HackApi {
    private static final String TAG = HackApi.class.getSimpleName();
    private static final TransactRegistry sTransactRegistry = new TransactRegistry();

    public static void attachBaseContext(Application app, Context base) {
        Cmd.INSTANCE().exec(CmdConstants.CMD_APPLICATION_ATTACHBASE, app, base);
    }

    public static void onCreate() {
        Cmd.INSTANCE().exec(CmdConstants.CMD_APPLICATION_ONCREATE);
    }

    public static Bundle getPackageSetting(String pkg, int userId, int flags) {
        return (Bundle) Cmd.INSTANCE().exec(CmdConstants.CMD_GET_PKG_SETTINGS, pkg, userId, flags);
    }

    /**
     * @param packageName
     * @param userId
     * @param forceInstall
     * @return public static final int INSTALL_SUCCEEDED = 1;
     */
    public static int installPackageFromHost(String packageName, int userId, boolean forceInstall) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_INSTALL_PACKAGE,
                userId, packageName,
                forceInstall ? CmdConstants.MODE_FORCE_INSTALL : 0
        );
    }

    /**
     * @param packageName
     * @param userId
     * @return public static final int DELETE_SUCCEEDED = 1;
     */
    public static int uninstallPackage(String packageName, int userId) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_UNINSTALL_PACKAGE,
                userId,
                packageName,
                0
        );
    }

    /**
     * @param packageName
     * @param userId
     * @return public static final int DELETE_SUCCEEDED = 1;
     */
    public static int deletePackageData(String packageName, int userId) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_REMOVE_PKG_DATA,
                userId,
                packageName,
                0
        );
    }

    /**
     * @param packageName
     * @param userId
     * @return public static final int DELETE_SUCCEEDED = 1;
     */
    public static int deletePackageCache(String packageName, int userId) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_DELETE_PKG_CACHE,
                userId,
                packageName,
                0
        );
    }

    public static PackageInfo getPackageInfo(String packageName, int userId, int flags) {
        return (PackageInfo) Cmd.INSTANCE().exec(
                CmdConstants.CMD_GET_PACKAGE_INFO,
                userId,
                packageName,
                flags
        );
    }

    public static ResolveInfo resolveIntent(Intent intent, String resolvedType, int flags, int userId) {
        return (ResolveInfo) Cmd.INSTANCE().exec(
                CmdConstants.CMD_RESOLVE_INTENT,
                intent,
                resolvedType,
                flags,
                userId
        );
    }

    public static List<ResolveInfo> queryIntentActivities(Intent intent, String resolvedType, int flags, int userId) {
        return (List) Cmd.INSTANCE().exec(
                CmdConstants.CMD_QUERY_ACTIVITIES,
                intent,
                resolvedType,
                flags,
                userId
        );
    }

    public static ActivityInfo getActivityInfo(ComponentName component, int flags, int userId) {
        return (ActivityInfo) Cmd.INSTANCE().exec(
                CmdConstants.CMD_GET_ACTIVITY_INFO,
                component,
                flags,
                userId
        );
    }

    public static List<String> getInstalledPackages(int flags, int userId) {
        return (List<String>) Cmd.INSTANCE().exec(
                CmdConstants.CMD_GET_INSTALLED_PKGS,
                flags,
                userId
        );
    }


    public static Map<String, List<Integer>> getUnavailablePackages(int flags) {
        return (Map<String, List<Integer>>) Cmd.INSTANCE().exec(
                CmdConstants.CMD_GET_UNAVAILABLE_PKGS,
                flags, 0);
    }

    public static void registerTransactCallback(int cmd, TransactCallback callback) {
        sTransactRegistry.registerTransactCallback(cmd, callback);
    }

    public static void unregisterTransactCallback(int cmd) {
        sTransactRegistry.unregisterTransactCallback(cmd);
    }

    /**
     *
     * @param packageName
     * @param userId
     * @return
     */
    @Deprecated
    public static boolean startPackage(String packageName, int userId) {
        return (boolean) Cmd.INSTANCE().exec(
                CmdConstants.CMD_START_PACKAGE,
                packageName,
                userId,
                0
        );
    }

    /**
     * START_SUCCESS = 0;
     * START_RETURN_INTENT_TO_CALLER = 1
     * START_TASK_TO_FRONT = 2
     * START_DELIVERED_TO_TOP = 3
     * others...
     *
     * @param intent
     * @param userId
     * @return
     */
    public static int startActivity(Intent intent, int userId) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_START_ACTIVITY,
                intent,
                null,
                userId
        );
    }

    /**
     * @param callback callback必须要含有onPackageDeleted函数，hack engine会通过反射调用
     *                 public void onPackageDeleted(String packageName, int returnCode, String msg, int userId);
     * @return 返回值建议保存，unregisterDeleteObserver的参数需要使用该返回值
     */
    public static Object registerDeleteObserver(Object callback) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_REGISTER_UNINSTALL_OBSERVER,
                callback
        );
    }

    public static void unregisterDeleteObserver(Object observer) {
        Cmd.INSTANCE().exec(
                CmdConstants.CMD_UNREGISTER_UNINSTALL_OBSERVER,
                observer
        );
    }

    /**
     * @param callback callback必须要含有onPackageInstalled函数, hack engine会通过反射调用
     *                 public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras, int userId)
     * @return 返回值建议保存, unregisterInstallObserver的参数需要使用该返回值
     */
    public static Object registerInstallObserver(Object callback) {
        return (int) Cmd.INSTANCE().exec(
                CmdConstants.CMD_REGISTER_INSTALL_OBSERVER,
                callback
        );
    }

    public static void unregisterInstallObserver(Object observer) {
        Cmd.INSTANCE().exec(
                CmdConstants.CMD_UNREGISTER_INSTALL_OBSERVER,
                observer
        );
    }

    public static int[] getAvailableUserSpace() {
        return (int[]) Cmd.INSTANCE().exec(
                CmdConstants.CMD_GET_ALL_USERID
        );
    }

    public static int[] getInstallUsersForPackage(String packageName) {
        return (int[]) Cmd.INSTANCE().exec(CmdConstants.CMD_PKG_ALL_USERID, packageName);
    }

    public static int getAvailableUser(String packageName) {
        int userId = 0;
        long time = System.currentTimeMillis();
        int[] users = getInstallUsersForPackage(packageName);
        if (users != null) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                boolean found = false;
                for (int user : users) {
                    if (user == i) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    userId = i;
                    break;
                }
            }
        }
        Slog.v(TAG, "getUsers consume: " + (System.currentTimeMillis() - time) / 1000);
        return userId;
    }

    public static TransactRegistry getTransactRegistry() {
        return sTransactRegistry;
    }


    public static Intent getLaunchIntentForPackage(String packageName, int uerId) {
        // First see if the package has an INFO activity; the existence of
        // such an activity is implied to be the desired front-door for the
        // overall package (such as if it has multiple launcher entries).
        Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
        intentToResolve.addCategory(Intent.CATEGORY_INFO);
        intentToResolve.setPackage(packageName);
        List<ResolveInfo> ris = queryIntentActivities(intentToResolve, null, 0, uerId);

        // Otherwise, try to find a main launcher activity.
        if (ris == null || ris.size() <= 0) {
            // reuse the intent instance
            intentToResolve.removeCategory(Intent.CATEGORY_INFO);
            intentToResolve.addCategory(Intent.CATEGORY_LAUNCHER);
            intentToResolve.setPackage(packageName);
            ris = queryIntentActivities(intentToResolve, null, 0, uerId);
        }
        if (ris == null || ris.size() <= 0) {
            return null;
        }
        Intent intent = new Intent(intentToResolve);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(ris.get(0).activityInfo.packageName, ris.get(0).activityInfo.name);
        return intent;
    }

    public static Map<String, Object> getRuntimeProperties() {
        Map<String,Object> map =  (Map<String, Object>) Cmd.INSTANCE().exec(CmdConstants.CMD_GET_RUNTIME_PROPERTIES);
        return map;
    }

    public static <T> T getRuntimeProperty(String key, T defaultValue) {
        try {
            Object o = getRuntimeProperties().get(key);
            if (o == null) {
                return defaultValue;
            }
            return (T) o;
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public static void registerApplicationCallback(ApplicationCallback callback){
        Cmd.INSTANCE().exec(CmdConstants.CMD_REGISTER_APPLICATION_CALLBACK, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                int cmd = (int) args[0];
                switch (cmd) {
                    case 0: {
                        callback.onInitAppContext(args[1], (Context) args[2]);
                        break;
                    }
                    case 1: {
                        callback.onAttachBaseContext((Application) args[1]);
                        break;
                    }
                    case 2: {
                        callback.onInstallProviders((Application) args[1]);
                        break;
                    }
                    case 3: {
                        callback.onCreate((Application) args[1]);
                        break;
                    }

                }
                return null;
            }
        });
    }

    public interface ApplicationCallback {

        void onInitAppContext(Object loadedApk, Context appContext);

        void onAttachBaseContext(Application app);

        void onInstallProviders(Application app);

        void onCreate(Application app);

    }
}
