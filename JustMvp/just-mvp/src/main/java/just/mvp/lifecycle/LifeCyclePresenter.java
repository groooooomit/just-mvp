package just.mvp.lifecycle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import just.mvp.base.AbstractPresenter;
import just.mvp.base.IView;


/**
 * 感知 View 生命周期的 Presenter，内部通过 {@link androidx.lifecycle.DefaultLifecycleObserver} 实现
 * <p>
 * 在 Activity 或 Fragment onDestroy 之前触发 detachView 来解除 Presenter 和 View 的引用，{@link AutoDetachViewWrapper}
 */
public abstract class LifeCyclePresenter<V extends IView> extends AbstractPresenter<V> implements IViewLifeCycleListener {

    /**
     * 单独声明 LifecycleTransfer，防止重复创建多个实例
     */
    private final LifecycleTransfer mLifecycleTransfer = new LifecycleTransfer(new AutoDetachViewWrapper<>(this));

    @Override
    public void onInitialize() {
        // do nothing.
    }

    @CallSuper
    @Override
    protected void onAttachView(@NonNull V view) {
        view.getLifecycle().addObserver(mLifecycleTransfer);
    }

    @CallSuper
    @Override
    protected void onDetachView(@NonNull V view) {
        view.getLifecycle().removeObserver(mLifecycleTransfer);
    }

    @Override
    protected void onRelease() {
        // do nothing.
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 判断 view 是否处于 created 状态
     *
     * @return 返回 true 表示 View 处于 created 状态
     */
    protected final boolean isViewCreated() {
        final V view = peekActiveView();
        return null != view && view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED);
    }

    /**
     * 判断 view 是否处于 started 状态
     *
     * @return 返回 true 表示 View 处于 started 状态
     */
    protected final boolean isViewStarted() {
        final V view = peekActiveView();
        return null != view && view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    /**
     * 判断 view 是否处于 resumed 状态
     *
     * @return 返回 true 表示 View 处于 resumed 状态
     */
    protected final boolean isViewResumed() {
        final V view = peekActiveView();
        return null != view && view.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

}
