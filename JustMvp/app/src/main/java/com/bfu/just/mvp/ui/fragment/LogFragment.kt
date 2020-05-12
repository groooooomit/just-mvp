package com.bfu.just.mvp.ui.fragment

import android.os.Bundle
import android.util.Log
import just.mvp.base.IPresenter
import just.mvp.PresenterFragment

abstract class LogFragment<P : IPresenter<*>> : PresenterFragment<P>() {

    private val label = "LifeCycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(label, "${this.javaClass.simpleName} - ${hashCode()} >>>> onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.w(label, "${this.javaClass.simpleName} - ${hashCode()} >>>> onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.w(label, "${this.javaClass.simpleName} - ${hashCode()} >>>> onResume")
    }

    override fun onPause() {
        Log.w(label, "${this.javaClass.simpleName} - ${hashCode()} >>>> onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.w(label, "${this.javaClass.simpleName} - ${hashCode()} >>>> onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.w(label, "${this.javaClass.simpleName} - ${hashCode()} >>>> onDestroy")
        super.onDestroy()
    }


}