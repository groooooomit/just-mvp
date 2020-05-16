package com.bfu.just.mvp.core.model

import android.app.Application

typealias  OnSuccess = (token: String) -> Unit
typealias  OnFailure = (error: Throwable) -> Unit

class UserLoginModel(private val application: Application) {

    fun login(
        username: String,
        password: String,
        onSuccess: OnSuccess,
        onFailure: OnFailure
    ) {
        // 模拟耗时登录
        Thread {
            Thread.sleep(5000)
            if ("user" == username && "123456" == password) {
                onSuccess("ABCDEFG")
            } else {
                onFailure(Exception("用户名或密码错误"))
            }
        }.start()
    }

}