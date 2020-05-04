package just.mvp.common.llifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.Objects;

public class LifecycleTransfer implements DefaultLifecycleObserver {
    private IViewLifeCycleListener viewLifeCycleListener;

    public LifecycleTransfer(@NonNull IViewLifeCycleListener viewLifeCycleListener) {
        this.viewLifeCycleListener = Objects.requireNonNull(viewLifeCycleListener);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.afterViewCreate();
        this.viewLifeCycleListener.onViewAny();
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.afterViewStart();
        this.viewLifeCycleListener.onViewAny();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.afterViewResume();
        this.viewLifeCycleListener.onViewAny();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.beforeViewPause();
        this.viewLifeCycleListener.onViewAny();
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.beforeViewStop();
        this.viewLifeCycleListener.onViewAny();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.viewLifeCycleListener.beforeViewDestroy();
        this.viewLifeCycleListener.onViewAny();
    }

}
