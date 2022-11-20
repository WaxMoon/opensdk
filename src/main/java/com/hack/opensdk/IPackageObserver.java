package com.hack.opensdk;

import android.os.Bundle;

public class IPackageObserver {
    public interface Install {
        //can't proguard! must keep
        void onPackageInstalled(String basePackageName, int returnCode,
                                String msg, Bundle extras, int userId);
    }

    public interface Delete {
        //can't proguard! must keep
        void onPackageDeleted(String packageName, int returnCode, String msg, int userId);
    }
}
