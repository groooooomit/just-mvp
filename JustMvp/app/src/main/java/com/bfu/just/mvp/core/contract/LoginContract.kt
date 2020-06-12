package com.bfu.just.mvp.core.contract

import just.mvp.base.IPresenter
import just.mvp.base.IView


interface LoginContract {

    interface View : IView {

        fun showInfo(info: String?)

        fun showLoginStart()

        fun showLoginEnd()

        fun goHomePage()

    }

    interface Presenter : IPresenter<View> {

        fun login(username: String?, password: String?)
    }

}