package just.mvp.llifecycle;

/**
 * 仿照 {@link androidx.lifecycle.DefaultLifecycleObserver} 定义 View 的生命周期
 */
public interface IViewLifeCycleListener {

    default void afterViewCreate() {
    }

    default void afterViewStart() {
    }

    default void afterViewResume() {
    }

    default void beforeViewPause() {
    }

    /**
     * 在 Activity 的主题是 Dialog 或 Translucent 时，启动的 Activity 的生命周期不会触发 onStop()
     */
    default void beforeViewStop() {
    }

    default void beforeViewDestroy() {
    }

}
