package just.mvp.ext.livedata;

import androidx.annotation.NonNull;

/**
 * 防止数据更新被重复触发
 *
 * @param <T>
 */
public class MutableEventLiveData<T> extends EventLiveData<T> {

    public void send(@NonNull T content) {
        setValue(new Event<>(content));
    }

    public void post(@NonNull T content) {
        postValue(new Event<>(content));
    }

}
