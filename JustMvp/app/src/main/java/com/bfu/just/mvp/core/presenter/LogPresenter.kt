package com.bfu.just.mvp.core.presenter

import android.util.Log
import just.mvp.BasePresenter
import just.mvp.base.IView

open class LogPresenter<V : IView> : BasePresenter<V>() {
    companion object {
        const val TAG = "JustPresenter"
    }

    override fun afterViewCreate() {
        Log.d(TAG, "${hashCode()} >>>> afterViewCreate")
    }

    override fun afterViewStart() {
        Log.d(TAG, "${hashCode()} >>>> afterViewStart")
    }

    override fun afterViewResume() {
        Log.d(TAG, "${hashCode()} >>>> afterViewResume")
    }

    override fun beforeViewPause() {
        Log.d(TAG, "${hashCode()} >>>> beforeViewPause")
    }

    override fun beforeViewStop() {
        Log.d(TAG, "${hashCode()} >>>> beforeViewStop")
    }

    override fun beforeViewDestroy() {
        Log.d(TAG, "${hashCode()} >>>> beforeViewDestroy")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "${hashCode()} >>>> onCleared")
    }

    override fun attachView(view: V) {
        super.attachView(view)
        Log.d(TAG, "${hashCode()} >>>> attachView")
    }
}