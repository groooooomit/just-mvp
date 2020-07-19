package just.mvp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.Presenters;
import just.mvp.widget.SimpleDialogFragment;

@SuppressWarnings("rawtypes")
public abstract class PresenterDialogFragment<P extends IPresenter> extends SimpleDialogFragment implements IView {

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /* View 和 Presenter 绑定. */
        //noinspection unchecked
        presenter = (P) Presenters.bind(this, presenterClass, this::onCreatePresenter);
    }

    /**
     * 默认创建 Presenter 的方式是反射创建，重写此方法自定义 Presenter 创建方法.
     */
    @NonNull
    protected P onCreatePresenter() {
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