package com.bfu.just.mvp.core.presenter

import androidx.lifecycle.MutableLiveData
import com.bfu.just.mvp.app.App
import com.bfu.just.mvp.app.Settings
import com.bfu.just.mvp.app.observeBy
import com.bfu.just.mvp.core.contract.LoginContract
import com.bfu.just.mvp.core.model.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 *  [LoginPresenter] 的 LiveData 版本
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
    private val info = MutableLiveData<String>()

    /**
     * 全局属性
     */
    private lateinit var settings: Settings

    override fun onInitialize() {
        settings = (application as App).settings
    }

    override fun onAttachView(view: LoginContract.View) {
        // liveData 安全及时地更新 view
        isLogining.observeBy(view) { if (it) view.showLoginStart() else view.showLoginEnd() }
        info.observeBy(view) { view.showInfo(it) }
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
                .subscribeBy(
                    onSuccess = {
                        info.value = "登录成功"
                        view?.goHomePage()
                    },
                    onError = { error ->
                        info.value = "登录异常：${error.message ?: "Unknown"}"
                    }
                ).attach()
        }
    }

}