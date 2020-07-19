package just.mvp.ext.livedata;

/**
 * 和 {@link Event} 类似，只不过不携带其他内容
 */
public class Signal {

    private boolean handled = false;

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
}
