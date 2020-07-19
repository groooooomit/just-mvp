package just.mvp.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

/**
 * 以组合的方式使用 ViewModel
 */
public final class IViewModelContainer extends AndroidViewModel {

    @Nullable
    private IViewModel iViewModel;

    public IViewModelContainer(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public final <T extends IViewModel> T prepare(@NonNull Creator<T> creator) {
        if (null == iViewModel) {
            iViewModel = creator.create();
            iViewModel.initialize(getApplication());
        }
        //noinspection unchecked
        return (T) iViewModel;
    }

    @NonNull
    public final <T extends IViewModel> T require() {
        if (null == iViewModel) {
            throw new RuntimeException("ViewModel doesn't prepare or this container already cleared!");
        }
        //noinspection unchecked
        return (T) iViewModel;
    }

    @Override
    protected final void onCleared() {
        if (null != iViewModel) {
            iViewModel.cleared();
            iViewModel = null;
        }
    }

    @FunctionalInterface
    public interface Creator<T extends IViewModel> {
        @NonNull
        T create();
    }


}
