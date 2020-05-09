package just.mvp.base;

import androidx.annotation.NonNull;

import just.mvp.IView;

/**
 * 感知 View 生命周期的 Presenter，内部通过 {@link androidx.lifecycle.DefaultLifecycleObserver} 实现
 * <p>
 * {@link AutoDetachViewDelegate} 用于在 Activity 或 Fragment onDestroy 之前触发 detachView 来解除 Presenter 和 View 的引用
 * <p>
 * {@link LifecycleOrderFixDelegate} 用于修正生命周期回调顺序
 */
public class LifecyclePresenter<V extends IView> extends AbstractPresenter<V> implements PresenterLifecycle<V> {

    @NonNull
    private final LifecycleOrderFixDelegate<V> delegate = new LifecycleOrderFixDelegate<>(new AutoDetachViewDelegate<>(this));

    @NonNull
    private final DefaultLifecycleObserverTransfer transfer = new DefaultLifecycleObserverTransfer(new PresenterLifecycleTransfer(delegate));

    @Override
    protected final void performOnInitialize() {
        delegate.onInitialize();
    }

    @Override
    protected final void performOnAttachView(@NonNull V view) {
        delegate.onAttachView(view);
        view.getLifecycle().addObserver(transfer);
    }

    @Override
    protected final void performOnDetachView(@NonNull V view) {
        view.getLifecycle().removeObserver(transfer);
        delegate.onDetachView(view);
    }

    @Override
    protected final void performOnCleared() {
        delegate.onCleared();
    }

}
