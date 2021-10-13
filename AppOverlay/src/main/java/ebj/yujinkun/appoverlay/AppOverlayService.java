package ebj.yujinkun.appoverlay;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class AppOverlayService extends Service {

    private static final String TAG = AppOverlayService.class.getSimpleName();

    View view;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created.");
        Toast.makeText(this, "Service created.", Toast.LENGTH_LONG).show();

        view = LayoutInflater.from(this).inflate(R.layout.overlay_fullscreen, null);
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
        );
        windowManager.addView(view, params);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed.");
        Toast.makeText(this, "Service destroyed.", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
