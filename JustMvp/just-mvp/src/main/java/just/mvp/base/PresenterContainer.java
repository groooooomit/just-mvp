package just.mvp.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 以组合的方式使用 ViewModel，而非继承
 * <p>
 * 继承自 {@link AndroidViewModel} 以获得 Application 对象.
 */
@SuppressWarnings("rawtypes")
public final class PresenterContainer extends AndroidViewModel {

    /**
     * PresenterContainer 持有的 Presenter 对象集合
     */
    @NonNull
    private final Map<String, IPresenter> presenterMap = new LinkedHashMap<>(1);

    public PresenterContainer(@NonNull Application application) {
        super(application);
    }

    /**
     * 检查 Presenter 是否存在，如果不存在，那么通过外部传入的 {@link Creator} 创建，并为 Presenter 注入 Application 对象
     */
    @NonNull
    public final <P extends IPresenter> P preparePresenter(@NonNull String key, @NonNull Creator<P> creator) {
        //noinspection unchecked
        P presenter = (P) presenterMap.get(key);
        if (null == presenter) {
            presenter = creator.create();
            presenter.initialize(getApplication());
            presenterMap.put(key, presenter);
        }
        return presenter;
    }

    /**
     * 获取 Presenter
     */
    @NonNull
    public final <P extends IPresenter> P requirePresenter(@NonNull String key) {
        //noinspection unchecked
        final P presenter = (P) presenterMap.get(key);
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
        for (IPresenter presenter : presenterMap.values()) {
            presenter.cleared();
        }
        presenterMap.clear();
    }

    /**
     * Presenter 创建器
     */
    @SuppressWarnings("rawtypes")
    @FunctionalInterface
    public interface Creator<P extends IPresenter> {
        @NonNull
        P create();
    }


}
