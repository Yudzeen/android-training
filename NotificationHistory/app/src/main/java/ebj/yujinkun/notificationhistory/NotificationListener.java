package ebj.yujinkun.notificationhistory;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = NotificationListener.class.getSimpleName();

    @Override
    public void onListenerConnected() {
        Log.d(TAG, "Listener Connected.");
    }

    @Override
    public void onListenerDisconnected() {
        Log.d(TAG, "Listener Disconnected.");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "Notification posted: " + sbn.toString());
        if (sbn.getPackageName().equals(Constants.YUJ_APP_NOTIFICATION_CREATOR)) {
            Log.d(TAG, "From custom app, handling posted notification");
            cancelNotification(sbn.getKey());
            Intent intent = new Intent(Constants.INTENT_ACTION_NOTIFICATION_POSTED);
            intent.putExtra(Constants.KEY_EXTRA_NOTIFICATION, sbn);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } else {
            Log.d(TAG, "From non-custom app, will not handle notif");
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "Notification removed: " + sbn.toString());
        Intent intent = new Intent(Constants.INTENT_ACTION_NOTIFICATION_REMOVED);
        intent.putExtra(Constants.KEY_EXTRA_NOTIFICATION, sbn);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}
