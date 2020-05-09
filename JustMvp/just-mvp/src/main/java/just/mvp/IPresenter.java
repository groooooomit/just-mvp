package just.mvp;


import androidx.annotation.NonNull;

import just.mvp.base.IViewModel;


/**
 * Presenter 用来承载界面背后的业务逻辑，这里定义 Presenter 的通用方法。
 * <p>
 * IPresenter 继承自 {@link IViewModel}，使得它的生命周期和 ViewModel 相同
 */
public interface IPresenter<V extends IView> extends IViewModel {

    /**
     * Presenter 持有 View 的引用
     * <p>
     * View create 时发生
     *
     * @param view View
     */
    void attachView(@NonNull V view);

    /**
     * Presenter 解除对 View 的引用
     * <p>
     * View destroy 时发生
     */
    void detachView();

}
