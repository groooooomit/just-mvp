package just.mvp.ext.livedata;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * 防止数据更新被重复触发
 *
 * @param <T>
 */
public abstract class EventLiveData<T> extends LiveData<Event<T>> {

    public void observe(@NonNull LifecycleOwner owner, @NonNull EventObserver<T> observer) {
        super.observe(owner, new OnceObserver<>(observer));
    }

    public void observe(@NonNull Fragment fragment, @NonNull EventObserver<T> observer) {
        super.observe(fragment.getViewLifecycleOwner(), new OnceObserver<>(observer));
    }

    /**
     * @deprecated Use {@link EventLiveData#observe(LifecycleOwner, EventObserver)} instead
     */
    @Deprecated
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Event<T>> observer) {
        throw new UnsupportedOperationException("You should use other observe method.");
    }

    private static final class OnceObserver<T> implements Observer<Event<T>> {

        @NonNull
        private final EventObserver<T> eventObserver;

        private OnceObserver(@NonNull EventObserver<T> eventObserver) {
            this.eventObserver = eventObserver;
        }

        @Override
        public void onChanged(Event<T> event) {
            if (!event.isHandled()) {
                event.setHandled(true);
                final T context = event.getContext();
                eventObserver.onChanged(context);
            }
        }
    }

}
