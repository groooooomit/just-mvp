package just.mvp;

import just.mvp.base.IView;
import just.mvp.base.ProxyViewPresenter;
import just.mvp.common.RunOnUiHelper;

public class BasePresenter<V extends IView> extends ProxyViewPresenter<V> {

	/**
	 * UI Handler 工具
	 */
	private final RunOnUiHelper mRunOnUiHelper = new RunOnUiHelper();

	/**
	 * 在 UI 线程执行
	 */
	protected final void runOnUi(Runnable runnable) {
		if (isViewActive()) {
			mRunOnUiHelper.runOnUiThread(runnable);
		}
	}

	/**
	 * 在 UI 线程延时执行
	 *
	 * @param delay 延时的毫秒数
	 */
	protected final void runOnUi(long delay, Runnable runnable) {
		if (isViewActive()) {
			mRunOnUiHelper.runOnUiThread(delay, () -> !isViewActive(), runnable);
		}
	}

	/**
	 * 清空 mHandler 发出的任务
	 */
	protected final void clearAllRuns() {
		mRunOnUiHelper.clearAllRuns();
	}

	@Override
	public void onCleared() {
		super.onCleared();
		mRunOnUiHelper.release();
	}
}
