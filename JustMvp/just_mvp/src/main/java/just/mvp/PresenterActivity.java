package just.mvp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.common.viewmodel.Presenters;

/**
 * 提供一个 Mvp 的模版，如果无法继承自 PresenterActivity，那么也可以直接使用 {@link Presenters} 工具
 * <p>
 * Presenters#bind 用来绑定 View 和 Presenter
 * <p>
 * Presenters#get 用来获取绑定了 View 的 Presenter
 * <p>
 * 当前创建 Presenter 的方式为 Class#newInstance() 的方式
 * <p>
 * [注意]：应该避免 Activity onDestroy() 方法中调用 Presenters，因为 ViewModel 会有异常
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
        Presenters
                .bind(this, () -> {
                    try {
                        return presenterType.newInstance();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(String.format("Cannot create an instance of %s", presenterType), e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(String.format("Cannot create an instance of %s", presenterType), e);
                    }
                });
    }

    /**
     * 获取 Presenter
     */
    public P getPresenter() {
        return Presenters.get(this, presenterType);
    }

    @Nullable
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean isActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !isFinishing() && !isDestroyed();
        } else {
            return !isFinishing();
        }
    }

    @Override
    public void showMessage(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
