package com.hack.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.Map;
import java.util.Set;

public class IntentUtils {

    public static String getPackage(Intent intent) {
        if (intent == null) {
            return null;
        }
        ComponentName cnn = intent.getComponent();
        if (cnn != null) {
            return cnn.getPackageName();
        }
        return intent.getPackage();
    }

    public static boolean isSysLauncherHome(Intent intent) {
        if (intent == null) {
            return false;
        }

        return intent.getAction() != null && TextUtils.equals(intent.getAction(), Intent.ACTION_MAIN)
                && intent.hasCategory(Intent.CATEGORY_HOME);
    }

    public static String toShortString(Intent intent) {
        if (intent != null) {
            StringBuilder builder = new StringBuilder();
            if (intent.getExtras() != null) {
                builder.append(toShortString(intent.getExtras()));
            }
            return builder.toString();
        }
        return null;
    }

    public static String toShortString(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(IntentUtils.class.getClassLoader());
            bundle.containsKey("test");//force unparcel
            if (bundle != null && bundle instanceof BaseBundle) {
                RefUtils.FieldRef<ArrayMap<String, Object>> filed_mMap =
                        new RefUtils.FieldRef(BaseBundle.class, false, "mMap");
                ArrayMap<String, Object> mMap = filed_mMap.get(bundle);
                if (mMap != null) {
                    StringBuilder builder = new StringBuilder("{<- ");
                    Set<Map.Entry<String, Object>> entrySet = mMap.entrySet();
                    for (Map.Entry<String, Object> entry : entrySet) {
                        builder.append("[" + entry.getKey());
                        builder.append(":");
                        Object value = entry.getValue();
                        if (value instanceof Bundle) {
                            builder.append(toShortString((Bundle)value));
                        } else if (value instanceof Intent) {
                            builder.append(toShortString((Intent)value));
                        } else {
                            builder.append(value);
                        }
                        builder.append("]");
                    }
                    builder.append(" ->}");
                    return builder.toString();
                }
            }
        }
        return null;
    }
}
