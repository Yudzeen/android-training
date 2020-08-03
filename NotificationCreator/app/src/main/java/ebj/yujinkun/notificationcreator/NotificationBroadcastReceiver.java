package ebj.yujinkun.notificationcreator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent received: " + intent.toString());
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Constants.INTENT_ACTION_NOTIFICATION_DISMISSED)) {
                Toast.makeText(context, "Notification dismissed!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
