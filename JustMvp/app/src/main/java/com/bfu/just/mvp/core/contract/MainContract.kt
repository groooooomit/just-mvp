package com.bfu.just.mvp.core.contract

import just.mvp.IPresenter
import just.mvp.IView

interface MainContract {

    interface View : IView {

        /**
         * 展示 token
         */
        fun showToken(token: String?)

    }

    interface Presenter : IPresenter<View>
}