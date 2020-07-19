package com.bfu.just.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.LoginViewModel
import com.bfu.just.mvp.core.viewmodel.LoginViewModelImpl
import just.mvp.ktx.*
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Activity 作为 View 的 kotlin demo
 */
class Login4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()
        viewModel.info.observeData(this) { txt_info.text = it }
        viewModel.toast.observeEvent(this) { toast(it, ToastLength.SHORT, ToastGravity.CENTER) }
        viewModel.goHomePage.observeSignal(this) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        viewModel.isLogining.observeData(this) {
            loading.visibility = if (it) View.VISIBLE else View.GONE
            username.isEnabled = !it
            password.isEnabled = !it
            login.isEnabled = !it
        }
        login.setOnClickListener {
            /* 用户名： user， 密码： 123456 */
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()
            viewModel.login(usernameStr, passwordStr)
        }
    }

}