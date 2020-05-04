package com.bfu.just.mvp.core.presenter

import android.util.Log
import androidx.annotation.CallSuper
import just.mvp.BasePresenter
import just.mvp.base.IView

open class LogPresenter<V : IView> : BasePresenter<V>() {

    private val tag = this::class.simpleName

    @CallSuper
    override fun afterViewCreate() {
        Log.d(tag, "${hashCode()} >>>> afterViewCreate")
    }

    @CallSuper
    override fun afterViewStart() {
        Log.d(tag, "${hashCode()} >>>> afterViewStart")
    }

    @CallSuper
    override fun afterViewResume() {
        Log.d(tag, "${hashCode()} >>>> afterViewResume")
    }

    @CallSuper
    override fun beforeViewPause() {
        Log.d(tag, "${hashCode()} >>>> beforeViewPause")
    }

    @CallSuper
    override fun beforeViewStop() {
        Log.d(tag, "${hashCode()} >>>> beforeViewStop")
    }

    @CallSuper
    override fun beforeViewDestroy() {
        Log.d(tag, "${hashCode()} >>>> beforeViewDestroy")
    }

    @CallSuper
    override fun onInitialize() {
        Log.d(tag, "${hashCode()} >>>> onInitialize")
    }

    @CallSuper
    override fun onRelease() {
        super.onRelease()
        Log.d(tag, "${hashCode()} >>>> onRelease")

    }

    @CallSuper
    override fun onAttachView(view: V) {
        super.onAttachView(view)
        Log.d(tag, "${hashCode()} >>>> onAttachView")
    }

    @CallSuper
    override fun onDetachView(view: V) {
        super.onDetachView(view)
        Log.d(tag, "${hashCode()} >>>> onDetachView")

    }
}