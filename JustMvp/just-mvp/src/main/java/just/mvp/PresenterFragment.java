package just.mvp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import just.mvp.base.IPresenter;
import just.mvp.base.IView;
import just.mvp.common.viewmodel.Presenters;

public class PresenterFragment<P extends IPresenter> extends Fragment implements IView {
    /**
     * 记录 Presenter 的类型
     */
    private final Class<P> presenterType = Presenters.getPresenterType(this.getClass());

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* view 和 presenter 进行绑定. */
        Presenters
                .bind(this, () -> {
                    try {
                        return presenterType.newInstance();
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(String.format("Cannot create an instance of %s", presenterType), e);
                    } catch (java.lang.InstantiationException e) {
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

    @Override
    public boolean isActive() {
        return isAdded() && !isStateSaved();
    }

    /**
     * showMessage 默认实现弹出一个 toast.
     */
    @Override
    public void showMessage(@NonNull String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
