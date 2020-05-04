package just.mvp.common.viewmodel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import just.mvp.base.IPresenter;
import just.mvp.base.IView;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public class Presenters {

    private Presenters() {
        throw new RuntimeException("Presenters cannot be instantiated");
    }

    /**
     * 获取 Presenter 的具体类型
     */
    public static <P extends IPresenter> Class<P> getPresenterType(Class<?> aClass) {
        //noinspection unchecked
        return (Class<P>) ((ParameterizedType) (Objects.requireNonNull(aClass.getGenericSuperclass()))).getActualTypeArguments()[0];
    }

    /**
     * Presenter 创建器
     */
    @FunctionalInterface
    public interface Creator {
        IPresenter create();
    }

    /**
     * 绑定 FragmentActivity 和 Presenter
     *
     * @param view    实现了 {@link IView} 接口的 {@link FragmentActivity}
     * @param creator 如果 Presenter 没有被创建，那么通过 Creator 创建
     * @param <V>     {@link FragmentActivity} & {@link IView}
     */
    public static <V extends FragmentActivity & IView> void bind(V view, Creator creator) {
        final PresenterContainer presenterContainer = Presenters.of(view);
        bind(view, creator, presenterContainer);
    }

    /**
     * 绑定 Fragment 和 Presenter
     *
     * @param view    实现了 {@link IView} 接口的 {@link Fragment}
     * @param creator 如果 Presenter 没有被创建，那么通过 Creator 创建
     * @param <V>     {@link Fragment} & {@link IView}
     */
    public static <V extends Fragment & IView> void bind(V view, Creator creator) {
        final PresenterContainer presenterContainer = Presenters.of(view);
        bind(view, creator, presenterContainer);
    }

    /**
     * 获取绑定到 View 的 Presenter
     *
     * @param view   实现了 {@link IView} 接口的 {@link FragmentActivity}
     * @param pClass Presenter 的具体类型
     * @param <V>    {@link FragmentActivity} & {@link IView}
     * @param <P>    {@link IPresenter}
     * @return 绑定到 View 的 Presenter 实例
     */
    public static <V extends FragmentActivity & IView, P extends IPresenter> P get(V view, Class<P> pClass) {
        return pClass.cast(Presenters
                .of(view)
                .getPresenter());
    }

    /**
     * 获取绑定到 View 的 Presenter
     *
     * @param view   实现了 {@link IView} 接口的 {@link Fragment}
     * @param pClass Presenter 的具体类型
     * @param <V>    {@link Fragment} & {@link IView}
     * @param <P>    {@link IPresenter}
     * @return 绑定到 View 的 Presenter 实例
     */
    public static <V extends Fragment & IView, P extends IPresenter> P get(V view, Class<P> pClass) {
        return pClass.cast(Presenters
                .of(view)
                .getPresenter());
    }

    private static <V extends IView> void bind(V view, Creator creator, PresenterContainer presenterContainer) {
        IPresenter presenter = presenterContainer.getPresenter();
        /* 如果存在，不重复创建 */
        if (null == presenter) {
            presenter = creator.create();
            presenterContainer.setPresenter(presenter);
        }
        //noinspection unchecked
        presenter.attachView(view);
    }


    @NonNull
    private static PresenterContainer of(FragmentActivity fragmentActivity) {
        return ViewModelProviders
                .of(fragmentActivity)
                .get(PresenterContainer.class);
    }


    @NonNull
    private static PresenterContainer of(Fragment fragment) {
        return ViewModelProviders
                .of(fragment)
                .get(PresenterContainer.class);
    }

}
