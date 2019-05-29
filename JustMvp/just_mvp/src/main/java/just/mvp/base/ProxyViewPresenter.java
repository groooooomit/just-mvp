package just.mvp.base;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 对 View 进行动态代理，在调用 View 的具体方法前，先进行 isActive 判断
 * <p>
 * 代理 View 持有真实 View 的弱引用以防止内存泄漏
 */
public class ProxyViewPresenter<V extends IView> extends LifeCyclePresenter<V> {

    @Override
    public void attachView(V view) {
        super.attachView(createProxyView(view));
    }

    /**
     * 创建 IView 的代理对象，当 {@link IView#isActive()} 为 false 时，不执行后续方法调用
     * <p>
     * 代理对象持有真实对象的弱引用，防止内存泄漏
     */
    private static <T extends IView> T createProxyView(T target) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new ViewActiveInvocationHandler(target));
    }

    // Presenter 持有 ProxyView，ProxyView 持有 InvocationHandler，InvocationHandler 持有 View 的弱引用
    private static final class ViewActiveInvocationHandler implements InvocationHandler {

        private static final String METHOD_IS_ACTIVE = "isActive";
        private final WeakReference<IView> targetViewRef;

        private ViewActiveInvocationHandler(IView target) {
            this.targetViewRef = new WeakReference<>(target);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /* 获取 target view. */
            final IView targetView = targetViewRef.get();
            final boolean isTargetViewActive = null != targetView && targetView.isActive();

            /* 判断调用的方法是不是 isActive. */
            if (METHOD_IS_ACTIVE.equals(method.getName())) {
                return isTargetViewActive;
            } else {
                /* 处理其他方法. */
                if (isTargetViewActive) {
                    /* 如果状态为 active，那么调用后续方法. */
                    return method.invoke(targetView, args);
                } else {
                    /* [注意] 基本数据类型是不接受 null 值的. */
                    return null;
                }
            }
        }
    }

}