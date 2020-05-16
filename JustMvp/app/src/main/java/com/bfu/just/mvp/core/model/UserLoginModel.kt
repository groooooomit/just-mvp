package com.bfu.just.mvp.core.model

import android.app.Application
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class UserLoginModel(private val application: Application) {

    fun login(
        username: String,
        password: String
    ): Single<String> = Completable.complete()
        .delay(5, TimeUnit.SECONDS) /* 模拟耗时操作. */
        .toSingle { if ("user" == username && "123456" == password) "ABCDEFG" else throw Exception("用户名或密码错误") } /* 模拟账号登录逻辑. */

}

