package just.mvp;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import just.mvp.base.BasePresenter;
import just.mvp.base.IView;

/**
 * 通过动态代理对 View 对象的方法调用统一进行 active 状态判断
 * <pre>
 * 当原始 View 的状态处于非 active 状态时：
 * 1、如果代理 View 对象调用 isActive 方法，那么将将返回 false；
 * 2、如果代理 View 对象调用没有返回值（即 void）的方法，因为已经处于非 active 状态，原始 View 对应的方法行为不会被执行；
 * 3、如果代理 View 对象调用返回类型为基本数据类型的方法，因为已经处于非 active 状态，原始 View 对应的方法行为不会被执行，将直接返回基本数据类型的默认值 {@link ProxyViewPresenter#PRIMITIVE_DEFAULT_VALUE}；
 * 4、如果代理 View 对象调用返回类型为对象数据类型的方法，因为已经处于非 active 状态，原始 View 对应的方法行为不会被执行，将直接返回 null，对此要做好非空检查；
 * <pre/>
 * 动态代理有性能影响，另外如果 Presenter 中出现较多的上述第 4 种对 View 的使用场景，容易因为疏忽造成空指针异常。如果不考虑这两个缺点，那么使用它也是 OK 的
 * @param <V>
 */
public class ProxyViewPresenter<V extends IView> extends BasePresenter<V> {

    /**
     * View 的代理对象
     */
    @Nullable
    private V proxyView;

    @CallSuper
    @Override
    public void onAttachView(@NonNull V view) {

        /* 根据实际传入的 View 创建它的代理对象. */
        if (null == proxyView) {
            //noinspection unchecked
            proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new ViewActiveInvocationHandler<V>());
        }

        /* 代理 view 持有原始 view 的引用. */
        //noinspection unchecked
        ((ViewActiveInvocationHandler<V>) Proxy.getInvocationHandler(proxyView)).storeViewRef(view);
    }

    @CallSuper
    @Override
    public void onDetachView(@NonNull V view) {
        /* 代理 view 清除对原始 view 的引用. */
        //noinspection unchecked
        ((ViewActiveInvocationHandler<V>) Proxy.getInvocationHandler(Objects.requireNonNull(proxyView))).clearViewRef();
    }

    /**
     * 返回代理 View，第一次回调 onAttachView 前，proxyView 为 null，只要进行过一次 attach 后，Presenter 便得到了 View 的实际类型，此后 ProxyView 将不会为 null。
     * <p>
     * ProxyView 会在 onDetachView 回调时清除自己持有的原始 View 的引用，以避免造成 View 的内存泄漏的出现
     */
    @NonNull
    public V getView() {
        if (null == proxyView) {
            throw new RuntimeException("You should getView since onAttachView()");
        }
        return proxyView;
    }

    ///////////////////////////////////////////////////////////////////////////
    // static components
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 基本数据类型的默认值.
     */
    // @formatter:off
    @NonNull
    private static final Map<Class<?>, Object> PRIMITIVE_DEFAULT_VALUE = new HashMap<Class<?>, Object>() {{
        put(byte.class,     (byte)  0);
        put(short.class,    (short) 0);
        put(char.class,     (char)  0);
        put(int.class,      0        );
        put(long.class,     0L       );
        put(float.class,    0F       );
        put(double.class,   0D       );
        put(boolean.class,  false    );
    }};
    // @formatter:on

    /**
     * Presenter 持有 ProxyView，ProxyView 持有 InvocationHandler，InvocationHandler 持有 原始 View 的引用
     */
    private static final class ViewActiveInvocationHandler<V extends IView> implements InvocationHandler {

        /**
         * 原始 View 的引用
         */
        @Nullable
        private V targetViewRef;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /* 获取原始 view 的 active 状态. */
            final boolean isTargetViewActive = null != targetViewRef && targetViewRef.isActive();
            if ("isActive".equals(method.getName())) {
                /* 如果调用的方法是 isActive，那么直接返回当前状态. */
                return isTargetViewActive;
            } else {
                /* 处理其他方法. */
                if (isTargetViewActive) {
                    /* 如果状态为 active，那么调用后续方法. */
                    return method.invoke(targetViewRef, args);
                } else {
                    /* 否则返回默认值，如果不是基本数据类型，那么返回 null. */
                    return PRIMITIVE_DEFAULT_VALUE.get(method.getReturnType());
                }
            }
        }

        private void storeViewRef(@NonNull V view) {
            this.targetViewRef = view;
        }

        private void clearViewRef() {
            this.targetViewRef = null;
        }
    }

}
