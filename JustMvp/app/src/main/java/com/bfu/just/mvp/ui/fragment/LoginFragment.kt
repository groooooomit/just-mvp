package com.bfu.just.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.presenter.LoginPresenter
import com.bfu.just.mvp.ui.activity.HomeActivity
import just.mvp.PresenterFragment
import just.mvp.widget.LayoutResId
import kotlinx.android.synthetic.main.activity_login.*

@LayoutResId(R.layout.fragment_login)
class LoginFragment : PresenterFragment<LoginPresenter>(), LoginContract.View {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login.setOnClickListener {
            /* 用户名： user， 密码： 123456 */
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()
            presenter.login(usernameStr, passwordStr)
        }
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
        startActivity(Intent(activity, HomeActivity::class.java))
        activity?.finish()
    }

}