package com.bfu.just.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bfu.just.mvp.R
import com.bfu.just.mvp.core.contract.LoginViewModel
import com.bfu.just.mvp.core.viewmodel.LoginViewModelImpl
import com.bfu.just.mvp.ui.activity.HomeActivity
import just.mvp.ktx.observeData
import just.mvp.ktx.observeEvent
import just.mvp.ktx.observeSignal
import kotlinx.android.synthetic.main.activity_login.*

class Login4Fragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()
        viewModel.info.observeData(this) { txt_info.text = it }
        viewModel.toast.observeEvent(this) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.goHomePage.observeSignal(this) {
            activity?.apply {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
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