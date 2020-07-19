package just.mvp.ext.livedata;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * 和 {@link EventLiveData} 类似，只不过不携带任何参数
 */
public abstract class SignalLiveData extends LiveData<Signal> {

    public void observe(@NonNull LifecycleOwner owner, @NonNull SignalObserver observer) {
        if (owner instanceof Fragment) {
            /* Google 填坑：Fragment 要通过 getViewLifecycleOwner() 来获取正确的 LifecycleOwner. https://medium.com/@cs.ibrahimyilmaz/viewlifecycleowner-vs-this-a8259800367b */
            final Fragment fragment = (Fragment) owner;
            super.observe(fragment.getViewLifecycleOwner(), new OnceObserver<>(observer));
        } else {
            super.observe(owner, new OnceObserver<>(observer));
        }
    }

    /**
     * @deprecated Use {@link SignalLiveData#observe(LifecycleOwner, SignalObserver)} instead
     */
    @Deprecated
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Signal> observer) {
        throw new UnsupportedOperationException("You should use other observe method.");
    }

    private static final class OnceObserver<T> implements Observer<Signal> {

        @NonNull
        private final SignalObserver signalObserver;

        private OnceObserver(@NonNull SignalObserver signalObserver) {
            this.signalObserver = signalObserver;
        }

        @Override
        public void onChanged(Signal signal) {
            if (!signal.isHandled()) {
                signal.setHandled(true);
                signalObserver.onChanged();
            }
        }
    }

}
