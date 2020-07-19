package just.mvp.ext.livedata;

/**
 * 和 {@link MutableEventLiveData} 类似，只不过不携带任何参数
 */
public class MutableSignalLiveData extends SignalLiveData {

    public void send() {
        setValue(new Signal());
    }

    public void post() {
        postValue(new Signal());
    }

}
