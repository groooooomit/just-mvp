package com.bfu.just.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.presenter.LoginPresenter
import just.mvp.PresenterActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Activity 作为 View 的 kotlin demo
 */
class LoginActivity : PresenterActivity<LoginPresenter>(), LoginContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener {
            /* 用户名： user， 密码： 123456 */
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()
            presenter.login(usernameStr, passwordStr)
        }
        presenter.info.observe(this, Observer { txt_info.text = it })
    }

    override fun showLoginStart() {
        loading.visibility = View.VISIBLE
        username.isEnabled = false
        password.isEnabled = false
        login.isEnabled = false
    }

    override fun showLoginEnd() {
        loading.visibility = View.GONE
        username.isEnabled = true
        password.isEnabled = true
        login.isEnabled = true
    }

    override fun goHomePage() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}