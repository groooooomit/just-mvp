# just-mvp
* 一个轻量灵活小清新的 [android mvp](https://github.com/android/architecture-samples/tree/todo-mvp) 模式框架，基于 Java8、[androidx](https://developer.android.google.cn/jetpack/androidx)、[ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 和 [Lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle)；
* Presenter 以 [ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 的形式被创建和管理，在发生屏幕旋转等配置更改后 Presenter 以及它所持有的数据和业务 Model 将继续存在，无需重新初始化，大大地提高了程序响应速度；
* Presenter 通过 [Lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle) 感知 View 的生命周期，配合 ViewModel 实现了 View 和 Presenter 的充分解耦，当 View 即将被 destroy 时 Presenter 及时主动地解除对 View 持有的引用，有效地避免了占用大量资源的 View 出现内存泄漏（后面有详细的生命周期方法说明）；
* api 设计即友好又简洁，尤其是针对 kotlin 开发者，同时内置一些常用方法提升开发效率；
* 配套一个专属的一键生成 MVP 模板代码和布局的 AndroidStudio 插件 [Just Mvp Generator](https://github.com/groooooomit/just-mvp-plugin) ，免于繁琐的样板代码编写，为你的生产力加分。

## 添加到你的项目
```gradle
implementation 'com.bfu:just-mvp:1.1.6'
```
## 开始使用
> 以用户登录的场景为例  
* [LoginContract](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/core/contract/LoginContract.kt)
```kotlin
interface LoginContract {

    interface View : IView {
        ...
    }

    interface Presenter : IPresenter<View> {
        fun login(username: String?, password: String?)
    }
}
```
* [LoginPresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/core/presenter/LoginPresenter.kt)
```kotlin
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String?, password: String?) {
        ...
    }
}
```
* [LoginActivity](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/ui/activity/LoginActivity.kt) 或 [LoginFragment](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/ui/fragment/LoginFragment.kt)
```kotlin
class LoginActivity : PresenterActivity<LoginPresenter>(), LoginContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener {
            ...
            presenter.login(usernameStr, passwordStr)
        }
    }
}
```
* :warning: 避免在界面控件初始化完成时立即访问 Presenter，为了确保 Presenter 中对 View 的访问的正确性（控件不为null），Presenter 的初始化是在控件绑定之后才进行的，应该将业务逻辑搬到 Presenter 的 **afterViewCreate** 中；  
* :warning: 不要在 View 的 onDestroy 方法中访问 Presenter。Presenter 由 ViewModel 承载，onDestroy 回调前 ViewModel 已经被回收了，应该将逻辑转移到 Presenter 的 **beforeViewDestroy** 中；

## [BasePresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/BasePresenter.java) 设计思路
* [IViewModel](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/base/IViewModel.java) 通过接口组合的方式实现 ViewModel；  
* [IPresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/base/IPresenter.java) 继承自 IViewModel 使得 presenter 拥有 ViewModel 的特性；  
* [UiActionExecutor](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/uirun/UiActionExecutor.java) 使得 presenter 能够通过 Handler 在异步场景中便捷地更新 UI；  
* [PresenterLifecycle](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/lifecycle/PresenterLifecycle.java) 定义了 presenter 的生命周期方法；  
* [AbstractPresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/base/AbstractPresenter.java) 聚合了 presenter 的 api；  
* [BasePresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/BasePresenter.java) 是 AbstractPresenter 的具体实现。  

![BasePresenter 类图](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/BasePresenter.png)  

## [BasePresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/BasePresenter.java) 生命周期
* presenter 生命周期一共 10 个方法：onInitialize -> onAttachView -> afterViewCreate -> afterViewStart -> afterViewResume -> beforeViewPause -> beforeViewStop -> beforeViewDestroy -> onDetachView -> onCleared；  
* onInitialize 和 onCleared 对应 ViewModel 的生命周期；  
* onAttachView 和 onDetachView 在 presenter 持有和解除对 view 的引用时触发；  
* afterViewCreate ... beforeViewDestroy 一一对应 view 的生命周期。  
* 打开和关闭一个 view 时，绑定了 view 的 presenter 生命周期执行流程：  

![打开和关闭 view 时 presenter 的生命周期流程](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/open_close_page.gif "打开和关闭 view 时 presenter 的生命周期流程")  

* 当发生屏幕旋转 view 被重建时，绑定了 view 的 presenter 生命周期执行流程：  

![屏幕旋转 view 被重建时 presenter 的生命周期流程](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/rotate_page.gif "屏幕旋转 view 被重建时 presenter 的生命周期流程")
