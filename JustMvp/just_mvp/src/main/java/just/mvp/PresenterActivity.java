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
import just.mvp.common.Presenters;

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
