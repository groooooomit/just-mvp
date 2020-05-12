package just.mvp;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import just.mvp.base.BundleData;
import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.Presenters;
import just.mvp.base.ViewData;
import just.mvp.widget.SimpleFragment;

public abstract class PresenterFragment<P extends IPresenter> extends SimpleFragment implements IView {
    /**
     * 记录 Presenter 的类型
     */
    private final Class<P> presenterType = Presenters.getPresenterType(this.getClass());

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* view 和 presenter 进行绑定. */
        Presenters.bind(this, this::onCreatePresenter);
    }

    /**
     * 默认实现是进行反射调用
     */
    @NonNull
    protected P onCreatePresenter() {
        try {
            return presenterType.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterType), e);
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterType), e);
        }
    }

    /**
     * 获取 Presenter
     */
    @NonNull
    protected final P getPresenter() {
        return Presenters.get(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // implements {@link IView}
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private final BundleData bundleData = new BundleData(PresenterFragment.this::getArguments);

    @NonNull
    @Override
    public ViewData getData() {
        return bundleData;
    }

    @Override
    public boolean isActive() {
        return isAdded() && !isStateSaved();
    }

}
