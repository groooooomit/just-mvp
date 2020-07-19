package just.mvp.ext.livedata;

import androidx.annotation.NonNull;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * <p>
 * Evolved from https://github.com/android/architecture-samples/blob/todo-mvvm-live/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/Event.java
 */
public class Event<T> {

    @NonNull
    private final T context;

    private boolean handled = false;

    public Event(@NonNull T context) {
        this.context = context;
    }

    @NonNull
    public T getContext() {
        return context;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public boolean isHandled() {
        return handled;
    }

}