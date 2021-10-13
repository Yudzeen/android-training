package ebj.yujinkun.appoverlay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int PERMISSION_REQUEST_CODE = 2520;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isOverlayEnabled()) {
            Log.d(TAG, "Overlay enabled, starting service.");
            startOverlayService();
        } else {
            Log.d(TAG, "Overlay not enabled, requesting permissions");
            showPermissionDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (isOverlayEnabled()) {
                Log.d(TAG, "Permission granted, starting service");
                startOverlayService();
            } else {
                Log.d(TAG, "Permission denied");
            }
        }
    }

    private void startOverlayService() {
        Intent intent = new Intent(this, AppOverlayService.class);
        startService(intent);
    }

    private boolean isOverlayEnabled(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(getApplicationContext());
        }
        return false;
    }

    private void showPermissionDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Permission required.");
        alertDialogBuilder.setMessage("Please allow to continue.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION), PERMISSION_REQUEST_CODE);
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
