package com.bfu.just.mvp.core.contract

import androidx.lifecycle.LiveData
import just.mvp.ext.livedata.EventLiveData
import just.mvp.ext.livedata.SignalLiveData

/**
 * 输入：触发事件、输入数据
 * 输出：界面事件、展示数据
 * todo 数据展示形页面使用 MVVM，业务交互复杂型使用 MVP
 * todo Room (缓存方案)-> LiveData -> Paging -> modularization
 * todo 缓存方案：
 */
interface LoginViewModel {

    /**
     * 提示信息
     */
    val info: LiveData<String>

    /**
     * 当前登录状态
     */
    val isLogining: LiveData<Boolean>

    /**
     * 弹出 toast
     */
    val toast: EventLiveData<String>

    /**
     * 打开首页
     */
    val goHomePage: SignalLiveData

    /**
     * 执行登录逻辑
     */
    fun login(username: String?, password: String?)

}