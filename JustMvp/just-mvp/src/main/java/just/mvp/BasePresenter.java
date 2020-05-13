package just.mvp;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import just.mvp.base.AbstractPresenter;
import just.mvp.base.IView;
import just.mvp.lifecycle.PresenterAutoClearAllUiRunsWrapper;
import just.mvp.lifecycle.PresenterAutoDetachViewWrapper;
import just.mvp.lifecycle.PresenterLifecycleOrderFixWrapper;
import just.mvp.lifecycle.PresenterLifecycleTrigger;
import just.mvp.uirun.UiActionExecutorImpl;
import just.mvp.uirun.ViewRunnable;

/**
 * Presenter 的基类
 * <p>
 * 能够感知 View 生命周期的 Presenter，对 View 生命周期的感知通过 {@link androidx.lifecycle.DefaultLifecycleObserver} 实现
 * ，attach view 后注册 view 的生命周期监听器，当 view destroy 后主动 detach view 释放 view 的引用
 * <p>
 * {@link PresenterLifecycleTrigger} 用于触发 Presenter 相关生命周期方法
 * <p>
 * {@link PresenterLifecycleOrderFixWrapper} 用于修正生命周期回调顺序
 * <p>
 * {@link PresenterAutoDetachViewWrapper} 用于在 Activity 或 Fragment onDestroy 之前触发 detachView 来解除 Presenter 和 View 的引用
 * <p>
 * {@link PresenterAutoClearAllUiRunsWrapper} 用于在 ViewModel 销毁之前清除 pending 的 action
 *
 * @param <V>
 */
public class BasePresenter<V extends IView> implements AbstractPresenter<V> {

    /**
     * Presenter 持有对 View 的引用
     */
    @Nullable
    private V view;

    /**
     * Presenter 被 new 出来后立即装载 Application，使得在 Presenter 中能够方便地获取到 Application 对象
     */
    @Nullable
    private Application application;

    /**
     * 便捷地更新 UI
     */
    @NonNull
    private final UiActionExecutorImpl<V> uiActionExecutorImpl = new UiActionExecutorImpl<>(this::getView);

    /**
     * 生命周期触发源
     */
    @NonNull
    private final PresenterLifecycleTrigger<V> trigger = new PresenterLifecycleTrigger<>(new PresenterLifecycleOrderFixWrapper<>(new PresenterAutoDetachViewWrapper<>(this, new PresenterAutoClearAllUiRunsWrapper<>(this, this))));

    @Nullable
    @Override
    public V getRawView() {
        return view;
    }

    @NonNull
    @Override
    public Application getApplication() {
        if (null == application) {
            throw new RuntimeException("Presenter doesn't initialize!");
        }
        return application;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Override
    public final void initialize(@NonNull Application application) {
        this.application = application;
        trigger.onInitialize();
    }

    @Override
    public final void attachView(@NonNull V view) {
        this.view = view;
        trigger.onAttachView(view);
    }

    @Override
    public final void detachView() {
        if (null != view) {
            trigger.onDetachView(view);
            view = null;
        }
    }

    @Override
    public final void cleared() {
        trigger.onCleared();
    }

    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void runOnUi(@NonNull ViewRunnable<V> action) {
        uiActionExecutorImpl.runOnUi(action);
    }

    @Override
    public void runOnUi(long delay, @NonNull ViewRunnable<V> action) {
        uiActionExecutorImpl.runOnUi(delay, action);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void runOnUi(@NonNull Object token, long delay, @NonNull ViewRunnable<V> action) {
        uiActionExecutorImpl.runOnUi(token, delay, action);
    }

    @Override
    public void clearUiRuns(@NonNull Object token) {
        uiActionExecutorImpl.clearUiRuns(token);
    }

    @Override
    public void clearAllUiRuns() {
        uiActionExecutorImpl.clearAllUiRuns();
    }

}
