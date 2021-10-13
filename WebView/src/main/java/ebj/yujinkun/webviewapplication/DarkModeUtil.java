package ebj.yujinkun.webviewapplication;

import android.content.Context;
import android.content.res.Configuration;

public class DarkModeUtil {

    public static boolean isDarkMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }
}
