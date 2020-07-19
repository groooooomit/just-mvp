package com.bfu.just.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.presenter.LoginPresenter
import com.bfu.just.mvp.ui.activity.HomeActivity
import just.mvp.PresenterFragment
import just.mvp.ktx.observeBy
import just.mvp.widget.LayoutResId
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Fragment 作为 View 的 kotlin demo
 */
@LayoutResId(R.layout.fragment_login)
class LoginFragment : PresenterFragment<LoginPresenter>(), LoginContract.View {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login.setOnClickListener {
            /* 用户名： user， 密码： 123456 */
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()
            presenter.login(usernameStr, passwordStr)
        }
        presenter.info.observeBy(this) { txt_info.text = it }
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

}