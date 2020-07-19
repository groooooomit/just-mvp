package just.mvp;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.IViewModelContainer;
import just.mvp.base.Presenters;

/**
 * 提供一个 Mvp 的模版，如果无法继承自 PresenterActivity，那么也可以直接使用 {@link Presenters} 工具
 * <p>
 * {@link Presenters#bind(IView, Class, IViewModelContainer.Creator)} 用来绑定 View 和 Presenter
 * <p>
 * 当前创建 Presenter 的方式为 Class#newInstance() 的方式
 * <p>
 * [注意]：不能在 Activity onDestroy() 方法中调用 getPresenter()，因为 ViewModel 在 Activity/Fragment destroy 之前就被回收了
 */
@SuppressWarnings("rawtypes")
public abstract class PresenterActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    /**
     * presenter Class 类型
     */
    private final Class<P> presenterClass = Presenters.getPresenterType(this.getClass());

    /**
     * Presenter 引用
     */
    private P presenter;

    /**
     * view 和 presenter 绑定，具体行为是创建 Presenter 并将 Presenter 的生命周期监听器注册给 View
     */
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* View 和 Presenter 绑定. */
        //noinspection unchecked
        presenter = (P) Presenters.bind(this, presenterClass, this::onCreatePresenter);
    }

    @NonNull
    protected P onCreatePresenter() {
        /* 默认创建 Presenter 的方式是反射创建，重写此方法自定义 Presenter 创建方法. */
        return Presenters.newInstance(presenterClass);
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
