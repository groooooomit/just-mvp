package com.bfu.just.mvp.ui.activity

import android.os.Bundle
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.presenter.LogActivity
import com.bfu.just.mvp.ui.fragment.LoginFragment


class HomeActivityActivity : LogActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var fragment = supportFragmentManager.findFragmentByTag("LoginFragment")
        if (null == fragment) {
            fragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, "LoginFragment")
                .commit()
        }

    }

}