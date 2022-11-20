package com.hack.agent;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Intent;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.hack.Features;
import com.hack.Slog;

public class HackNotificationService extends NotificationListenerService {
    private static final boolean DEBUG = false || Features.DEBUG;
    private static final String TAG = HackNotificationService.class.getSimpleName();

    public HackNotificationService() {
        super();
        if (DEBUG) {
            Slog.d(TAG, "Constructor in");
            new Exception().printStackTrace();
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationPosted in1");
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationPosted in2");
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationRemoved in1");
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationRemoved(sbn, rankingMap);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationRemoved in2");
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        super.onNotificationRemoved(sbn, rankingMap, reason);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationRemoved in3");
        }
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        if (DEBUG) {
            Slog.d(TAG, "onListenerConnected in");
        }
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        if (DEBUG) {
            Slog.d(TAG, "onListenerDisconnected in");
        }
    }

    @Override
    public void onNotificationRankingUpdate(RankingMap rankingMap) {
        super.onNotificationRankingUpdate(rankingMap);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationRankingUpdate in");
        }
    }

    @Override
    public void onListenerHintsChanged(int hints) {
        super.onListenerHintsChanged(hints);
        if (DEBUG) {
            Slog.d(TAG, "onListenerHintsChanged in");
        }
    }

    @Override
    public void onSilentStatusBarIconsVisibilityChanged(boolean hideSilentStatusIcons) {
        super.onSilentStatusBarIconsVisibilityChanged(hideSilentStatusIcons);
        if (DEBUG) {
            Slog.d(TAG, "onSilentStatusBarIconsVisibilityChanged in");
        }
    }

    @Override
    public void onNotificationChannelModified(String pkg, UserHandle user, NotificationChannel channel, int modificationType) {
        super.onNotificationChannelModified(pkg, user, channel, modificationType);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationChannelModified in");
        }
    }

    @Override
    public void onNotificationChannelGroupModified(String pkg, UserHandle user, NotificationChannelGroup group, int modificationType) {
        super.onNotificationChannelGroupModified(pkg, user, group, modificationType);
        if (DEBUG) {
            Slog.d(TAG, "onNotificationChannelGroupModified in");
        }
    }

    @Override
    public void onInterruptionFilterChanged(int interruptionFilter) {
        super.onInterruptionFilterChanged(interruptionFilter);
        if (DEBUG) {
            Slog.d(TAG, "onInterruptionFilterChanged in");
        }
    }

    @Override
    public StatusBarNotification[] getActiveNotifications() {
        if (DEBUG) {
            Slog.d(TAG, "getActiveNotifications in1");
        }
        return super.getActiveNotifications();
    }

    @Override
    public StatusBarNotification[] getActiveNotifications(String[] keys) {
        if (DEBUG) {
            Slog.d(TAG, "getActiveNotifications in2");
        }
        return super.getActiveNotifications(keys);
    }

    @Override
    public RankingMap getCurrentRanking() {
        if (DEBUG) {
            Slog.d(TAG, "getCurrentRanking in");
        }
        return super.getCurrentRanking();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (DEBUG) {
            Slog.d(TAG, "onBind in " + intent);
        }
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Slog.d(TAG, "onDestroy in ");
        }
    }
}
