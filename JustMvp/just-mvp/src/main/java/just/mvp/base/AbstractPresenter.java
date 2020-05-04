package just.mvp.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * {@link IPresenter} 的默认实现.
 *
 * @param <V> View
 */
public abstract class AbstractPresenter<V extends IView> implements IPresenter<V> {

    /**
     * Presenter 被 new 出来后立即装载 Application，使得在 Presenter 中能够方便地获取到 Application 对象
     */
    @Nullable
    private Application application;

    /**
     * Presenter 持有 View 的引用
     */
    @Nullable
    private V view;

    @Override
    public final void initialize(@NonNull Application application) {
        this.application = application;
        onInitialize();
    }


    @Override
    public final void attachView(@NonNull V view) {
        this.view = view;
        onAttachView(view);
    }

    @Override
    public final void detachView() {
        if (view != null) {
            onDetachView(view);
            this.view = null;
        }
    }

    @Override
    public final void onCleared() {
        onRelease();
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 安全地获取 View 的引用，当 view 处于 active 状态时，才能够得到它的引用，否则返回 null
     * <p>
     * 在异步执行了耗时任务后，想要更新 View，需要对 view 进行判空
     * <p>
     * 通常情况下，如果 View 非 active，那么不应该对 View 进行任何操纵
     */
    @Nullable
    protected final V getView() {
        return (null != view && view.isActive()) ? view : null;
    }

    /**
     * 不安全地获取 View 的引用，满足某些极端场景需要在 view 非 active 的时候使用 view.
     */
    @Deprecated
    @Nullable
    protected final V getRawView() {
        return view;
    }

    /**
     * 获取全局 Application 对象
     */
    @NonNull
    protected final Application getApplication() {
        if (null == application) {
            throw new RuntimeException("You should getApplication since onInitialize(). ");
        }
        return application;
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Presenter 创建并持有 Application 对象后回调
     */
    protected abstract void onInitialize();

    /**
     * Presenter 持有 View 的引用后回调
     */
    protected abstract void onAttachView(@NonNull V view);

    /**
     * Presenter 在即将解除 View 的引用前回调
     */
    protected abstract void onDetachView(@NonNull V view);

    /**
     * Presenter 跟随 ViewModel 一同被回收时回调
     */
    protected abstract void onRelease();

}
