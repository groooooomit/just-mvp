package com.bfu.just.mvp.core.presenter

import android.app.Application
import androidx.lifecycle.Observer
import com.bfu.just.mvp.app.App
import com.bfu.just.mvp.app.Settings
import com.bfu.just.mvp.core.contract.HomeContract
import just.mvp.base.BasePresenter

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private lateinit var settings: Settings

    override fun onInitialize(application: Application) {
        settings = (application as App).settings
    }

    override fun onAttachView(view: HomeContract.View) {
        settings.token.observe(view.lifecycleOwner, Observer { view.showToken(it) })
    }

    override fun afterViewStart() {
        /* 获取前一个页面传过来的数据. */
        val message = activeView?.args?.getString("arg_message")
        activeView?.toast(message ?: "No message")
    }

}