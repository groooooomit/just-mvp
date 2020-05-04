package just.mvp.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

/**
 * View 中只定义 Presenter 需要用到的方法
 */
public interface IView extends LifecycleOwner {

	/**
	 * 获取 View 的 Context 对象，当 {@link IView#isActive()} 为 false 时， Context 为 null.
	 */
	@Nullable
	Context getContext();

	/**
	 * 判断 View 是否活跃
	 */
	boolean isActive();

	/**
	 * 显示消息
	 */
	void showMessage(@NonNull String msg);

}
