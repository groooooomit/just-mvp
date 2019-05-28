package just.mvp.base;


import just.mvp.common.IViewModel;

/**
 * Presenter 中只定义 View 需要用到的方法
 */
public interface IPresenter<V extends IView> extends IViewModel {

	void attachView(V view);

}
