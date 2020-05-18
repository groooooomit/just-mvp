package com.bfu.just.mvp.ui.activity

import android.os.Bundle
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.HomeContract
import com.bfu.just.mvp.core.presenter.HomePresenter
import just.mvp.PresenterActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : PresenterActivity<HomePresenter>(), HomeContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun showToken(token: String?) {
        txt_token.text = token ?: "--"
    }

}