package com.bfu.just.mvp

import android.app.Application
import com.squareup.leakcanary.LeakCanary

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // 内存泄漏检查
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
    }
}
