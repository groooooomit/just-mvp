package just.mvp.base;

import androidx.annotation.NonNull;

import just.mvp.IView;

/**
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
public final class LifecycleOrderFixDelegate<V extends IView> implements PresenterLifecycle<V> {

    /**
     * 记录 onCleared() 方法是否被触发过.
     */
    private boolean isOnClearedCalled;

    /**
     * 记录 beforeViewDestroy() 方法是否被触发过
     */
    private boolean isBeforeViewDestroyCalled;

    @NonNull
    private final PresenterLifecycle<V> origin;

    public LifecycleOrderFixDelegate(@NonNull PresenterLifecycle<V> origin) {
        this.origin = origin;
    }

    @Override
    public void onInitialize() {
        /* onInitialize() 执行后需要对应执行 onCleared(). */
        isOnClearedCalled = false;
        origin.onInitialize();
    }

    @Override
    public void onAttachView(@NonNull V view) {
        origin.onAttachView(view);
    }

    @Override
    public void afterViewCreate() {
        /* afterViewCreate() 执行后需要对应执行 beforeViewDestroy(). */
        isBeforeViewDestroyCalled = false;
        origin.afterViewCreate();
    }

    @Override
    public void afterViewStart() {
        origin.afterViewStart();
    }

    @Override
    public void afterViewResume() {
        origin.afterViewResume();
    }

    @Override
    public void beforeViewPause() {
        origin.beforeViewPause();
    }

    @Override
    public void beforeViewStop() {
        origin.beforeViewStop();
    }

    @Override
    public void beforeViewDestroy() {
        /* 标记 beforeViewDestroy() 被触发了. */
        isBeforeViewDestroyCalled = true;

        /* 判断在这之前 onCleared() 方法有没有被触发过. */
        if (isOnClearedCalled) {
            /* 如果 onCleared() 方法被触发过，那么顺序需要调整. */
            origin.beforeViewDestroy();

            /* 执行完 beforeViewDestroy() 后补发 onCleared(). */
            origin.onCleared();
        } else {
            /* 如果 onCleared() 方法没有被触发过，那么顺序是正确的. */
            origin.beforeViewDestroy();
        }
    }

    @Override
    public void onDetachView(@NonNull V view) {
        origin.onDetachView(view);
    }

    @Override
    public void onCleared() {
        /* 标记 onCleared() 被触发了. */
        isOnClearedCalled = true;

        /* 判断 beforeViewDestroy() 是否被调用过.  */
        //noinspection StatementWithEmptyBody
        if (isBeforeViewDestroyCalled) {
            /* 如果 beforeViewDestroy() 被调用过，说明顺序正常. */
            origin.onCleared();
        } else {
            /* 如果 beforeViewDestroy() 没有被调用过，说明顺序不正常，此处不应该触发 origin.onCleared()，而是等 beforeViewDestroy() 被调用时帮忙补发 origin.onCleared(). */
        }

    }


}
