package com.bfu.just.mvp.app

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import java.util.*

// 为了代码隔离，不要在 app 模块中写业务代码
// app  不包含任何业务逻辑，implementation 所有 component，通过 Initializer 完成 component 的注册
// component-a // 要单独测试时，只需要在自己的分支维护一个类似 app module 的 launcher 即可，即该 component 代码有两个 branch，一个时 master，一个是 test branch
// component-b
// component-c
// component-d
// core-api // component 彼此之间不能相互依赖，他们所提供的服务都以接口的形式定义在这其中。component-a 想要使用 component-b 的服务，就需要通过 core-api 来使用
// lib-a
// lib-b
// lib-c
// lib-d
// lib-e

// @Dependencies{A::class, B::class, C::class ,D:: class}
class XCrashComponentInitializer : Initializer<XComponent> {
// 改造，两种 viewModel，一种是不持有 View 引用的普通 ViewModel，一种是持有 View 引用的复杂 ViewModel
// 持有 View 的引用主要是为了获取 View 的资源，例如 context、activity 等，这种初始化的数据需要在 view destroy 的时候 release 掉
// 准确的说是持有 LifeCycleOwner 的引用，onAttach(owner:LifecycleOwner), onDetach(owner: LifecycleOwner)
// 持有 context 以满足复杂业务场景的初始化方法，仅此而已
    fun onCreate(context: Context, dependencies: Map<Class<*>, Any>) {
        val any = dependencies[XComponent::class.java]
        val an = dependencies[XComponent::class.java]
        val anb = dependencies[XComponent::class.java]
        ServiceLoader.load(Initializer::class.java, Initializer::class.java.classLoader)
        val xComponent = AppInitializer.getInstance(context).initializeComponent(XCrashComponentInitializer::class.java)
    }

    // just-start-up
    override fun create(context: Context): XComponent {
        ServiceLoader.load(Initializer::class.java, Initializer::class.java.classLoader)
        val xComponent = AppInitializer.getInstance(context)
            .initializeComponent(XCrashComponentInitializer::class.java)
        return object : XComponent {
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf()
}