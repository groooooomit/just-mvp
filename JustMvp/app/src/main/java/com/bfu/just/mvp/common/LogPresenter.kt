package com.bfu.just.mvp.common

import android.util.Log
import androidx.annotation.CallSuper
import just.mvp.BasePresenter
import just.mvp.base.IView

open class LogPresenter<V : IView> : BasePresenter<V>() {

    private val tag = "LifeCycle"

    @CallSuper
    override fun onInitialize() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onInitialize")
    }

    @CallSuper
    override fun onAttachView(view: V) {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onAttachView")
    }

    @CallSuper
    override fun afterViewCreate() {
        Log.d(tag, "${this.javaClass.simpleName} - ${hashCode()} >>>> afterViewCreate")
    }

    @CallSuper
    override fun afterViewStart() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> afterViewStart")
    }

    @CallSuper
    override fun afterViewResume() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> afterViewResume")
    }

    @CallSuper
    override fun beforeViewPause() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> beforeViewPause")
    }

    @CallSuper
    override fun beforeViewStop() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> beforeViewStop")
    }

    @CallSuper
    override fun beforeViewDestroy() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> beforeViewDestroy")
    }

    @CallSuper
    override fun onDetachView(view: V) {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onDetachView")
    }

    @CallSuper
    override fun onCleared() {
        Log.d(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onCleared")
    }

}