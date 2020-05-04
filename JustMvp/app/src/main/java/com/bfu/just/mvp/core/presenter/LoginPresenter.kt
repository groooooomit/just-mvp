package com.bfu.just.mvp.core.presenter

import com.bfu.just.mvp.core.contract.LoginContract

class LoginPresenter : LogPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String?, password: String?) {
        view?.showLoginStart()

        // check username
        if (username.isNullOrEmpty()) {
            view?.apply {
                showLoginEnd()
                toast("用户名不能为空")
            }
            return
        }

        // check password
        if (password.isNullOrEmpty()) {
            view?.apply {
                showLoginEnd()
                toast("密码不能为空")
            }
            return
        }

        // 模拟耗时登录
        Thread {
            Thread.sleep(5000)
            if ("user" == username && "123456" == password) {
                runOnUi {
                    view?.apply {
                        showLoginEnd()
                        toastLong("登录成功")
                        goMainPage("abc123")
                    }
                }
            } else {
                runOnUi {
                    view?.apply {
                        showLoginEnd()
                        snack("用户名或密码错误")
                    }
                }
            }
        }.start()
    }
}