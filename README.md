# just-mvp
一个轻量灵活小清新的 android mvp 模式框架，基于 Java8、androidx、ViewModel 和 Lifecycle。

## 在 AndroidStudio 项目中使用
```gradle
implementation 'com.bfu:just-mvp:1.1.6'
```
## 特点
* Presenter 以 [ViewModel](https://developer.android.google.cn/topic/libraries/architecture/viewmodel) 的形式被创建和管理，在发生屏幕旋转等配置更改后 Presenter 以及它所持有的数据和业务 Model 将继续存在，无需重新初始化，大大地提高了程序响应速度。
* Presenter 通过 [Lifecycle](https://developer.android.google.cn/topic/libraries/architecture/lifecycle) 感知 View 的生命周期，配合 ViewModel 实现了 View 和 Presenter 的充分解耦，当 View 即将被 destroy 时 Presenter 及时主动地解除对 View 持有的引用，有效地避免了占用大量资源的 View 出现内存泄漏（后面有详细的生命周期方法说明）
* api 设计即友好又简洁，尤其是针对 kotlin 开发者，同时内置一些常用方法提升开发效率。
## 开始使用
> 以用户登录的场景为例  
> 国际惯例首先要编写 Contract 类，如下：  

```kotlin
interface LoginContract {

    interface View : IView

    interface Presenter : IPresenter<View> {

        fun login(username: String?, password: String?)
    }

}
```
> Presenter
```kotlin
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun login(username: String?, password: String?) {
        ...
    }
}
```
> View
```kotlin
class LoginActivity : PresenterActivity<LoginPresenter>(), LoginContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener {
            val usernameStr = username.text?.toString()
            val passwordStr = password.text?.toString()
            presenter.login(usernameStr, passwordStr)
        }
    }
}
```
* :warning:注意1：不要在 View 的 onDestroy 方法中访问 Presenter。Presenter 由 ViewModel 承载，onDestroy 回调前 ViewModel 已经被回收了，因为将逻辑转移到 Presenter 的 beforeViewDestroy 中
* :warning:注意2：避免在界面控件初始化完成时立即访问 Presenter，为了确保 Presenter 中对 View 的访问的正确性（控件不为null），Presenter 的初始化是在控件绑定之后才进行的，应该将业务逻辑搬到 Presenter 的 afterViewCreate 中 
## 开始使用
关于 Fragment 中 Presenter 的绑定时机选择以及最佳实践的思考，为了 View 对 Presenter 透明化，即 View 的行为表现需要一致
第一次学习 MVP 模式的项目是 [Google Mvp Sample](https://github.com/android/architecture-samples) 

![一键生成 MVP 模板代码](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/open_close_page.gif) ![一键生成 MVP 模板代码](https://raw.githubusercontent.com/groooooomit/just-mvp/master/screenshots/rotate_page.gif)
