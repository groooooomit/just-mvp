package just.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import just.mvp.widget.SimpleDialogFragment;
import just.mvp.base.BundleData;
import just.mvp.base.Presenters;

public abstract class PresenterDialogFragment<P extends IPresenter> extends SimpleDialogFragment implements IView {
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
        //noinspection unchecked
        return (P) Presenters.get(this);
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

    @Override
    public void toast(@NonNull String msg) {
        final Context context = getContext();
        if (null != context) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toastLong(@NonNull String msg) {
        final Context context = getContext();
        if (null != context) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void snack(@NonNull String msg) {
        final View view = getView();
        if (null != view) {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void snackLong(@NonNull String msg) {
        final View view = getView();
        if (null != view) {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void snackIndefinite(@NonNull String msg) {
        final View view = getView();
        if (view != null) {
            final Snackbar make = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
            make.setAction("知道了", v -> make.dismiss()).show();
        }
    }

}
