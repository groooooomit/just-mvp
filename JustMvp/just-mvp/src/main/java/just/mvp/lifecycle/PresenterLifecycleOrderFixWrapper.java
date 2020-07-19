package just.mvp.lifecycle;

import android.app.Application;

import androidx.annotation.NonNull;

import just.mvp.base.IView;

/**
 * @deprecated 通过 Lifecycle 监听 Fragment 生命周期时，使用 {@link androidx.fragment.app.Fragment#getViewLifecycleOwner()} 获取 Lifecycle 就不会出现下述的生命周期问题了
 * <p> 原文：
 * <p>
 * 修正 presenter 生命周期方法调用顺序
 * <p>
 * lifeCycleOwner、viewModel、Activity/Fragment 销毁时生命周期：
 * <pre>
 *     activity:
 *          DefaultLifecycleObserver onDestroy
 *          viewModel onCleared
 *          onDestroy begin
 *                  fragmentA:
 *                          viewModel onCleared
 *                          DefaultLifecycleObserver onDestroy
 *                          onDestroy
 *                  fragmentB:
 *                          viewModel onCleared
 *                          DefaultLifecycleObserver onDestroy
 *                          onDestroy
 *                  fragmentC:
 *                          viewModel onCleared
 *                          DefaultLifecycleObserver onDestroy
 *                          onDestroy
 *          onDestroy end
 * <pre/>
 *
 * <pre>
 * Presenter 关于 view 的生命周期回调由 DefaultLifecycleObserver 提供，上面的代码可以看出
 * 在 Activity 中先执行 presenter 的 onDestroy，再执行 ViewModel 的 onCleared；
 * 在 Fragment 中先执行 viewModel 的 onCleared，再执行 presenter 的 onDestroy；
 * 当把 Activity 换到 Fragment 时，由于生命周期顺序不一致，就会导致 presenter 出现问题。
 * 这里通过记录事件触发顺序对 presenter 生命周期进行修正，使得无论 Activity 还是 Fragment，presenter 生命周期顺序都保持如下：
 * onInitialize -> onAttachView -> afterViewCreate -> afterViewStart -> afterViewResume -> beforeViewPause -> beforeViewStop -> beforeViewDestroy -> onDetachView -> onCleared
 * <pre/>
 * @param <V> View
 */
@Deprecated
public class PresenterLifecycleOrderFixWrapper<V extends IView> extends PresenterLifecycleWrapper<V> {

    /**
     * 记录 onCleared() 方法是否被触发过.
     */
    private boolean isOnClearedCalled;

    /**
     * 记录 beforeViewDestroy() 方法是否被触发过
     */
    private boolean isOnViewDestroyCalled;

    public PresenterLifecycleOrderFixWrapper(@NonNull PresenterLifecycle<V> origin) {
        super(origin);
    }

    @Override
    public void onInitialize(@NonNull Application application) {
        /* onInitialize() 执行后需要对应执行 onCleared(). */
        isOnClearedCalled = false;
        super.onInitialize(application);
    }

    @Override
    public void afterViewCreate() {
        /* afterViewCreate() 执行后需要对应执行 beforeViewDestroy(). */
        isOnViewDestroyCalled = false;
        super.afterViewCreate();
    }

    @Override
    public void beforeViewDestroy() {
        /* 标记 beforeViewDestroy() 被触发了. */
        isOnViewDestroyCalled = true;

        /* 判断在这之前 onCleared() 方法有没有被触发过. */
        if (isOnClearedCalled) {
            /* 如果 onCleared() 方法被触发过，那么顺序需要调整. */
            super.beforeViewDestroy();

            /* 执行完 beforeViewDestroy() 后补发 onCleared(). */
            super.onCleared();
        } else {
            /* 如果 onCleared() 方法没有被触发过，那么顺序是正确的. */
            super.beforeViewDestroy();
        }
    }

    @Override
    public void onCleared() {
        /* 标记 onCleared() 被触发了. */
        isOnClearedCalled = true;

        /* 判断 beforeViewDestroy() 是否被调用过.  */
        //noinspection StatementWithEmptyBody
        if (isOnViewDestroyCalled) {
            /* 如果 beforeViewDestroy() 被调用过，说明顺序正常. */
            super.onCleared();
        } else {
            /* 如果 beforeViewDestroy() 没有被调用过，说明顺序不正常，此处不应该触发 super.onCleared()，而是等 beforeViewDestroy() 被调用时帮忙补发 super.onCleared(). */
        }
    }


}
