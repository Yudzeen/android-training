package ebj.yujinkun.pagingsample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class CheeseViewModel extends AndroidViewModel {

    private static final int PAGE_SIZE = 60;

    private LiveData<PagedList<Cheese>> cheeseList;
    private CheeseDao cheeseDao;

    public CheeseViewModel(@NonNull Application application) {
        super(application);
        cheeseDao = CheeseDb.getInstance(application.getApplicationContext()).cheeseDao();
        cheeseList = new LivePagedListBuilder<>(cheeseDao.getAllCheesesByName(), PAGE_SIZE)
                .build();
    }

    LiveData<PagedList<Cheese>> getCheeseList() {
        return cheeseList;
    }


    void insert(CharSequence text) {
        AppExecutors.io().execute(() -> cheeseDao.insert(new Cheese(0, text.toString())));
    }

    void remove(Cheese cheese) {
        AppExecutors.io().execute(() -> cheeseDao.delete(cheese));
    }

}
