package just.mvp.base;

import androidx.annotation.CallSuper;

/**
 * 使用 {@link androidx.lifecycle.ViewModel} 组件存储 Presenter
 *
 * @param <V> View
 */
public class ViewModelPresenter<V extends IView> implements IPresenter<V> {

    private V view;

    protected final V getView() {
        return view;
    }

    @CallSuper
    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @CallSuper
    @Override
    public void onCleared() {
        this.view = null;
    }

}
