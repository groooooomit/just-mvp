package just.mvp.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
     * 默认的 Presenter key
     */
    public static final String KEY_DEFAULT_PRESENTER = "default-presenter";

    /**
     * 获取 Presenter 的具体类型
     */
    @SuppressWarnings("rawtypes")
    @NonNull
    public static <P extends IPresenter> Class<P> getPresenterType(@NonNull Class<?> viewClass) {
        //noinspection unchecked
        return (Class<P>) ((ParameterizedType) (Objects.requireNonNull(viewClass.getGenericSuperclass()))).getActualTypeArguments()[0];
    }


    /**
     * 反射创建 Presenter 的实例
     */
    @SuppressWarnings("rawtypes")
    @NonNull
    public static <P extends IPresenter> P newInstance(@NonNull Class<P> presenterClass) {
        try {
            return presenterClass.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterClass), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(String.format("Cannot create an instance of %s", presenterClass), e);
        }
    }

    /**
     * 绑定 View 和 Presenter，如果 Presenter 已创建，那么直接返回，否则通过 Creator 进行创建
     *
     * @param view    View
     * @param creator 创建器
     */
    @NonNull
    public static <V extends IView, P extends IPresenter<V>> P bind(@NonNull V view, @NonNull PresenterContainer.Creator<P> creator) {
        return bind(view, Presenters.KEY_DEFAULT_PRESENTER, creator);
    }

    @NonNull
    public static <V extends IView, P extends IPresenter<V>> P bind(@NonNull V view, @NonNull String key, @NonNull PresenterContainer.Creator<P> creator) {
        final P presenter = of(view).get(PresenterContainer.class).preparePresenter(key, creator);
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
    public static <V extends IView, P extends IPresenter<V>> P get(@NonNull V view, @NonNull String key) {
        return of(view).get(PresenterContainer.class).requirePresenter(key);
    }

    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private static Application checkApplication(@NonNull Activity activity) {
        final Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity is not yet attached to Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }

    @NonNull
    private static Activity checkActivity(@NonNull IView view) {
        final Activity activity = view.getHostActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached view");
        }
        return activity;
    }

    /**
     * 创建 ViewModelProvider 对象
     */
    @NonNull
    private static ViewModelProvider of(@NonNull IView view) {
        final Application application = checkApplication(checkActivity(view));
        final ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        return new ViewModelProvider(view, factory);
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取 View 的宿主 context
     */
    @Nullable
    public static Context getHostContextOf(@NonNull IView view) {
        if (view instanceof Activity) {
            return (Context) view;
        } else if (view instanceof Fragment) {
            return ((Fragment) view).getContext();
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }

    /**
     * 获取 View 的宿主 activity
     */
    @Nullable
    public static FragmentActivity getHostActivityOf(@NonNull IView view) {
        if (view instanceof FragmentActivity) {
            return (FragmentActivity) view;
        } else if (view instanceof Fragment) {
            return ((Fragment) view).getActivity();
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }

    /**
     * 获取 View 持有的界面参数
     */
    @Nullable
    public static Bundle getArgsOf(@NonNull IView view) {
        if (view instanceof Activity) {
            return ((Activity) view).getIntent().getExtras();
        } else if (view instanceof Fragment) {
            return ((Fragment) view).getArguments();
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }

    /**
     * 判断 View 是否 active
     */
    public static boolean isActiveOf(@NonNull IView view) {
        if (view instanceof Activity) {
            return isActivityActive((Activity) view);
        } else if (view instanceof Fragment) {
            return isFragmentActive((Fragment) view);
        } else {
            throw new UnsupportedOperationException("Type of " + view.getClass().getName() + " is unsupported.");
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 判断 Activity 是否 active
     */
    private static boolean isActivityActive(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !activity.isFinishing() && !activity.isDestroyed();
        } else {
            return !activity.isFinishing();
        }
    }

    /**
     * 判断 Fragment 是否 active
     */
    private static boolean isFragmentActive(@NonNull Fragment fragment) {
        return fragment.isAdded() && !fragment.isStateSaved();
    }


}
