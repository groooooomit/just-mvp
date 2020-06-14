package com.bfu.just.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bfu.just.mvp.R
import com.bfu.just.mvp.app.bind
import com.bfu.just.mvp.core.contract.HomeContract
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.presenter.HomePresenter
import com.bfu.just.mvp.core.presenter.LoginPresenter
import com.bfu.just.mvp.ui.activity.HomeActivity
import just.mvp.base.Presenters
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Fragment 作为 View 的非继承 kotlin demo
 */
class Login3Fragment : Fragment(R.layout.fragment_login), LoginContract.View, HomeContract.View {

    /* 1. 声明 Presenter. */
    private lateinit var loginPresenter: LoginContract.Presenter
    private lateinit var homePresenter: HomeContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.isClickable = true
        login.setOnClickListener {

            /* 用户名： user， 密码： 123456 */
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()

            /* 3. Presenter 执行业务逻辑. */
            loginPresenter.login(usernameStr, passwordStr)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /* 2. View 和 Presenter 绑定. */
        loginPresenter = bind("login-presenter") { LoginPresenter() }
        homePresenter = bind("home-presenter") { HomePresenter() }
    }

    override fun showInfo(info: String?) {
        txt_info.text = info
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
        val intent = Intent(activity, HomeActivity::class.java)
        /* 页面间数据传递. */
        intent.putExtra("arg_message", "I'm a message")
        startActivity(intent)
        activity?.finish()
    }

    override fun showToken(token: String?) {

    }
}