package ebj.yujinkun.applauncher.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ebj.yujinkun.applauncher.data.AppInfo;
import ebj.yujinkun.applauncher.data.AppInfoManager;

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