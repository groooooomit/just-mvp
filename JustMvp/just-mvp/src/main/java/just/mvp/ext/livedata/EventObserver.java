package just.mvp.ext.livedata;

import androidx.annotation.NonNull;

@FunctionalInterface
public interface EventObserver<T> {

    void onChanged(@NonNull T t);
}

