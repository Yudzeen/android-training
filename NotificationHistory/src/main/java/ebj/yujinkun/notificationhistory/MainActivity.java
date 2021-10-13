package ebj.yujinkun.notificationhistory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import static ebj.yujinkun.notificationhistory.Constants.ACTION_NOTIFICATION_LISTENER_SETTINGS;
import static ebj.yujinkun.notificationhistory.Constants.ENABLED_NOTIFICATION_LISTENERS;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    NotifHistoryAdapter notifHistoryAdapter;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Intent received. Action: " + intent.getAction());
            if (intent.getAction() != null) {
                if (intent.getAction().equals(Constants.INTENT_ACTION_NOTIFICATION_POSTED)) {
                    StatusBarNotification sbn = intent.getParcelableExtra(Constants.KEY_EXTRA_NOTIFICATION);
                    handleNotificationPosted(sbn);
                } else if (intent.getAction().equals(Constants.INTENT_ACTION_NOTIFICATION_REMOVED)){
                    handleNotificationRemoved();
                }
            } else {
                Log.w(TAG, "Intent with null action");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNotificationServiceEnabled()) {
            showNotificationServiceAlertDialog();
        }

        notifHistoryAdapter = new NotifHistoryAdapter();

        RecyclerView recyclerView = findViewById(R.id.notif_list);
        recyclerView.setAdapter(notifHistoryAdapter);

        registerReceiver();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }

    private void handleNotificationPosted(StatusBarNotification sbn) {
        String appOrigin = sbn.getPackageName();
        String title = sbn.getNotification().extras.getString("android.title");
        String text = sbn.getNotification().extras.getString("android.text");
        NotifHistory notifHistory = new NotifHistory(appOrigin, title, text);
        notifHistoryAdapter.addNotifHistory(notifHistory);
    }

    private void handleNotificationRemoved() {

    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.INTENT_ACTION_NOTIFICATION_POSTED);
        intentFilter.addAction(Constants.INTENT_ACTION_NOTIFICATION_REMOVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private boolean isNotificationServiceEnabled(){
        ComponentName cn = new ComponentName(this, NotificationListener.class);
        String flat = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(cn.flattenToString());
    }

    private void showNotificationServiceAlertDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Permission required.");
        alertDialogBuilder.setMessage("Please allow to continue.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                });
        alertDialogBuilder.create().show();
    }
}
