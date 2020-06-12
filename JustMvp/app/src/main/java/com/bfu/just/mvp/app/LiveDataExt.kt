package com.bfu.just.mvp.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <X, Y> LiveData<X>.map(block: (X) -> Y): LiveData<Y> = Transformations.map(this, block)

fun <X, Y> LiveData<X>.switchMap(block: (X) -> LiveData<Y>): LiveData<Y> =
    Transformations.switchMap(this, block)

