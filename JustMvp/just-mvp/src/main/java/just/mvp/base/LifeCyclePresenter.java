package just.mvp.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import just.mvp.common.llifecycle.IViewLifeCycleListener;
import just.mvp.common.llifecycle.LifecycleTransfer;

/**
 * 感知 View 生命周期的 Presenter，内部通过 {@link androidx.lifecycle.DefaultLifecycleObserver} 实现
 */
public class LifeCyclePresenter<V extends IView & LifecycleOwner> extends ViewModelPresenter<V> implements IViewLifeCycleListener {

    /**
     * 单独声明 LifecycleTransfer，防止重复创建多个实例
     */
    private final LifecycleTransfer mLifecycleTransfer = new LifecycleTransfer(this);

    @Override
    public void attachView(V view) {
        super.attachView(view);
        /* 添加 View 的生命周期观察者 */
        getView().getLifecycle().addObserver(mLifecycleTransfer);
    }

    /**
     * 判断 view 是否处于 resumed 状态
     *
     * @return 返回 true 表示 View 处于 resumed 状态
     */
    protected final boolean isViewResumed() {
        return isViewActive() && Lifecycle.State.RESUMED.equals(getView().getLifecycle().getCurrentState());
    }

    /**
     * 判断 view 是否处于 started 状态
     *
     * @return 返回 true 表示 View 处于 started 状态
     */
    public final boolean isViewStarted() {
        return isViewActive() && Lifecycle.State.STARTED.equals(getView().getLifecycle().getCurrentState());
    }

    /**
     * view 是否处于 active 状态
     * <p>
     * 在异步执行了耗时任务后，想要更新 View，可以通过此方法对 View 的状态进行判断
     * <p>
     * 通常情况下，如果 View 非 active，那么不应该对 View 进行任何操纵
     *
     * @return 返回 true 表示 View 处于 active 状态
     */
    protected final boolean isViewActive() {
        final V view = getView();
        return null != view && view.isActive();
    }


}
