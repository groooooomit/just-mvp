package just.mvp.common;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RunOnUiHelper {
    /**
     * 内置一个发送延时任务的 Handler
     */
    private final Handler mHandler = new Handler(Looper.getMainLooper());


    /**
     * 主线程运行一个 Runnable
     *
     * @param action 要在 UI 线程执行的任务
     */
    public final void runOnUiThread(@NonNull Runnable action) {
        //noinspection ConstantConditions
        if (null == action) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else {
            mHandler.post(action);
        }
    }

    /**
     * 主线程延时执行一个 Runnable
     *
     * @param delayMillis delay 的毫秒数
     * @param interceptor 当 delay 完成，准备执行 action 的时候，如果 interceptor 决定拦截，那么最终不执行 action
     * @param action      要在 UI 线程执行的任务
     */
    public final void runOnUiThread(long delayMillis, @Nullable Interceptor interceptor, @NonNull Runnable action) {
        //noinspection ConstantConditions
        if (null == action) {
            return;
        }
        mHandler.postDelayed(() -> {
            if (null == interceptor || !interceptor.intercept()) {
                action.run();
            }
        }, delayMillis);
    }

    @FunctionalInterface
    public interface Interceptor {
        /**
         * 如果返回 true，那么将不执行 action
         */
        boolean intercept();
    }

    /**
     * 移除 mHandler 未执行的任务
     */
    public final void clearAllRuns() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public final void release() {
        clearAllRuns();
    }


}
