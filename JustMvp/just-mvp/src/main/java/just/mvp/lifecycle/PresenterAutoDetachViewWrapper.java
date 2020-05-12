package just.mvp.lifecycle;

import androidx.annotation.NonNull;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;

/**
 * 当执行完 {@link just.mvp.base.AbstractPresenter#beforeViewDestroy()} 时触发 {@link just.mvp.base.AbstractPresenter#detachView()} 方法以主动释放对 View 的引用，避免造成 View 的内存泄漏
 */
public class PresenterAutoDetachViewWrapper<V extends IView> extends PresenterLifecycleWrapper<V> {

    @NonNull
    private final IPresenter<V> presenter;

    public PresenterAutoDetachViewWrapper(@NonNull IPresenter<V> presenter, @NonNull PresenterLifecycle<V> origin) {
        super(origin);
        this.presenter = presenter;
    }

    @Override
    public void beforeViewDestroy() {
        super.beforeViewDestroy();
        presenter.detachView();
    }

}
