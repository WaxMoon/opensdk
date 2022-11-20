package com.hack.server.core;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;

public class TransactRegistry implements TransactCallback {
    private final SparseArray<TransactCallback> transactCallbacks = new SparseArray<>();

    public TransactRegistry() {
    }

    public final void registerTransactCallback(int cmd, TransactCallback callback) {
        synchronized (transactCallbacks) {
            transactCallbacks.put(cmd, callback);
        }
    }

    public final void unregisterTransactCallback(int cmd) {
        synchronized (transactCallbacks) {
            transactCallbacks.remove(cmd);
        }

    }

    @Override
    public Bundle transact(Context context, int cmd, Bundle extra) {
        TransactCallback callback;
        synchronized (transactCallbacks) {
            callback = transactCallbacks.get(cmd);
        }
        if (callback != null) {
            return callback.transact(context, cmd, extra);
        }
        return null;
    }
}
