package com.bfu.just.mvp.ui.activity

import android.os.Bundle
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.MainContract
import com.bfu.just.mvp.core.presenter.MainPresenter
import just.mvp.PresenterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PresenterActivity<MainPresenter>(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showToken(token: String?) {
        txt_token.text = token ?: "--"
    }

}
