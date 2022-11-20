package com.hack.server.core;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.hack.Features;
import com.hack.agent.ProviderBase;
import com.hack.opensdk.Cmd;
import com.hack.opensdk.CmdConstants;
import com.hack.utils.ThreadUtils;

public class HackServerProvider extends ProviderBase {
    private static final boolean DEBUG = Features.DEBUG;
    private static final String TAG = HackServerProvider.class.getSimpleName();

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
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
    public boolean onCreate() {
        ThreadUtils.postOnBackgroundThread(()->{
            getContext().startService(new Intent(getContext(), ForgroundService.class));
        });
        return (boolean) Cmd.INSTANCE().exec(CmdConstants.CMD_CORE_PROVIDER_CREATE, getContext());
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int getProviderCallType() {
        return CmdConstants.CMD_CORE_PROVIDER_CALL;
    }
}