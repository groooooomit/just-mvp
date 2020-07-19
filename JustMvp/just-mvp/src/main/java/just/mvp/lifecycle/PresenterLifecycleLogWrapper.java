package just.mvp.lifecycle;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import just.mvp.base.IView;

/**
 * 打印生命周期
 */
public class PresenterLifecycleLogWrapper<V extends IView> extends PresenterLifecycleWrapper<V> {

    private final boolean enable;

    @NonNull
    private final String tag;

    public PresenterLifecycleLogWrapper(boolean enable, @NonNull String tag, @NonNull PresenterLifecycle<V> origin) {
        super(origin);
        this.enable = enable;
        this.tag = tag;
    }

    private void log(@NonNull String name) {
        if (enable) {
            Log.d(tag, hashCode() + " : " + name);
        }
    }

    @Override
    public void onInitialize(@NonNull Application application) {
        super.onInitialize(application);
        log("onInitialize");
    }

    @Override
    public void onAttachView(@NonNull V view) {
        super.onAttachView(view);
        log("onAttachView");
    }

    @Override
    public void afterViewCreate() {
        super.afterViewCreate();
        log("afterViewCreate");
    }

    @Override
    public void afterViewStart() {
        super.afterViewStart();
        log("afterViewStart");
    }

    @Override
    public void afterViewResume() {
        super.afterViewResume();
        log("afterViewResume");
    }

    @Override
    public void beforeViewPause() {
        log("beforeViewPause");
        super.beforeViewPause();
    }

    @Override
    public void beforeViewStop() {
        log("beforeViewStop");
        super.beforeViewStop();
    }

    @Override
    public void beforeViewDestroy() {
        log("beforeViewDestroy");
        super.beforeViewDestroy();
    }

    @Override
    public void onDetachView(@NonNull V view) {
        log("onDetachView");
        super.onDetachView(view);
    }

    @Override
    public void onCleared() {
        log("onCleared");
        super.onCleared();
    }
}
