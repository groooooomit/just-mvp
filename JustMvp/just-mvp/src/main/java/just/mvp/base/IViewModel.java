package just.mvp.base;

import android.app.Application;

import androidx.annotation.NonNull;

/**
 * 通过组合的方式使用 {@link androidx.lifecycle.ViewModel}
 * <p>
 * 因为 {@link androidx.lifecycle.ViewModel} 是一个抽象类，如果直接使用那么必须继承自它，这给 Presenter 带来了不灵活。
 * <p>
 * 这里通过创建一个 {@link PresenterContainer} 的 ViewModel，来存储实现了 {@link IViewModel} 的任何对象，即将抽象类继承变更为接口组合
 */
public interface IViewModel {

    /**
     * Presenter 创建时触发.
     */
    void initialize(@NonNull Application application);

    /**
     * 传递  androidx.lifecycle.ViewModel#onCleared() 的回调
     * <p>
     * [小米 note] 测试发现：当滑动杀死 app 的时候，onCleared 不会被触发；当点击清理按钮杀死 app 的时候，会回调 onCleared
     */
    void cleared();
}
