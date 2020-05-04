package just.mvp;


import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;

import just.mvp.base.IView;
import just.mvp.llifecycle.LifeCyclePresenter;


/**
 * 内置一个 ui handler，用来便捷地更新 UI
 * <p>
 * lifeCycleOwner、viewModel、Activity/Fragment 销毁时生命周期：
 * <pre>
 *     activity:
 *          DefaultLifecycleObserver onDestroy
 *          viewModel onCleared
 *          onDestroy begin
 *                  fragmentA:
 *                          viewModel onCleared
 *                          DefaultLifecycleObserver onDestroy
 *                          onDestroy
 *                  fragmentB:
 *                          viewModel onCleared
 *                          DefaultLifecycleObserver onDestroy
 *                          onDestroy
 *                  fragmentC:
 *                          viewModel onCleared
 *                          DefaultLifecycleObserver onDestroy
 *                          onDestroy
 *          onDestroy end
 * <pre/>
 * <pre/>
 * Presenter 的生命周期长于 View，所以需要及时释放资源.
 *
 * @param <V> View
 */
public class BasePresenter<V extends IView> extends LifeCyclePresenter<V> {

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
    private void runIfViewActive(@NonNull Runnable action) {
        if (isViewActive()) {
            action.run();
        }
    }

    /**
     * 在 UI 线程执行
     */
    protected final void runOnUi(@NonNull Runnable action) {
        if (isMainThread()) {
            runIfViewActive(action);
        } else {
            uiHandler.post(() -> runIfViewActive(action));
        }
    }

    /**
     * 在 UI 线程延时执行
     */
    protected final void runOnUi(long delay, @NonNull Runnable action) {
        uiHandler.postDelayed(() -> runIfViewActive(action), delay);
    }

    /**
     * 在 UI 线程延时执行
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    protected final void runOnUi(@NonNull Object token, long delay, @NonNull Runnable action) {
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
    protected void onRelease() {
        clearAllUiRuns();
    }
}
