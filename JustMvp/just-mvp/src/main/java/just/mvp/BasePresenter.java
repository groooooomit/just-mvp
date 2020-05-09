package just.mvp;


import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.lifecycle.Lifecycle;

import just.mvp.base.LifecyclePresenter;

/**
 * 内置一个 ui handler，用来便捷地更新 UI
 *
 * @param <V> View
 */
public class BasePresenter<V extends IView> extends LifecyclePresenter<V> {

    /**
     * 访问 View 的入口方法，子类可以重写以实现自己的需求
     */
    @Nullable
    protected V getView() {
        return peekActiveView();
    }

    /**
     * 内置一个发送延时任务的 Handler
     */
    @NonNull
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    /**
     * 判断是否是 UI 线程
     */
    protected final boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 如果 View 处于 active 状态，就执行 action
     */
    @UiThread
    private void runIfViewActive(@NonNull ViewRunnable<V> action) {
        final V view = peekActiveView();
        if (null != view) {
            action.run(view);
        }
    }

    /**
     * 在 UI 线程执行，且 View 处于 active 状态
     */
    protected final void runOnUi(@NonNull ViewRunnable<V> action) {
        if (isMainThread()) {
            runIfViewActive(action);
        } else {
            uiHandler.post(() -> runIfViewActive(action));
        }
    }

    /**
     * 在 UI 线程延时执行，且 View 处于 active 状态
     */
    protected final void runOnUi(long delay, @NonNull ViewRunnable<V> action) {
        uiHandler.postDelayed(() -> runIfViewActive(action), delay);
    }

    /**
     * 在 UI 线程延时执行，且 View 处于 active 状态
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    protected final void runOnUi(@NonNull Object token, long delay, @NonNull ViewRunnable<V> action) {
        uiHandler.postDelayed(() -> runIfViewActive(action), token, delay);
    }

    /**
     * 清空 uiHandler 发出的任务
     */
    protected final void clearAllUiRuns() {
        uiHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 清空指定的尚未执行的任务
     */
    protected final void clearUiRuns(@NonNull Object token) {
        uiHandler.removeCallbacksAndMessages(token);
    }

    /**
     * 清除尚未执行的 runnable.
     */
    @CallSuper
    @Override
    public void onCleared() {
        clearAllUiRuns();
    }

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
