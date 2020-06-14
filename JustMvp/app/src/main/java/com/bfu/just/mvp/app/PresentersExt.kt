package com.bfu.just.mvp.app

import just.mvp.base.IPresenter
import just.mvp.base.IView
import just.mvp.base.Presenters

fun <V : IView, P : IPresenter<V>> V.bind(creator: () -> P): P = Presenters.bind(this, creator)

fun <V : IView, P : IPresenter<V>> V.bind(key: String, creator: () -> P): P = Presenters.bind(this, key, creator)