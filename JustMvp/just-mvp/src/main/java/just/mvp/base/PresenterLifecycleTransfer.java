package just.mvp.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public class PresenterLifecycleTransfer implements ViewLifecycleObserver {

    @NonNull
    private final PresenterLifecycle presenter;

    public PresenterLifecycleTransfer(@NonNull PresenterLifecycle presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        presenter.afterViewCreate();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        presenter.afterViewStart();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        presenter.afterViewResume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        presenter.beforeViewPause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        presenter.beforeViewStop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        presenter.beforeViewDestroy();
    }

}
