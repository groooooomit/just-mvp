package just.mvp.common;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import just.mvp.base.IPresenter;
import just.mvp.base.IView;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public interface Presenters {

    @FunctionalInterface
    interface Creator {
        IPresenter create();
    }

    static <V extends FragmentActivity & IView> void bind(V view, Creator creator) {
        final PresenterContainer presenterContainer = Presenters.of(view);
        bind(view, creator, presenterContainer);
    }

    static <V extends Fragment & IView> void bind(V view, Creator creator) {
        final PresenterContainer presenterContainer = Presenters.of(view);
        bind(view, creator, presenterContainer);
    }

    static <V extends IView> void bind(V view, Creator creator, PresenterContainer presenterContainer) {
        IPresenter presenter = presenterContainer.getPresenter();
        /* 如果存在，不重复创建 */
        if (null == presenter) {
            presenter = creator.create();
            presenterContainer.setPresenter(presenter);
        }
        //noinspection unchecked
        presenter.attachView(view);
    }

    static <V extends Fragment & IView, P extends IPresenter> P get(V view, Class<P> pClass) {
        return pClass.cast(Presenters
                .of(view)
                .getPresenter());
    }

    static <V extends FragmentActivity & IView, P extends IPresenter> P get(V view, Class<P> pClass) {
        return pClass.cast(Presenters
                .of(view)
                .getPresenter());
    }

    @NonNull
    static PresenterContainer of(FragmentActivity fragmentActivity) {
        return ViewModelProviders
                .of(fragmentActivity)
                .get(PresenterContainer.class);
    }


    @NonNull
    static PresenterContainer of(Fragment fragment) {
        return ViewModelProviders
                .of(fragment)
                .get(PresenterContainer.class);
    }

    static <P extends IPresenter> Class<P> getPresenterType(Class<?> aClass) {
        //noinspection unchecked
        return (Class<P>) ((ParameterizedType) (Objects.requireNonNull(aClass.getGenericSuperclass()))).getActualTypeArguments()[0];
    }


}
