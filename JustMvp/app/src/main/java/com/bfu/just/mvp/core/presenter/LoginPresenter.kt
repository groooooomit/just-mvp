package com.bfu.just.mvp.core.presenter

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bfu.just.mvp.app.App
import com.bfu.just.mvp.app.Settings
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import just.mvp.ext.livedata.EventObserver
import just.mvp.ext.livedata.MutableEventLiveData
import just.mvp.ext.livedata.MutableSignalLiveData
import just.mvp.ext.livedata.SignalObserver
import just.mvp.ktx.RxPresenter
import just.mvp.ktx.observeData

/**
 *  [LoginPresenter] 的 LiveData 版本
 *  TODO 1、presenter 增加协程支持 viewScope presenterScope
 *  TODO 2、修改插件生成的模板代码，a、presenter 添加 onInitialize 和 onAttachView
 */
class LoginPresenter : RxPresenter<LoginContract.View>(), LoginContract.Presenter {

    /**
     * 用户 Model
     */
    private val userModel = UserModel()

    /**
     * 用于记录并更新界面登录状态
     */
    private val isLogining = MutableLiveData<Boolean>()

    /**
     * 用于记录并更新界面提示信息
     */
    override val info = MutableLiveData<String>()

    /**
     * 发送界面跳转信号，演示 SignalLiveData 的使用
     */
    private val navigateHomePageSignal = MutableSignalLiveData()

    /**
     * 发送 toast，演示 EventLiveData 的使用
     */
    private val toastEvent = MutableEventLiveData<String>()

    /**
     * 全局属性
     */
    private lateinit var settings: Settings

    override fun onInitialize(application: Application) {
        settings = (application as App).settings
    }

    override fun onAttachView(view: LoginContract.View) {
        // liveData 安全及时地更新 view
        isLogining.observeData(view) { if (it) view.showLoginStart() else view.showLoginEnd() }
        navigateHomePageSignal.observe(view, SignalObserver { view.goHomePage() })
        toastEvent.observe(view, EventObserver { view.toast(it) })
        activeView?.showLoginStart()
    }

    override fun login(username: String?, password: String?) {
        if (isLogining.value != true) {
            isLogining.value = true
            info.value = "正在进行登录..."
            // login
            userModel.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isLogining.value = false }
                .doOnSuccess { settings.token.value = it }
                .doOnDispose { info.value = "登录已取消" }
                .subscribeBy(
                    onSuccess = {
                        info.value = "登录成功"
                        toastEvent.send("登录成功")
                        navigateHomePageSignal.send()
                    },
                    onError = { error ->
                        toastEvent.send("登录异常")
                        info.value = "登录异常：${error.message ?: "Unknown"}"
                    }
                ).disposeWhenCleared()
        }
    }

}