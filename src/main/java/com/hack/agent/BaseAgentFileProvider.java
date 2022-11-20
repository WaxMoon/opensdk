package com.hack.agent;

import static com.hack.opensdk.CmdConstants.CMD_FILE_PROVIDER_MAKE_URI;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import com.hack.opensdk.Cmd;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class BaseAgentFileProvider extends ContentProvider {
    private static final String TAG ="proxies." + BaseAgentFileProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        return false;
    }

    public static ContentProviderClient getClient(Context context, Uri uri) {
        ContentResolver resolver = context.getContentResolver();
        return resolver.acquireContentProviderClient(uri.getAuthority());
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.query(proxyOrTarget, projection, selection, selectionArgs, sortOrder);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        Log.e(TAG, "getType: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.getType(proxyOrTarget);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.insert(proxyOrTarget, values);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.delete(proxyOrTarget, selection, selectionArgs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(TAG, "update: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.update(proxyOrTarget, values, selection, selectionArgs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        Log.e(TAG, "bulkInsert: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.bulkInsert(proxyOrTarget, values);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String str)
            throws FileNotFoundException {
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        Log.e(TAG, "openAssetFile " + uri + ", str " + str + "->" + proxyOrTarget);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        Log.e(TAG, "openAssetFile: client result "+client );
        if (client != null) {
            try {
                AssetFileDescriptor descriptor =  client.openAssetFile(proxyOrTarget, str);
                Log.e(TAG, "openAssetFile  result " + uri + ", str " + str + "->" + descriptor);
                return descriptor;
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "openAssetFile  result "+uri ,e);
                throw new FileNotFoundException(uri.toString());


            }
        }
        return super.openAssetFile(uri, str);
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal)
            throws FileNotFoundException {
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        Log.e(TAG, "openAssetFile 2" + uri + ", str " + str + ", cancellationSignal " + cancellationSignal + "->" + proxyOrTarget);

        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                AssetFileDescriptor descriptor = client.openAssetFile(proxyOrTarget, str, cancellationSignal);
                Log.e(TAG, "openAssetFile 2  result " + proxyOrTarget + ", str " + str + "->" + client.getLocalContentProvider());
                return descriptor;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return super.openAssetFile(uri, str, cancellationSignal);
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String str)
            throws FileNotFoundException {
        Log.e(TAG, "openFile: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.openFile(proxyOrTarget, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return super.openFile(uri, str);
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal)
            throws FileNotFoundException {
        Log.e(TAG, "openFile: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.openFile(proxyOrTarget, str, cancellationSignal);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return super.openFile(uri, str, cancellationSignal);
    }

    @Override
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts)
            throws FileNotFoundException {
        Log.e(TAG, "openTypedAssetFile: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.openTypedAssetFileDescriptor(proxyOrTarget, mimeTypeFilter, opts);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return super.openTypedAssetFile(uri, mimeTypeFilter, opts);
    }

    @Override
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts,
                                                  CancellationSignal cancellationSignal)
            throws FileNotFoundException {
        Log.e(TAG, "openTypedAssetFile: "+uri );
        Uri proxyOrTarget = (Uri) Cmd.INSTANCE().exec(CMD_FILE_PROVIDER_MAKE_URI, uri);
        ContentProviderClient client = getClient(getContext(), proxyOrTarget);
        if (client != null) {
            try {
                return client.openTypedAssetFile(proxyOrTarget, mimeTypeFilter, opts, cancellationSignal);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return super.openTypedAssetFile(uri, mimeTypeFilter, opts, cancellationSignal);
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        Log.e(TAG, "applyBatch: " );
        if (operations != null) {
            Log.d(TAG, "applyBatch operations " + Arrays.toString(operations.toArray()));
        }
        return super.applyBatch(operations);
    }
}