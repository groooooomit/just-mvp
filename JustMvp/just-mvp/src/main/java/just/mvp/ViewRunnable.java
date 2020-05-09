package just.mvp;

import androidx.annotation.NonNull;

@FunctionalInterface
public interface ViewRunnable<V extends IView> {
    void run(@NonNull V view);
}
