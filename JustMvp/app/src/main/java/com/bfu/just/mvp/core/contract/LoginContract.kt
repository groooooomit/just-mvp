package com.bfu.just.mvp.core.contract

import just.mvp.IPresenter
import just.mvp.IView


interface LoginContract {

    interface View : IView {

        fun showLoginStart()

        fun showLoginEnd()

        fun goMainPage(token: String)

        val width: Int
    }

    interface Presenter : IPresenter<View> {

        fun login(username: String?, password: String?)
    }

}