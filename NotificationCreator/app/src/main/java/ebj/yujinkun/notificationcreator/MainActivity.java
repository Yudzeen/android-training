package ebj.yujinkun.notificationcreator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // unique per channel
    private static final String CHANNEL_ID = "Notification Creator";

    // unique per notification, can be used to update or delete it later
    private static final int NOTIFICATION_ID = 2520;

    EditText titleEditText;
    EditText descriptionEditText;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.title_text);
        descriptionEditText = findViewById(R.id.description_text);
        createButton = findViewById(R.id.create_notif_button);

        createNotificationChannel();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateButtonClicked(v);
            }
        });
    }

    private void onCreateButtonClicked(View v) {

        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        builder.setDeleteIntent(createDeletePendingIntent());

        Notification notification = builder.build();

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(NOTIFICATION_ID, notification);
    }

    private PendingIntent createDeletePendingIntent() {
        Intent dismissIntent = new Intent(this, NotificationBroadcastReceiver.class);
        dismissIntent.setAction(Constants.INTENT_ACTION_NOTIFICATION_DISMISSED);
        return PendingIntent.getBroadcast(this, Constants.REQUEST_CODE_NOTIFICATION_DISMISSED, dismissIntent, PendingIntent.FLAG_ONE_SHOT);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Creator";
            String description = "This is from notification creator";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            } else {
                Log.e(TAG, "Notification Manager is null");
            }
        }
    }
}
