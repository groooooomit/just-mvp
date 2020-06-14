package just.mvp;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.Presenters;
import just.mvp.widget.SimpleFragment;

@SuppressWarnings("rawtypes")
public abstract class PresenterFragment<P extends IPresenter> extends SimpleFragment implements IView {

    /**
     * Presenter 引用
     */
    private P presenter;

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

        /* View 和 Presenter 绑定. */
        //noinspection unchecked
        presenter = (P) Presenters.bind(this, this::onCreatePresenter);
    }

    @NonNull
    protected P onCreatePresenter() {
        /* 默认创建 Presenter 的方式是反射创建，重写此方法自定义 Presenter 创建方法. */
        return Presenters.newInstance(Presenters.getPresenterType(this.getClass()));
    }

    /**
     * 获取 Presenter
     */
    @NonNull
    protected final P getPresenter() {
        if (null == presenter) {
            throw new RuntimeException("You should access presenter since onCreate() ");
        }
        return presenter;
    }

}
