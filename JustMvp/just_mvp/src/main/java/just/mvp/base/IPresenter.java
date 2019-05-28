package just.mvp.base;


import just.mvp.common.viewmodel.IViewModel;

/**
 * Presenter 中只定义 View 需要用到的方法
 */
public interface IPresenter<V extends IView> extends IViewModel {

    /**
     * Presenter 持有 View 的引用
     *
     * @param view View
     */
    void attachView(V view);

}
