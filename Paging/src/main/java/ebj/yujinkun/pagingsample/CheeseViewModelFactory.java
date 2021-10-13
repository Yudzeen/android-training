package ebj.yujinkun.pagingsample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CheeseViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public CheeseViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CheeseViewModel.class)) {
            //noinspection unchecked
            return (T) new CheeseViewModel(application);
        }
        throw new IllegalArgumentException("Unable to construct view model");
    }

}
