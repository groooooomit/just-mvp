package just.mvp.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import just.mvp.lifecycle.PresenterLifecycle;
import just.mvp.lifecycle.PresenterLifecycleLogWrapper;
import just.mvp.lifecycle.PresenterLifecycleTrigger;

public class BasePresenter<V extends IView> implements IPresenter<V>, PresenterLifecycle<V> {

    @Nullable
    private V view;

    @Nullable
    private Application application;

    @NonNull
    private final PresenterLifecycleTrigger<V> trigger =
            /* 用于触发 Presenter 相关生命周期方法. */
            new PresenterLifecycleTrigger<>(this,
                    /* 用于输出生命周期调用 log. */
                    new PresenterLifecycleLogWrapper<>(false, "PresenterLifecycle", this
                            /* 用于在 ViewModel 销毁之前清除 pending 的 action. */
//                                    new PresenterAutoClearAllUiRunsWrapper<>(this, this)
                    )
            );

    @Override
    public final void initialize(@NonNull Application application) {
        this.application = application;
        trigger.performOnInitialize(application);
    }

    @Override
    public final void attachView(@NonNull V view) {
        this.view = view;
        trigger.performOnAttachView(view);
    }

    @Override
    public final void detachView() {
        if (null != view) {
            trigger.performOnDetachView(view);
            view = null;
        }
    }

    @Override
    public final void cleared() {
        trigger.performOnCleared();
    }

    @NonNull
    protected final <T extends Application> T getApplication() {
        if (null == application) {
            throw new RuntimeException("Presenter doesn't initialize!");
        }
        //noinspection unchecked
        return (T) application;
    }

    /**
     * 返回 presenter 持有的 view 引用
     */
    @Nullable
    protected final V getRawView() {
        return view;
    }

    /**
     * 如果 View 处于非 active 状态，那么将返回 null
     */
    @Nullable
    protected final V getActiveView() {
        return (null != view && view.isActive()) ? view : null;
    }

}
