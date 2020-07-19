package just.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * View 用来承载界面更新、数据展示和接收用户输入，只定义 Presenter 需要对 View 调用的方法
 * <p>
 * IView 继承自 {@link ViewModelStoreOwner}，因为在本框架中 Presenter 是由 ViewModel 承载的
 */
public interface IView extends ViewModelStoreOwner {

    /**
     * 获取 View 的 Context
     */
    @NonNull
    default Context getHostContext() {
        return Presenters.getHostContextOf(this);
    }

    /**
     * 获取当前 View 持有的数据，Activity 的数据来源于 getIntent()，Fragment 的数据来源于 getArguments()
     */
    @Nullable
    default Bundle getArgs() {
        return Presenters.getArgsOf(this);
    }

    /**
     * 获取正确的 LifecycleOwner 对象
     */
    @NonNull
    default LifecycleOwner getLifecycleOwner() {
        return Presenters.getFixedLifecycleOwnerOf(this);
    }

    /**
     * 判断 View 是否还可更新
     */
    default boolean isActive() {
        return getLifecycleOwner().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内置两个常用的弹出消息的方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 显示 toast 消息
     */
    default void toast(@NonNull String msg) {
        Toast.makeText(getHostContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长的 toast 消息
     */
    default void toastLong(@NonNull String msg) {
        Toast.makeText(getHostContext(), msg, Toast.LENGTH_LONG).show();
    }

}
