package ebj.yujinkun.applauncher.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ebj.yujinkun.applauncher.data.AppInfo;

public class AppUtil {

    private AppUtil() {}

    public static List<AppInfo> sortByAppNameIgnoreCase(List<AppInfo> appInfoList) {
        List<AppInfo> newList = new ArrayList<>(appInfoList);
        Collections.sort(newList, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                return o1.getAppName().compareToIgnoreCase(o2.getAppName());
            }
        });
        return newList;
    }
}
