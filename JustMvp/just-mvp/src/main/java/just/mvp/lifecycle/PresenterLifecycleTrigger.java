package just.mvp.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import just.mvp.base.IView;

/**
 * Presenter 生命周期触发器
 */
public class PresenterLifecycleTrigger<V extends IView> extends PresenterLifecycleWrapper<V> implements DefaultLifecycleObserver {

    public PresenterLifecycleTrigger(@NonNull PresenterLifecycle<V> origin) {
        super(origin);
    }

    @Override
    public void onAttachView(@NonNull V view) {
        super.onAttachView(view);
        view.getLifecycle().addObserver(this);
    }

    @Override
    public void onDetachView(@NonNull V view) {
        view.getLifecycle().removeObserver(this);
        super.onDetachView(view);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        afterViewCreate();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        afterViewStart();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        afterViewResume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        beforeViewPause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        beforeViewStop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        beforeViewDestroy();
    }
}
