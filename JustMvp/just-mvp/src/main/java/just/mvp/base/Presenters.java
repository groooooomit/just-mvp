package just.mvp.base;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Presenter 辅助工具
 * <p>
 * 工具类不可被继承
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
    public static <P extends IPresenter> Class<P> getPresenterType(@NonNull Class<?> aClass) {
        //noinspection unchecked
        return (Class<P>) ((ParameterizedType) (Objects.requireNonNull(aClass.getGenericSuperclass()))).getActualTypeArguments()[0];
    }

    /**
     * 绑定 View 和 Presenter
     *
     * @param view    实现了 {@link IView}
     * @param creator 如果 Presenter 没有被创建，那么通过 Creator 创建
     */
    public static <V extends IView, P extends IPresenter> void bind(@NonNull V view, @NonNull PresenterContainer.Creator<P> creator) {
        //noinspection unchecked
        of(view).get(PresenterContainer.class)
                .preparePresenter(creator)
                .attachView(view);
    }

    /**
     * 获取绑定到 View 的 Presenter
     *
     * @param view 实现了 {@link IView} 接口
     * @return 绑定到 View 的 Presenter 实例
     */
    @NonNull
    public static <V extends IView, P extends IPresenter> P get(@NonNull V view) {
        //noinspection unchecked
        return (P) of(view)
                .get(PresenterContainer.class)
                .requirePresenter();
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
        final Activity activity = view.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
        }
        return activity;
    }

    /**
     * 创建 ViewModelProvider 对象
     */
    @NonNull
    private static <V extends IView> ViewModelProvider of(@NonNull V view) {
        final Application application = checkApplication(checkActivity(view));
        final ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        return new ViewModelProvider(view, factory);
    }


}
