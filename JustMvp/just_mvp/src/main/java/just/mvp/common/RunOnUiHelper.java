package just.mvp.common;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;

public class RunOnUiHelper {
	/**
	 * 内置一个发送延时任务的 Handler
	 */
	private final Handler mHandler = new Handler(Looper.getMainLooper());

	/**
	 * 主线程运行一个 Runnable
	 */
	public final void runOnUiThread(Runnable action) {
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
	 */
	public final void runOnUiThread(long delayMillis, @Nullable Interceptor interceptor, Runnable action) {
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
		boolean intercept();
	}

	public final void clearAllRuns() {
		mHandler.removeCallbacksAndMessages(null);
	}

	public final void release() {
		clearAllRuns();
	}


}
