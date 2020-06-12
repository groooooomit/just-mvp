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
        settings.token.observe(view, Observer { view.showToken(it) })
    }

}