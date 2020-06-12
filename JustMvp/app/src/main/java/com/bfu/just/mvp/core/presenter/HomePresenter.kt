package com.bfu.just.mvp.core.presenter

import androidx.lifecycle.Observer
import com.bfu.just.mvp.app.App
import com.bfu.just.mvp.app.Settings
import com.bfu.just.mvp.core.contract.HomeContract
import just.mvp.BasePresenter

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private lateinit var settings: Settings

    override fun onInitialize() {
        settings = (application as App).settings
    }

    override fun onAttachView(view: HomeContract.View) {
        /* 获取全局数据. */
        settings.token.observe(view, Observer { view.showToken(it) })
    }

    override fun afterViewCreate() {
        /* 获取前一个页面传过来的数据. */
        val message = view?.data?.getString("arg_message")
        view?.toast(message ?: "No message")
    }

}