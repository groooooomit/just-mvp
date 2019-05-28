package just.mvp.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import just.mvp.common.IViewLifeCycleListener;
import just.mvp.common.LifecycleTransfer;

/**
 * 感知 View 生命周期的 Presenter
 */
public class LifeCyclePresenter<V extends IView & LifecycleOwner> extends ViewModelPresenter<V> implements IViewLifeCycleListener {

	/* 单独声明 LifecycleTransfer，防止重复创建多个实例. */
	private final LifecycleTransfer mLifecycleTransfer = new LifecycleTransfer(this);

	@Override
	public void attachView(V view) {
		super.attachView(view);
		/* 添加 View 的生命周期观察者 */
		getView().getLifecycle().addObserver(mLifecycleTransfer);
	}

	/**
	 * view 是否处于 resumed 状态
	 */
	protected final boolean isViewResumed() {
		return isViewActive() && Lifecycle.State.RESUMED.equals(getView().getLifecycle().getCurrentState());
	}

	/**
	 * view 是否处于 started 状态
	 */
	public final boolean isViewStarted() {
		return isViewActive() && Lifecycle.State.STARTED.equals(getView().getLifecycle().getCurrentState());
	}

	/**
	 * view 是否处于 active 状态
	 */
	protected final boolean isViewActive() {
		final V view = getView();
		return null != view && view.isActive();
	}


}
