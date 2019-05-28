package just.mvp;

import just.mvp.base.IView;
import just.mvp.base.ProxyViewPresenter;
import just.mvp.common.RunOnUiHelper;

/**
 * 内置一个 ui handler，用来便捷地更新 UI
 *
 * @param <V> View
 */
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
     * <p>
     * 当延时结束，准备执行 runnable 时，对 view 的状态进行判断。如果状态不是 active，那么不执行 runnable.
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
