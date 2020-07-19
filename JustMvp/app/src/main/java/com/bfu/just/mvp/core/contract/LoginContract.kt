package com.bfu.just.mvp.core.contract

import androidx.lifecycle.LiveData
import just.mvp.base.IPresenter
import just.mvp.base.IView

/**
 * MVP & MVVM
 *
 * MVP: 即 presenter 掌握一切业务逻辑，指挥 view 的更新，view 是被动的。业务逻辑分配 Presenter: 90%，View：10%
 * 优点：接口约定和隔离、View 层干净无业务逻辑，由于面向接口，mock View 或者 mock Presenter 非常容易，便于多人协同开发
 * 缺点：需要编写的接口类较多，View 层过于干净，随着业务增长 Presenter 会变得较为肿胀
 *
 * MVVM：即 viewModel 对 view 提供数据，view 自己决定数据如何组织展示。同时 view 指挥多个 viewModel 相互配合。业务逻辑分配 Presenter: 50%，View：50%
 * 优点：LiveData & ViewModel is so clever
 * 缺点：view 还需要承载一些业务逻辑，当遇到非数据传输型的应用场景时会很局限
 *
 * just-mvp：将 MVP 和 MVVM 的优点结合到一起。业务逻辑分配 Presenter: 70%，View：30%
 * Presenter 以实现接口的方式继承 ViewModel 的特性，配合插件生成模板代码文件，
 * View 承载一部分轻量的 UI 更新的业务逻辑，Presenter 专心于复杂业务逻辑。随着业务复杂度的增加，可以将其拆分到多个 Presenter 中，即一个 View 对应多个 Presenter
 * 业务比例：如果是数据展示型的界面，偏向 MVVM 多一点；如果是业务逻辑型的界面，偏向 MVP 多一点
 */
interface LoginContract {

    interface View : IView {

        fun showLoginStart()

        fun showLoginEnd()

        fun goHomePage()

    }

    interface Presenter : IPresenter<View> {

        /**
         * LiveData 的 MVVM 使用示例
         */
        val info: LiveData<String>

        fun login(username: String?, password: String?)
    }

}