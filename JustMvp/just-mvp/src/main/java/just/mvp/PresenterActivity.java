package just.mvp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.base.IntentData;
import just.mvp.base.PresenterContainer;
import just.mvp.base.Presenters;
import just.mvp.base.ViewData;

/**
 * 提供一个 Mvp 的模版，如果无法继承自 PresenterActivity，那么也可以直接使用 {@link Presenters} 工具
 * <p>
 * {@link Presenters#bind(IView, PresenterContainer.Creator)} 用来绑定 View 和 Presenter
 * <p>
 * {@link Presenters#get(IView)} 用来获取绑定了 View 的 Presenter
 * <p>
 * 当前创建 Presenter 的方式为 Class#newInstance() 的方式
 * <p>
 * [注意]：不能在 Activity onDestroy() 方法中调用 getPresenter()，因为 ViewModel 在 Activity/Fragment destroy 之前就被回收了
 */
public abstract class PresenterActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    /**
     * 记录 Presenter 的类型
     */
    private final Class<P> presenterType = Presenters.getPresenterType(this.getClass());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        } catch (InstantiationException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterType), e);
        }
    }

    /**
     * 获取 Presenter
     */
    @NonNull
    public final P getPresenter() {
        return Presenters.get(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // implements {@link IView}
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Nullable
    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @NonNull
    private final IntentData intentData = new IntentData(PresenterActivity.this::getIntent);

    @NonNull
    @Override
    public ViewData getData() {
        return intentData;
    }

    @Override
    public boolean isActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isFinishing() && !isDestroyed();
        } else {
            return !isFinishing();
        }
    }

}
