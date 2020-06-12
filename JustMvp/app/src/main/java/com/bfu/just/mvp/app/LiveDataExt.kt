package com.bfu.just.mvp.app

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <X, Y> LiveData<X>.map(block: (X) -> Y): LiveData<Y> = Transformations.map(this, block)

fun <X, Y> LiveData<X>.switchMap(block: (X) -> LiveData<Y>): LiveData<Y> = Transformations.switchMap(this, block)

fun <T> LiveData<T>.observeBy(owner: LifecycleOwner, block: (T) -> Unit): Unit = this.observe(owner, Observer(block))

