package just.mvp.lifecycle;

import androidx.annotation.NonNull;

import just.mvp.base.IView;
import just.mvp.uirun.UiActionExecutor;

/**
 * 当执行完 {@link PresenterLifecycle#onCleared()} 时触发 {@link UiActionExecutor#clearAllUiRuns()} 方法清除正在 pending 的 action
 */
public class PresenterAutoClearAllUiRunsWrapper<V extends IView> extends PresenterLifecycleWrapper<V> {

    @NonNull
    private final UiActionExecutor<V> safeUiRunner;

    public PresenterAutoClearAllUiRunsWrapper(@NonNull UiActionExecutor<V> safeUiRunner, @NonNull PresenterLifecycle<V> origin) {
        super(origin);
        this.safeUiRunner = safeUiRunner;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        safeUiRunner.clearAllUiRuns();
    }
}
