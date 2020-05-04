package just.mvp.llifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.Objects;

/**
 * 将 {@link DefaultLifecycleObserver} 的生命周期事件传递给 {@link IViewLifeCycleListener}
 */
public final class LifecycleTransfer implements DefaultLifecycleObserver {
    private IViewLifeCycleListener viewLifeCycleListener;

    public LifecycleTransfer(@NonNull IViewLifeCycleListener viewLifeCycleListener) {
        this.viewLifeCycleListener = Objects.requireNonNull(viewLifeCycleListener);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.afterViewCreate();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.afterViewStart();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.afterViewResume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.beforeViewPause();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.beforeViewStop();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.beforeViewDestroy();
    }

}
