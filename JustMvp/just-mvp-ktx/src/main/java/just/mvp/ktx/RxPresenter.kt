package just.mvp.ktx

import androidx.annotation.CallSuper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import just.mvp.base.BasePresenter
import just.mvp.base.IView

/**
 * 内置两个 CompositeDisposable 管理 RxJava 产生的 Disposable
 */
abstract class RxPresenter<V : IView> : BasePresenter<V>() {

    private val viewModelContainer = CompositeDisposable()

    private lateinit var lifecycleContainer: CompositeDisposable

    @CallSuper
    override fun afterViewCreate() {
        lifecycleContainer = CompositeDisposable()
    }

    @CallSuper
    override fun beforeViewDestroy() {
        lifecycleContainer.dispose()
    }

    @CallSuper
    override fun onCleared() {
        viewModelContainer.dispose()
    }

    /**
     * 当 Presenter 所持有的 View 销毁时执行 [Disposable.dispose]
     */
    protected fun Disposable.disposeWhenViewDestroy(): Disposable = apply { lifecycleContainer.add(this) }

    /**
     * 当 Presenter 销毁时执行 [Disposable.dispose]
     */
    protected fun Disposable.disposeWhenCleared(): Disposable = apply { viewModelContainer.add(this) }


}
