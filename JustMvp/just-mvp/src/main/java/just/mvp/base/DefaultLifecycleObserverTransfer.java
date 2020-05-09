package just.mvp.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * 将 DefaultLifecycleObserver 的回调传递到 ViewLifecycleObserver，并在类名上使用 ViewLifecycleObserver，使得项目引入 lifecycle 时只需要 Implementation 而不必要 api
 */
public class DefaultLifecycleObserverTransfer implements DefaultLifecycleObserver {

    @NonNull
    private final ViewLifecycleObserver observer;

    public DefaultLifecycleObserverTransfer(@NonNull ViewLifecycleObserver observer) {
        this.observer = observer;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        observer.onCreate(owner);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        observer.onStart(owner);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        observer.onResume(owner);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        observer.onPause(owner);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        observer.onStop(owner);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        observer.onDestroy(owner);
    }
}
