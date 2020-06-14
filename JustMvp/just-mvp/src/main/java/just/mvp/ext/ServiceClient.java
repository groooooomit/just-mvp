package just.mvp.ext;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * 封装绑定或开启 service 的操作
 *
 * @param <B>
 */
public abstract class ServiceClient<B extends IBinder> {

    @NonNull
    private final Context context;

    @NonNull
    private final ServiceConnection serviceConnection;

    @NonNull
    private final Class<? extends Service> serviceClass;

    @Nullable
    private B binder;

    public ServiceClient(@NonNull Context context, @NonNull Class<? extends Service> serviceClass) {
        this.context = context;
        this.serviceClass = serviceClass;
        this.serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //noinspection unchecked
                binder = (B) service;

                /* 回调 connected. */
                ServiceClient.this.onServiceConnected(binder);

                /* 执行尚未执行的任务. */
                for (PendingRunnable<B> pendingRunnable : pendingRunnables) {
                    pendingRunnable.run(binder);

                    /* 任务执行后将其从 pending 集合移除. */
                    pendingRunnables.remove(pendingRunnable);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                if (binder != null) {
                    ServiceClient.this.onServiceDisconnected(binder);
                }
                binder = null;
            }
        };
    }

    @Nullable
    public final B getBinder() {
        return binder;
    }

    public final boolean isBind() {
        return null != this.binder;
    }

    public final void bind() {
        bind(Context.BIND_AUTO_CREATE);
    }

    public final void bind(int flags) {
        if (!isBind()) {
            final Intent intent = new Intent(context, serviceClass);
            context.bindService(intent, serviceConnection, flags);
            postBindService();
        }
    }

    public final void unBind() {
        if (isBind()) {
            preUnBindService(binder);
            context.unbindService(serviceConnection);
            binder = null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    @FunctionalInterface
    public interface PendingRunnable<B> {
        void run(@NonNull B binder);
    }

    @NonNull
    private final List<PendingRunnable<B>> pendingRunnables = new LinkedList<>();

    public final void postAction(@NonNull PendingRunnable<B> action) {
        if (isBind()) {
            final B binder = getBinder();
            action.run(binder);
        } else {
            /* 尚未绑定，将其加入队列，待绑定后执行. */
            pendingRunnables.add(action);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 开启或停止 Service
    ///////////////////////////////////////////////////////////////////////////

    public final void startService() {
        startService(null);
    }

    public final void startService(@NonNull String key, @NonNull Parcelable value) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(key, value);
        startService(bundle);
    }

    public final void startService(@NonNull String key, @NonNull String value) {
        final Bundle bundle = new Bundle();
        bundle.putString(key, value);
        startService(bundle);
    }


    public final void startService(@Nullable Bundle bundle) {
        final Intent intent = new Intent(context, serviceClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startService(intent);
    }

    public final void stopService() {
        final Intent intent = new Intent(context, serviceClass);
        context.stopService(intent);
    }

    ///////////////////////////////////////////////////////////////////////////
    // abstract method
    ///////////////////////////////////////////////////////////////////////////

    protected abstract void onServiceConnected(@NonNull B binder);

    protected abstract void onServiceDisconnected(@NonNull B binder);

    /**
     * bindService 执行之后调用
     */
    protected abstract void postBindService();

    /**
     * unbindService 执行之前调用
     */
    protected abstract void preUnBindService(@NonNull B binder);


}
