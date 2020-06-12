package com.bfu.just.mvp.core.presenter

import androidx.annotation.CallSuper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.disposables.DisposableContainer
import just.mvp.BasePresenter
import just.mvp.base.IView

abstract class RxPresenter<V : IView>(private val container: CompositeDisposable = CompositeDisposable()) : BasePresenter<V>(), DisposableContainer by container {

    @CallSuper
    override fun onCleared() {
        container.dispose()
    }

    protected fun Disposable.attach(): Disposable = apply { container.add(this) }

}
