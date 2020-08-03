package ebj.yujinkun.workmanagercodelab;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

import ebj.yujinkun.workmanagercodelab.workers.BlurWorker;
import ebj.yujinkun.workmanagercodelab.workers.CleanupWorker;

import static ebj.yujinkun.workmanagercodelab.Constants.IMAGE_MANIPULATION_WORK_NAME;
import static ebj.yujinkun.workmanagercodelab.Constants.KEY_IMAGE_URI;
import static ebj.yujinkun.workmanagercodelab.Constants.TAG_OUTPUT;

public class BlurViewModel extends AndroidViewModel {

    private WorkManager workManager;

    private Uri imageUri;
    private LiveData<List<WorkInfo>> savedWorkInfo;
    private Uri outputUri;

    public BlurViewModel(@NonNull Application application) {
        super(application);
        workManager = WorkManager.getInstance(application);
        savedWorkInfo = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT);
    }

    public void applyBlur(int blurLevel) {
        WorkContinuation continuation = workManager
                .beginUniqueWork(IMAGE_MANIPULATION_WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.from(CleanupWorker.class));

        for (int i = 0; i < blurLevel; i++) {
            OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(BlurWorker.class);
            if (i==0) {
                builder.setInputData(createInputDataForUri());
            }
            continuation = continuation.then(builder.build());
        }

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest save = new OneTimeWorkRequest.Builder(SaveImageToFileWorker.class)
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)
                .build();

        continuation = continuation.then(save);

        continuation.enqueue();
    }

    public void cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME);
    }

    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (imageUri != null) {
            builder.putString(KEY_IMAGE_URI, imageUri.toString());
        }
        return builder.build();
    }

    private Uri uriOrNull(String uriString) {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString);
        }
        return null;
    }

    LiveData<List<WorkInfo>> getOutputWorkInfo() { return savedWorkInfo; }

    void setOutputUri(String outputImageUri) {
        outputUri = uriOrNull(outputImageUri);
    }

    Uri getOutputUri() { return outputUri; }

    void setImageUri(String uri) {
        imageUri = uriOrNull(uri);
    }

    Uri getImageUri() {
        return imageUri;
    }

}
