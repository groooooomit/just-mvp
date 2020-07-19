package just.mvp.lifecycle;

import android.app.Application;

import androidx.annotation.NonNull;

import just.mvp.base.IView;

/**
 * Presenter 的生命周期
 *
 * @param <V>
 */
public interface PresenterLifecycle<V extends IView> {

    /**
     * Presenter 创建并持有 Application 对象后回调
     */
    default void onInitialize(@NonNull Application application) {
    }

    /**
     * Presenter 持有 View 的引用后回调
     */
    default void onAttachView(@NonNull V view) {
    }

    /**
     * Activity | Fragment onCreate 之后回调
     */
    default void afterViewCreate() {
    }

    /**
     * Activity | Fragment onStart 之后回调
     */
    default void afterViewStart() {
    }

    /**
     * Activity | Fragment onResume 之后回调
     */
    default void afterViewResume() {
    }

    /**
     * Activity | Fragment onPause 之前回调
     */
    default void beforeViewPause() {
    }

    /**
     * Activity | Fragment onStop 之前回调
     * <p>
     * 在 Activity 的主题是 Dialog 或 Translucent 时，启动的 Activity 的生命周期不会触发 onStop()
     */
    default void beforeViewStop() {
    }

    /**
     * Activity | Fragment onDestroy 之前回调
     */
    default void beforeViewDestroy() {
    }

    /**
     * Presenter 在即将解除 View 的引用前回调
     */
    default void onDetachView(@NonNull V view) {
    }

    /**
     * Presenter 跟随 ViewModel 一同被回收前回调
     */
    default void onCleared() {
    }

}
