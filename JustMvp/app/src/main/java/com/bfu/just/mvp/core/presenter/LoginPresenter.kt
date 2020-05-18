package com.bfu.just.mvp.core.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bfu.just.mvp.app.App
import com.bfu.just.mvp.app.Settings
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import just.mvp.BasePresenter

/**
 *  [LoginPresenter] 的 LiveData 版本
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    /**
     * 用户 Model
     */
    private val userModel = UserModel()

    /**
     * 标记当前是否正在登录
     */
    private val isLogining = MutableLiveData<Boolean>()

    /**
     * 全局属性
     */
    private lateinit var settings: Settings

    /**
     * disposable 容器
     */
    private val compositeDisposable = CompositeDisposable()

    override fun onInitialize() {
        settings = (application as App).settings
    }

    override fun onAttachView(view: LoginContract.View) {
        isLogining.observe(view, Observer { if (it) view.showLoginStart() else view.showLoginEnd() })
    }

    override fun onDetachView(view: LoginContract.View) {
        isLogining.removeObservers(view)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
    }

    override fun login(username: String?, password: String?) {
        isLogining.value = true

        // login
        userModel.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { isLogining.value = false }
            .subscribeBy(
                onSuccess = { token ->
                    settings.token.value = token
                    view?.toastLong("登录成功")
                    view?.goHomePage()
                },
                onError = { error ->
                    view?.toastLong(error.message ?: "未知错误")
                }
            ).let { compositeDisposable.add(it) }
    }

}