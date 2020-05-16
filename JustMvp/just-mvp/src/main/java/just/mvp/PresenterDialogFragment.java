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
import just.mvp.widget.SimpleDialogFragment;

public abstract class PresenterDialogFragment<P extends IPresenter> extends SimpleDialogFragment implements IView {
    /**
     * 记录 Presenter 的类型
     */
    private final Class<P> presenterType = Presenters.getPresenterType(this.getClass());

    /**
     * view 和 presenter 绑定，具体行为是创建 Presenter 并将 Presenter 的生命周期监听器注册给 View；
     * <p>
     * 为了保证 Presenter 中关于 View 的生命周期相关方法执行时 view 的界面控件已经完成初始化，绑定操作被设定在 onActivityCreated 中执行；
     * <p>
     * 在此之前的 Fragment 的生命周期方法不能够直接访问 Presenter，应该避免这种行为，因为 Presenter 尚未初始化，View 应该保持简洁，
     * Presenter 具有了感知 View 生命周期的能力后，View 生命周期所触发的业务逻辑都应该搬离到 Presenter 中对应 View 的生命周期方法中执行。
     */
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
    private final BundleData bundleData = new BundleData(PresenterDialogFragment.this::getArguments);

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
