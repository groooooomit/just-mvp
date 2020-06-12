package com.bfu.just.mvp.core.model

import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

data class LoginParam(val username: String?, val password: String?) {

    @Throws(Exception::class)
    fun valid() {
        if (username.isNullOrEmpty()) {
            throw Exception("用户名不能为空")
        }
        if (password.isNullOrEmpty()) {
            throw Exception("密码不能为空")
        }
    }
}

/**
 * 用户 model
 */
class UserModel {

    fun login(username: String?, password: String?): Single<String> = Single

        /* 构造登录参数. */
        .fromCallable { LoginParam(username, password) }

        /* 参数验证. */
        .doOnSuccess { it.valid() }

        /* 模拟耗时操作. */
        .delay(5, TimeUnit.SECONDS)

        /* 模拟账号登录逻辑. */
        .map {
            if ("user" == it.username!! && "123456" == it.password) "ABCDEFG"
            else throw Exception("用户名或密码错误")
        }

}

