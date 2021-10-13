package ebj.habinyasuyujin.androidtraining.applauncher.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ebj.habinyasuyujin.androidtraining.applauncher.data.AppInfo;
import ebj.habinyasuyujin.androidtraining.applauncher.data.AppInfoManager;

public class HomeViewModel extends AndroidViewModel {

    private AppInfoManager appInfoManager;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appInfoManager = AppInfoManager.getInstance(application);
    }

    public LiveData<List<AppInfo>> getAppInfoList() {
        return appInfoManager.getAppInfoList();
    }
}