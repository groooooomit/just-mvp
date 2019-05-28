package just.mvp.base;


import just.mvp.common.ProxyViewFactory;

/**
 * 通过动态代理持有真实 View 的弱引用，Presenter 持有代理 View 的引用
 */
public class ProxyViewPresenter<V extends IView> extends LifeCyclePresenter<V> {

	@Override
	public void attachView(V view) {

		/* 使用代理的 View */
		super.attachView(ProxyViewFactory.create(view));
	}

}
