package ebj.yujinkun.applauncher.data;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {

    private final String appName;
    private final String packageName;
    private final Drawable iconDrawable;
    private final Intent launchIntent;

    public AppInfo(String appName, String packageName, Drawable iconDrawable, Intent launchIntent) {
        this.appName = appName;
        this.packageName = packageName;
        this.iconDrawable = iconDrawable;
        this.launchIntent = launchIntent;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public Intent getLaunchIntent() {
        return launchIntent;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconDrawable=" + iconDrawable +
                ", launchIntent=" + launchIntent +
                '}';
    }
}
