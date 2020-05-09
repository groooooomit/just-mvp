package just.mvp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * View 用来承载界面更新和接收用户输入，这里定义 View 的通用方法。
 * <p>
 * IView 继承自 {@link LifecycleOwner}，方便在 Presenter 中对 View 添加生命周期监听器
 * <p>
 * IView 继承自 {@link ViewModelStoreOwner}，因为在本框架中 Presenter 是由 ViewModel 承载的
 */
public interface IView extends LifecycleOwner, ViewModelStoreOwner {

    /**
     * 获取 View 的 Context 对象
     */
    Context getContext();

    /**
     * 获取 view 的 activity
     */
    FragmentActivity getActivity();

    /**
     * 获取当前 View 持有的数据，Activity 的数据来源于 getIntent()，Fragment 的数据来源于 getArguments()
     */
    ViewData getData();

    /**
     * 判断 View 是否活跃
     */
    boolean isActive();

    ///////////////////////////////////////////////////////////////////////////
    // 以下是常用的界面弹出消息，加了 default 关键字，不强制子类实现
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 显示 toast 消息
     */
    default void toast(@NonNull String msg) {
    }

    /**
     * 显示一个长的 toast 消息
     */
    default void toastLong(@NonNull String msg) {
    }

    /**
     * 显示 snack 消息，自动消失
     */
    default void snack(@NonNull String msg) {
    }

    /**
     * 显示一个长的 snack 消息，自动消失
     */
    default void snackLong(@NonNull String msg) {
    }

    /**
     * 显示 snack 消息，点击才消失
     */
    default void snackIndefinite(@NonNull String msg) {
    }

}
