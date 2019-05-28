package just.mvp.common;

import androidx.lifecycle.ViewModel;
import just.mvp.base.IPresenter;

/**
 * 以组合的方式使用 ViewModel，而非继承
 */
public class PresenterContainer extends ViewModel {
	private IPresenter presenter;

	public IPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(IPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	protected void onCleared() {
		if (null != presenter) {
			presenter.onCleared();
		}
	}

}
