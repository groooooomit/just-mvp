package just.mvp.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public interface ViewLifecycleObserver {

    default void onCreate(@NonNull LifecycleOwner owner) {
    }

    default void onStart(@NonNull LifecycleOwner owner) {
    }

    default void onResume(@NonNull LifecycleOwner owner) {
    }

    default void onPause(@NonNull LifecycleOwner owner) {
    }

    default void onStop(@NonNull LifecycleOwner owner) {
    }

    default void onDestroy(@NonNull LifecycleOwner owner) {
    }
}
