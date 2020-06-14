package just.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.PresenterContainer;
import just.mvp.base.Presenters;

/**
 * 提供一个 Mvp 的模版，如果无法继承自 PresenterActivity，那么也可以直接使用 {@link Presenters} 工具
 * <p>
 * {@link Presenters#tryBind(IView, PresenterContainer.Creator)} 用来绑定 View 和 Presenter
 * <p>
 * {@link Presenters#get(IView)} 用来获取绑定了 View 的 Presenter
 * <p>
 * 当前创建 Presenter 的方式为 Class#newInstance() 的方式
 * <p>
 * [注意]：不能在 Activity onDestroy() 方法中调用 getPresenter()，因为 ViewModel 在 Activity/Fragment destroy 之前就被回收了
 */
@SuppressWarnings("rawtypes")
public abstract class PresenterActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    /**
     * Presenter 引用
     */
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
