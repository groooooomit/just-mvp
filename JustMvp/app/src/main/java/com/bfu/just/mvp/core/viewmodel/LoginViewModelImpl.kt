package com.bfu.just.mvp.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bfu.just.mvp.app.App
import com.bfu.just.mvp.core.contract.LoginViewModel
import com.bfu.just.mvp.core.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import just.mvp.ext.livedata.MutableEventLiveData
import just.mvp.ext.livedata.MutableSignalLiveData

class LoginViewModelImpl(application: Application) : AndroidViewModel(application), LoginViewModel {

    override val info = MutableLiveData("")

    override val isLogining = MutableLiveData<Boolean>()

    override val toast = MutableEventLiveData<String>()

    override val goHomePage = MutableSignalLiveData()

    private val settings = (application as App).settings

    private val userModel = UserModel()

    private val compDisposable = CompositeDisposable()

    override fun login(username: String?, password: String?) {
        if (isLogining.value != true) {
            isLogining.value = true
            info.value = "正在进行登录..."
            // login
            // todo MVVM + Retrofit + Coroutines
            userModel.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isLogining.value = false }
                .doOnSuccess { settings.token.value = it }
                .doOnDispose { info.value = "登录已取消" }
                .subscribeBy(
                    onSuccess = { 
                        info.value = "登录成功"
                        toast.send("登录成功")
                        goHomePage.send()
                    },
                    onError = { error ->
                        toast.send("登录异常")
                        info.value = "登录异常：${error.message ?: "Unknown"}"
                    }
                ).also { compDisposable.add(it) }
        }
    }

    override fun onCleared() {
        compDisposable.dispose()
    }


}