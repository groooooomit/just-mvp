package just.mvp.uirun;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;

import just.mvp.base.IView;

public class UiActionExecutorImpl<V extends IView> implements UiActionExecutor<V> {
    /**
     * 内置一个发送延时任务的 Handler
     */
    @NonNull
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    @NonNull
    private final ViewProvider<V> viewProvider;

    public UiActionExecutorImpl(@NonNull ViewProvider<V> viewProvider) {
        this.viewProvider = viewProvider;
    }

    @UiThread
    private void runIfViewActive(@NonNull ViewRunnable<V> action) {
        final V view = viewProvider.getView();
        if (null != view && view.isActive()) {
            action.run(view);
        }
    }

    @Override
    public final void runOnUi(@NonNull ViewRunnable<V> action) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runIfViewActive(action);
        } else {
            uiHandler.post(() -> runIfViewActive(action));
        }
    }

    @Override
    public final void runOnUi(long delay, @NonNull ViewRunnable<V> action) {
        uiHandler.postDelayed(() -> runIfViewActive(action), delay);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public final void runOnUi(@NonNull Object token, long delay, @NonNull ViewRunnable<V> action) {
        uiHandler.postDelayed(() -> runIfViewActive(action), token, delay);
    }

    @Override
    public final void clearUiRuns(@NonNull Object token) {
        uiHandler.removeCallbacksAndMessages(token);
    }

    @Override
    public final void clearAllUiRuns() {
        uiHandler.removeCallbacksAndMessages(null);
    }

    public interface ViewProvider<V extends IView> {

        @Nullable
        V getView();
    }

}
