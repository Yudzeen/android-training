package ebj.yujinkun.pagingsample;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final int NETWORK_THREAD_COUNT = 3;

    private static final Executor ioExecutor = Executors.newSingleThreadExecutor();
    private static final Executor networkExecutor = Executors.newFixedThreadPool(NETWORK_THREAD_COUNT);
    private static final Executor mainThreadExecutor = new MainThreadExecutor();

    public static Executor io() {
        return ioExecutor;
    }

    public static Executor network() {
        return networkExecutor;
    }

    public static Executor mainThread() {
        return mainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }

    }

}
