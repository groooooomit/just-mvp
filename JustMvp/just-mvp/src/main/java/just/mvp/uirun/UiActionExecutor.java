package just.mvp.uirun;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import just.mvp.base.IView;

/**
 * 用来便捷地更新 UI
 *
 * @param <V>
 */
public interface UiActionExecutor<V extends IView> {

    /**
     * 在 UI 线程执行，且 View 处于 active 状态
     */
    void runOnUi(@NonNull ViewRunnable<V> action);

    /**
     * 在 UI 线程延时执行，且 View 处于 active 状态
     */
    void runOnUi(long delay, @NonNull ViewRunnable<V> action);

    /**
     * 在 UI 线程延时执行，且 View 处于 active 状态
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    void runOnUi(@NonNull Object token, long delay, @NonNull ViewRunnable<V> action);

    /**
     * 清空指定的尚未执行的任务
     */
    void clearUiRuns(@NonNull Object token);

    /**
     * 清空 uiHandler 发出的任务
     */
    void clearAllUiRuns();

}
