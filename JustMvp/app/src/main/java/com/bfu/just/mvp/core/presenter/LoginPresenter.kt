package com.bfu.just.mvp.core.presenter

import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import just.mvp.BasePresenter

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    /**
     * 用户 Model
     */
    private lateinit var userModel: UserModel

    /**
     * 标记当前是否正在登录
     */
    private var isLogining = false

    /**
     * disposable 容器
     */
    private val compositeDisposable = CompositeDisposable()

    override fun onInitialize() {
        userModel = UserModel(application)
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

        // login
        userModel.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                view?.showLoginEnd()
                isLogining = false
            }
            .subscribeBy(
                onSuccess = { token ->
                    view?.toastLong("登录成功")
                    view?.goMainPage(token)
                },
                onError = { error ->
                    view?.toastLong(error.message ?: "未知错误")
                }
            ).let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }
}