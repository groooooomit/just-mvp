package just.mvp.lifecycle;

import androidx.annotation.NonNull;

import just.mvp.base.IView;

public class PresenterLifecycleWrapper<V extends IView> implements PresenterLifecycle<V> {

    @NonNull
    private final PresenterLifecycle<V> origin;

    public PresenterLifecycleWrapper(@NonNull PresenterLifecycle<V> origin) {
        this.origin = origin;
    }

    @Override
    public void onInitialize() {
        origin.onInitialize();
    }

    @Override
    public void onAttachView(@NonNull V view) {
        origin.onAttachView(view);
    }

    @Override
    public void afterViewCreate() {
        origin.afterViewCreate();
    }

    @Override
    public void afterViewStart() {
        origin.afterViewStart();
    }

    @Override
    public void afterViewResume() {
        origin.afterViewResume();
    }

    @Override
    public void beforeViewPause() {
        origin.beforeViewPause();
    }

    @Override
    public void beforeViewStop() {
        origin.beforeViewStop();
    }

    @Override
    public void beforeViewDestroy() {
        origin.beforeViewDestroy();
    }

    @Override
    public void onDetachView(@NonNull V view) {
        origin.onDetachView(view);
    }

    @Override
    public void onCleared() {
        origin.onCleared();
    }

}
