package ebj.habinyasuyujin.androidtraining.applauncher.data;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class AppInfoManager {

    private static AppInfoManager instance = null;

    private MutableLiveData<List<AppInfo>> appInfoListLiveData = new MutableLiveData<>();

    public static AppInfoManager getInstance(Application app) {
        if (instance == null) {
            synchronized (AppInfoManager.class) {
                if (instance == null) {
                    instance = new AppInfoManager(app);
                }
            }
        }
        return instance;
    }

    private AppInfoManager(Application app) {
        init(app);
    }

    private void init(Application app) {
        initializeList(app);
    }

    private void initializeList(Application app) {
        PackageManager pm = app.getPackageManager();
        List<AppInfo> list = new ArrayList<>();
        for (ApplicationInfo appInfo: pm.getInstalledApplications(PackageManager.GET_META_DATA)) {
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packageName = appInfo.packageName;
            Drawable icon = pm.getApplicationIcon(appInfo);
            Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
            if (launchIntent == null) {
                // non-launchable app
                continue;
            }
            list.add(new AppInfo(appName, packageName, icon, launchIntent));
        }
        appInfoListLiveData.setValue(list);
    }

    public LiveData<List<AppInfo>> getAppInfoList() {
        return appInfoListLiveData;
    }
}
