package just.mvp.lifecycle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.Presenters;

/**
 * Presenter 生命周期触发器
 */
public class PresenterLifecycleTrigger<V extends IView> {

    @NonNull
    private final IPresenter<V> presenter;

    @NonNull
    private final PresenterLifecycle<V> presenterLifecycle;

    public PresenterLifecycleTrigger(@NonNull IPresenter<V> presenter, @NonNull PresenterLifecycle<V> presenterLifecycle) {
        this.presenter = presenter;
        this.presenterLifecycle = presenterLifecycle;
    }

    public void performOnInitialize(@NonNull Application application) {
        presenterLifecycle.onInitialize(application);
    }

    public void performOnAttachView(@NonNull V view) {
        presenterLifecycle.onAttachView(view);
        Presenters.getFixedLifecycleOwnerOf(view).getLifecycle().addObserver(observer);
    }

    public void performOnDetachView(@NonNull V view) {
        Presenters.getFixedLifecycleOwnerOf(view).getLifecycle().removeObserver(observer);
        presenterLifecycle.onDetachView(view);
    }

    public void performOnCleared() {
        presenterLifecycle.onCleared();
    }

    private final DefaultLifecycleObserver observer = new DefaultLifecycleObserver() {
        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {
            presenterLifecycle.afterViewCreate();
        }

        @Override
        public void onStart(@NonNull LifecycleOwner owner) {
            presenterLifecycle.afterViewStart();
        }

        @Override
        public void onResume(@NonNull LifecycleOwner owner) {
            presenterLifecycle. afterViewResume();
        }

        @Override
        public void onPause(@NonNull LifecycleOwner owner) {
            presenterLifecycle.beforeViewPause();
        }

        @Override
        public void onStop(@NonNull LifecycleOwner owner) {
            presenterLifecycle.beforeViewStop();
        }

        @Override
        public void onDestroy(@NonNull LifecycleOwner owner) {
            presenterLifecycle.beforeViewDestroy();
            /* View destroy 时 presenter 释放解除对它的引用. */
            presenter.detachView();
        }
    };

}
