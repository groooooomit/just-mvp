package just.mvp.base;

import androidx.annotation.NonNull;

import just.mvp.IPresenter;
import just.mvp.IView;

public class AutoDetachViewDelegate<V extends IView, P extends IPresenter<V> & PresenterLifecycle<V>> implements PresenterLifecycle<V> {

    @NonNull
    private final P presenter;

    public AutoDetachViewDelegate(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onInitialize() {
        presenter.onInitialize();
    }

    @Override
    public void onAttachView(@NonNull V view) {
        presenter.onAttachView(view);
    }

    @Override
    public void afterViewCreate() {
        presenter.afterViewCreate();
    }

    @Override
    public void afterViewStart() {
        presenter.afterViewStart();
    }

    @Override
    public void afterViewResume() {
        presenter.afterViewResume();
    }

    @Override
    public void beforeViewPause() {
        presenter.beforeViewPause();
    }

    @Override
    public void beforeViewStop() {
        presenter.beforeViewStop();
    }

    @Override
    public void beforeViewDestroy() {
        /* 当 View destroy 时，presenter 主动 detach view 以释放对 view 的引用. */
        presenter.beforeViewDestroy();
        presenter.detachView();
    }

    @Override
    public void onDetachView(@NonNull V view) {
        presenter.onDetachView(view);
    }

    @Override
    public void onCleared() {
        presenter.onCleared();
    }
}
