package com.bfu.just.mvp.core.presenter

import com.bfu.just.mvp.core.contract.LoginContract

class LoginPresenter : LogPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String?, password: String?) {
        view.showLoginStart()

        // check username
        if (username.isNullOrEmpty()) {
            view.showLoginEnd()
            view.showMessage("用户名不能为空")
            return
        }

        // check password
        if (password.isNullOrEmpty()) {
            view.showLoginEnd()
            view.showMessage("密码不能为空")
            return
        }

        // 模拟登录
        runOnUi(1500) {
            if ("user" == username && "123456" == password) {
                view.showLoginEnd()
                view.showMessage("登录成功")
                view.goMainPage()
            } else {
                view.showLoginEnd()
                view.showMessage("用户名或密码错误")
            }
        }
    }
}