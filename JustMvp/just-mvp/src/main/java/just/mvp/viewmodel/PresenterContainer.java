package just.mvp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import just.mvp.base.IPresenter;


/**
 * 以组合的方式使用 ViewModel，而非继承
 * <p>
 * 继承自 {@link AndroidViewModel} 以获得 Application 对象.
 */
public final class PresenterContainer extends AndroidViewModel {

    /**
     * PresenterContainer 持有的 Presenter 对象
     */
    @Nullable
    private IPresenter presenter;

    public PresenterContainer(@NonNull Application application) {
        super(application);
    }

    /**
     * 检查 Presenter 是否存在，如果不存在，那么通过外部传入的 {@link Creator} 创建，并为 Presenter 注入 Application 对象
     */
    @NonNull
    public final IPresenter preparePresenter(@NonNull Creator creator) {
        if (null == presenter) {
            presenter = creator.create();
            presenter.initialize(getApplication());
        }
        return presenter;
    }

    /**
     * 获取 Presenter
     */
    @NonNull
    public final IPresenter requirePresenter() {
        if (null == presenter) {
            throw new RuntimeException("Presenter doesn't prepare or this container already cleared!");
        }
        return presenter;
    }

    /**
     * 当 PresenterContainer 回收时，同时触发 Presenter 的对应回调，并清除对 Presenter 持有的引用
     */
    @Override
    protected final void onCleared() {
        if (null != presenter) {
            presenter.onCleared();
            presenter = null;
        }
    }

    /**
     * Presenter 创建器
     */
    @FunctionalInterface
    public interface Creator {
        @NonNull
        IPresenter create();
    }


}
