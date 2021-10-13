package ebj.yujinkun.workmanagercodelab.workers;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import ebj.yujinkun.workmanagercodelab.Constants;
import ebj.yujinkun.workmanagercodelab.R;
import ebj.yujinkun.workmanagercodelab.WorkerUtils;

import static ebj.yujinkun.workmanagercodelab.Constants.*;

public class BlurWorker extends Worker {

    private static final String TAG = BlurWorker.class.getSimpleName();

    public BlurWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();

        WorkerUtils.makeStatusNotification("Blurring image", applicationContext);
        WorkerUtils.sleep();
        String resourceUri = getInputData().getString(KEY_IMAGE_URI);

        try {

            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri");
                throw new IllegalArgumentException("Invalid input uri");
            }

            ContentResolver resolver = applicationContext.getContentResolver();

            // create a bitmap
            Bitmap picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)));

            // blur bitmap
            Bitmap output = WorkerUtils.blurBitmap(picture, applicationContext);

            // write to file
            Uri outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output);

            Data outputData = new Data.Builder()
                    .putString(KEY_IMAGE_URI, outputUri.toString())
                    .build();

            return Result.success(outputData);
        } catch (Throwable throwable) {
            Log.e(TAG, "Error applying blur", throwable);
            return Result.failure();
        }
    }
}
