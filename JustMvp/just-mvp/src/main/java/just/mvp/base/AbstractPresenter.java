package just.mvp.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import just.mvp.lifecycle.PresenterLifecycle;
import just.mvp.uirun.UiActionExecutor;

/**
 * 申明 Presenter 对外暴露的方法
 *
 * @param <V>
 */
public interface AbstractPresenter<V extends IView> extends IPresenter<V>, PresenterLifecycle<V>, UiActionExecutor<V> {

    /**
     * 直接获取 View 的引用，一般情况下应该使用 {@link AbstractPresenter#getView()}
     */
    @Nullable
    V getRawView();

    /**
     * 获取全局 Application 对象
     */
    @NonNull
    Application getApplication();

    /**
     * 安全地获取 View 的引用，当 view 处于 active 状态时，才能够得到它的引用，否则返回 null
     * <p>
     * 在异步执行了耗时任务后，想要更新 View，需要对 view 进行判空
     * <p>
     * 通常情况下，如果 View 非 active，那么不应该对 View 进行任何操纵
     */
    @Nullable
    default V getView() {
        final V rawView = getRawView();
        return null != rawView && rawView.isActive() ? rawView : null;
    }

    /**
     * 根据 View 自身类型获取 ViewModelProvider
     */
    @Nullable
    default ViewModelProvider getViewModelProvider() {
        final V rawView = getRawView();
        return null != rawView && rawView.isActive() ? new ViewModelProvider(rawView, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())) : null;
    }

    /**
     * 获取 View 所属 Activity 的 ViewModelProvider
     */
    @Nullable
    default ViewModelProvider getActivityViewModelProvider() {
        final V rawView = getRawView();
        if (null != rawView && rawView.isActive()) {
            final FragmentActivity hostActivity = rawView.getHostActivity();
            return null != hostActivity ? new ViewModelProvider(hostActivity, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())) : null;
        } else {
            return null;
        }
    }

    /**
     * 判断 View 是否处于 active 状态
     */
    default boolean isViewActive() {
        final V rawView = getRawView();
        return null != rawView && rawView.isActive();
    }

    /**
     * 判断 view 是否处于 created 状态
     */
    default boolean isViewCreated() {
        final V rawView = getRawView();
        return null != rawView && rawView.isActive() && rawView.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED);
    }

    /**
     * 判断 view 是否处于 started 状态
     */
    default boolean isViewStarted() {
        final V rawView = getRawView();
        return null != rawView && rawView.isActive() && rawView.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    /**
     * 判断 view 是否处于 resumed 状态
     */
    default boolean isViewResumed() {
        final V rawView = getRawView();
        return null != rawView && rawView.isActive() && rawView.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }

}
