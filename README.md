# just-mvp
* 一个轻量灵活小清新的 [android mvp](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) 模式框架，基于 Java8、[androidx](https://developer.android.google.cn/jetpack/androidx)、[ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 和 [Lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle)，api level 14+ ；
* presenter 以 [ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 的形式被创建和管理，在发生屏幕旋转等配置更改后 presenter 以及它所持有的数据和业务 Model 将继续存在，无需重新初始化，大大地提高了程序响应速度。由于 presenter 拥有 ViewModel 的特性，可以直接持有 [LiveData](https://developer.android.google.cn/topic/libraries/architecture/livedata)；
* presenter 通过 [Lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle) 感知 View 的生命周期，配合 ViewModel 实现了 view 和 presenter 的充分解耦，当 view 即将被 destroy 时 presenter 及时主动地解除对 view 持有的引用，有效地避免了占用大量资源的 view 出现内存泄漏（后面有详细的生命周期方法说明）；
* api 设计即友好又简洁，尤其是针对 kotlin 开发者，同时内置一些常用方法提升开发效率；
* 配套一个专属的一键生成 MVP 模板代码和布局的 AndroidStudio 插件 [Just Mvp Generator](https://github.com/groooooomit/just-mvp-plugin) ，免于繁琐的样板代码编写，为你的生产力加分。

## 添加到你的项目
```gradle
implementation 'com.bfu:just-mvp:1.1.8'     // 添加 just-mvp
implementation 'com.bfu:just-mvp-ktx:1.1.8' // 针对 kotlin 的扩展（可选）
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
* 也可以不继承自 [PresenterActivity](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/PresenterActivity.java) 或 [PresenterFragment](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/PresenterFragment.java) 单独使用，且可以做到一个 View 绑定复用多个 Presenter，例如 [Login3Fragment](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/ui/fragment/Login3Fragment.kt) 或 [Login3Activity](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/ui/activity/Login3Activity.java)
```kotlin
class Login3Fragment : Fragment(R.layout.fragment_login), LoginContract.View {

    /* 1. 声明 Presenter. */
    private lateinit var presenter: LoginContract.Presenter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.isClickable = true
        login.setOnClickListener {

            /* 用户名： user， 密码： 123456 */
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()

            /* 3. Presenter 执行业务逻辑. */
            presenter.login(usernameStr, passwordStr)
        }
        ...
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /* 2. View 和 Presenter 绑定. */
        presenter = bind { LoginPresenter() }        
        ...
    }

}
```
:warning: 避免在界面控件初始化完成时立即访问 Presenter，为了确保 Presenter 中对 View 的访问的正确性（控件不为null），Presenter 的初始化是在控件绑定之后才进行的，应该将业务逻辑搬到 Presenter 的 **afterViewCreate** 中；  
:warning: 不要在 View 的 onDestroy 方法中访问 Presenter。Presenter 由 ViewModel 承载，onDestroy 回调前 ViewModel 已经被回收了，应该将逻辑转移到 Presenter 的 **beforeViewDestroy** 中；

## 一键生成模板代码
> 本框架专属神器 **[Just Mvp Generator](https://github.com/groooooomit/just-mvp-plugin)** ，可以一键生成繁琐的 Contract、Presenter、Activity/Fragment 样板代码以及布局文件，点击链接查看插件详情。  

![Just Mvp Generator](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/just-mvp-generator-plugin.png "Just Mvp Generator")    

## [BasePresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/BasePresenter.java) 设计思路
* [IViewModel](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/base/IViewModel.java) 通过接口组合的方式实现 ViewModel；  
* [IPresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/base/IPresenter.java) 继承自 IViewModel 使得 presenter 拥有 ViewModel 的特性；  
* [UiActionExecutor](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/uirun/UiActionExecutor.java) 使得 presenter 能够通过 Handler 在异步场景中便捷地更新 UI；  
* [PresenterLifecycle](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/lifecycle/PresenterLifecycle.java) 定义了 presenter 的生命周期方法；  
* [AbstractPresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/base/AbstractPresenter.java) 聚合了 presenter 的 api；  
* [BasePresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/BasePresenter.java) 是 AbstractPresenter 的具体实现。  

![BasePresenter 类图](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/BasePresenter.png "BasePresenter 类图")  

## [BasePresenter](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/just-mvp/src/main/java/just/mvp/BasePresenter.java) 生命周期
* presenter 生命周期一共 10 个方法：onInitialize -> onAttachView -> afterViewCreate -> afterViewStart -> afterViewResume -> beforeViewPause -> beforeViewStop -> beforeViewDestroy -> onDetachView -> onCleared；  
* onInitialize 和 onCleared 对应 ViewModel 的生命周期；  
* onAttachView 和 onDetachView 在 presenter 持有和解除对 view 的引用时触发；  
* afterViewCreate ... beforeViewDestroy 分別对应 view 的生命周期。  
* 打开和关闭一个 view 时，绑定了 view 的 presenter 生命周期执行流程：  

    ![打开和关闭 view 时 presenter 的生命周期流程](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/open_close_page.gif "打开和关闭 view 时 presenter 的生命周期流程")  

* 当发生屏幕旋转 view 被销毁又重建时，绑定了 view 的 presenter 生命周期执行流程：  

    ![屏幕旋转 view 被销毁又重建时 presenter 的生命周期流程](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/rotate_page.gif "屏幕旋转 view 被销毁又重建时 presenter 的生命周期流程")  

    > 可以看到由于使用了 ViewModel，presenter 并没有跟随 view 一同被销毁又重建，于是 view 重建后能很快恢复之前的状态。如下[示例](https://github.com/groooooomit/just-mvp/blob/master/JustMvp/app/src/main/java/com/bfu/just/mvp/core/presenter/LoginPresenter.kt)，屏幕多次旋转，view 多次销毁又重建但是并没有对登录逻辑造成影响，presenter 及时地 detach view 也避免造成 view 的内存泄漏。  

    ![用户登录](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/screen_rotate.gif "用户登录")  
   
## 框架细节参见项目源码  


```
Less is more.
```
