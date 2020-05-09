package com.bfu.just.mvp.core.presenter

import com.bfu.just.mvp.core.contract.MainContract
import just.mvp.BasePresenter

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun afterViewCreate() {
        val token = view!!.data.getString("token")
        runOnUi { it.showToken(token) }
    }
}