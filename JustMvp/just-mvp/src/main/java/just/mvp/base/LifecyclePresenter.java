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

    /**
     * 修正生命周期调用顺序
     */
    @NonNull
    private final LifecycleOrderFixDelegate<V> delegate = new LifecycleOrderFixDelegate<>(new AutoDetachViewDelegate<>(this));

    /**
     * 将 view 的生命周期传递给 delegate，最后经过 delegate 的修正后回调当前 presenter 的对应 PresenterLifecycle 生命周期
     */
    @NonNull
    private final DefaultLifecycleObserverTransfer transfer = new DefaultLifecycleObserverTransfer(new PresenterLifecycleTransfer(delegate));

    @Override
    protected final void performOnInitialize() {
        delegate.onInitialize();
    }

    @Override
    protected final void performOnAttachView(@NonNull V view) {
        /*
         * 注意顺序很重要，先 attachView，这样 presenter 才有了 view 的引用，再注册 lifecycle 生命周期监听器，这样
         * 当任意一个 view 相关的生命周期触发时，view 的引用都是可用的。
         */
        delegate.onAttachView(view);
        view.getLifecycle().addObserver(transfer);
    }

    @Override
    protected final void performOnDetachView(@NonNull V view) {
        /* 与 attach view 相反，先移除生命周期监听器，再 detach view. */
        view.getLifecycle().removeObserver(transfer);
        delegate.onDetachView(view);
    }

    @Override
    protected final void performOnCleared() {
        delegate.onCleared();
    }

}
