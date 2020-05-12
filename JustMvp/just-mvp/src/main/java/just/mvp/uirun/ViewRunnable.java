package just.mvp.uirun;

import androidx.annotation.NonNull;

import just.mvp.base.IView;

@FunctionalInterface
public interface ViewRunnable<V extends IView> {

    void run(@NonNull V view);

}
