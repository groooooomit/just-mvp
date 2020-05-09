package just.mvp.lifecycle;

import androidx.annotation.NonNull;

import just.mvp.base.IPresenter;


/**
 * 将生命周期传递给 presenter，同时当 beforeViewDestroy 触发时，执行 presenter.detachView()
 */
public final class AutoDetachViewWrapper<P extends IPresenter & IViewLifeCycleListener> implements IViewLifeCycleListener {

    @NonNull
    private final P presenter;

    public AutoDetachViewWrapper(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @Override
    public final void afterViewCreate() {
        this.presenter.afterViewCreate();
    }

    @Override
    public final void afterViewStart() {
        this.presenter.afterViewStart();
    }

    @Override
    public final void afterViewResume() {
        this.presenter.afterViewResume();
    }

    @Override
    public final void beforeViewPause() {
        this.presenter.beforeViewPause();
    }

    @Override
    public final void beforeViewStop() {
        this.presenter.beforeViewStop();
    }

    @Override
    public final void beforeViewDestroy() {
        this.presenter.beforeViewDestroy();

        /* 关键在于此，通过 wrapper 的方式强制在 view destroy 前执行 presenter 的 detachView. */
        this.presenter.detachView();
    }

}
