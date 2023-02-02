package com.hack.server.core;

import static com.hack.opensdk.CmdConstants.TRANSACT_CMD_ACQUIRE_PROVIDER;
import static com.hack.opensdk.CmdConstants.TRANSACT_CMD_OUTER_INTENT;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_AUTHORITY;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_CMD;
import static com.hack.opensdk.CmdConstants.TRANSACT_KEY_INTENT;
import static com.hack.opensdk.CmdConstants.TRANSACT_PROVIDER_METHOD;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.hack.Features;
import com.hack.Slog;
import com.hack.opensdk.HackApi;
import com.hack.utils.IntentUtils;

public class TransactProvider extends ContentProvider {
    private static final String TAG = TransactProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (Features.DEBUG) {
            if (extras != null) {
                extras.size();
            }
            Slog.d(TAG, "m:" + method + " arg:" + arg + " extras:" + extras);
        }
        if (TextUtils.equals(method, TRANSACT_PROVIDER_METHOD)) {
            extras.setClassLoader(TransactProvider.class.getClassLoader());
            int cmd = extras.getInt(TRANSACT_KEY_CMD);

            if (cmd == TRANSACT_CMD_ACQUIRE_PROVIDER) {
                Uri uri = extras.getParcelable(TRANSACT_KEY_AUTHORITY);
                getContext().getContentResolver().acquireUnstableContentProviderClient(uri);
                return null;
            } else if (Features.DEBUG && cmd == TRANSACT_CMD_OUTER_INTENT) {
                Slog.d(TAG, "---->" + IntentUtils.toShortString((Intent) extras.getParcelable(TRANSACT_KEY_INTENT)));
            }
            return HackApi.getTransactRegistry().transact(getContext(), cmd, extras);
        }
        return null;
    }
}
