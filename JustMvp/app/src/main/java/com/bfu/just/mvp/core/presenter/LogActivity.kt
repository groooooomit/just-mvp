package com.bfu.just.mvp.core.presenter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class LogActivity : AppCompatActivity() {
    private val tag = "LifeCycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onResume")
    }

    override fun onPause() {
        Log.i(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(tag, "${this.javaClass.simpleName} -${hashCode()} >>>> onDestroy")
        super.onDestroy()
    }

}