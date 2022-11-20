package com.hack.server.core;

import android.content.Context;
import android.os.Bundle;

public interface TransactCallback  {
    Bundle transact(Context context,int cmd, Bundle extras);
}
