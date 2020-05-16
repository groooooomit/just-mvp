package com.bfu.just.mvp.core.presenter

import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.model.UserLoginModel
import just.mvp.BasePresenter

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private lateinit var userLoginModel: UserLoginModel
    private var isLogining = false

    override fun onInitialize() {
        userLoginModel = UserLoginModel(application)
    }

    override fun afterViewCreate() {
        if (isLogining) {
            view?.showLoginStart()
        } else {
            view?.showLoginEnd()
        }
    }

    override fun login(username: String?, password: String?) {
        isLogining = true
        view?.showLoginStart()

        // check username
        if (username.isNullOrEmpty()) {
            view?.apply {
                showLoginEnd()
                toast("用户名不能为空")
            }
            isLogining = false
            return
        }

        // check password
        if (password.isNullOrEmpty()) {
            view?.apply {
                showLoginEnd()
                toast("密码不能为空")
            }
            isLogining = false
            return
        }

        // login
        userLoginModel.login(username, password,
            onSuccess = { token ->
                runOnUi { view ->
                    view.apply {
                        showLoginEnd()
                        toastLong("登录成功")
                        goMainPage(token)
                    }
                    isLogining = false
                }
            },
            onFailure = { error ->
                runOnUi { view ->
                    view.apply {
                        showLoginEnd()
                        toastLong(error.message ?: "未知错误")
                    }
                    isLogining = false
                }
            })
    }
}