package just.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Presenter 辅助工具
 */
public final class Presenters {

    /**
     * 工具类不可被实例化
     */
    private Presenters() {
        throw new RuntimeException("Presenters cannot be instantiated");
    }

    /**
     * 获取 Presenter 的具体类型
     */
    @NonNull
    public static <P extends IPresenter<?>> Class<P> getPresenterType(@NonNull Class<? extends IView> viewClass) {
        //noinspection unchecked
        return (Class<P>) ((ParameterizedType) (Objects.requireNonNull(viewClass.getGenericSuperclass()))).getActualTypeArguments()[0];
    }

    /**
     * 反射创建 Presenter 的实例
     */
    @NonNull
    public static <P extends IPresenter<?>> P newInstance(@NonNull Class<P> presenterClass) {
        try {
            return presenterClass.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterClass), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterClass), e);
        }
    }

    @NonNull
    public static <V extends IView, P extends IPresenter<V>> P bind(@NonNull V view, @NonNull Class<P> presenterClass) {
        return bind(view, presenterClass, () -> Presenters.newInstance(presenterClass));
    }

    /**
     * ViewModel key 前缀
     */
    private static final String KEY_PREFIX = "just-mvp-IViewModelContainer:";

    /**
     * 绑定 View 和 Presenter，如果 Presenter 已存在，那么直接返回，否则通过 Creator 进行创建
     *
     * @param view           View
     * @param presenterClass 在 Presenter 的 Class 类型
     * @param creator        创建器
     */
    @NonNull
    public static <V extends IView, P extends IPresenter<V>> P bind(@NonNull V view, @NonNull Class<P> presenterClass, @NonNull IViewModelContainer.Creator<P> creator) {
        final String key = KEY_PREFIX + Objects.requireNonNull(presenterClass.getCanonicalName());
        final P presenter = new ViewModelProvider(view).get(key, IViewModelContainer.class).prepare(creator);
        presenter.attachView(view);
        return presenter;
    }

    /**
     * 获取绑定到 View 的 Presenter
     *
     * @param view View
     * @return 绑定到 View 的 Presenter 实例
     */
    @NonNull
    public static <V extends IView, P extends IPresenter<V>> P get(@NonNull V view, @NonNull Class<P> presenterClass) {
        final String key = KEY_PREFIX + Objects.requireNonNull(presenterClass.getCanonicalName());
        return new ViewModelProvider(view).get(key, IViewModelContainer.class).require();
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取 View 的 context
     */
    @NonNull
    public static Context getHostContextOf(@NonNull IView view) {
        if (view instanceof FragmentActivity) {
            return (Context) view;
        } else if (view instanceof Fragment) {
            return ((Fragment) view).requireContext();
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }

    /**
     * 获取 View 持有的界面参数，Activity 的数据来源于 getIntent()，Fragment 的数据来源于 getArguments()
     */
    @Nullable
    public static Bundle getArgsOf(@NonNull IView view) {
        if (view instanceof FragmentActivity) {
            return ((Activity) view).getIntent().getExtras();
        } else if (view instanceof Fragment) {
            return ((Fragment) view).getArguments();
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }

    /**
     * 获取正确的 LifecycleOwner 对象
     */
    @NonNull
    public static LifecycleOwner getFixedLifecycleOwnerOf(IView view) {
        if (view instanceof FragmentActivity) {
            return (FragmentActivity) view;
        } else if (view instanceof Fragment) {
            /* Google 填坑：Fragment 要通过 getViewLifecycleOwner() 来获取正确的 LifecycleOwner. https://medium.com/@cs.ibrahimyilmaz/viewlifecycleowner-vs-this-a8259800367b */
            return ((Fragment) view).getViewLifecycleOwner();
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }
}
