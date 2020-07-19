package just.mvp.ktx

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import just.mvp.ext.livedata.EventLiveData
import just.mvp.ext.livedata.EventObserver
import just.mvp.ext.livedata.SignalLiveData
import just.mvp.ext.livedata.SignalObserver

@MainThread
inline fun <X, Y> LiveData<X>.map(crossinline block: (X) -> Y): LiveData<Y> =
    Transformations.map(this) { block(it) }

@MainThread
inline fun <X, Y> LiveData<X>.switchMap(crossinline block: (X) -> LiveData<Y>): LiveData<Y> =
    Transformations.switchMap(this) { block(it) }

@MainThread
inline fun <T> LiveData<T>.observeData(
    owner: LifecycleOwner,
    crossinline onChange: (T) -> Unit
) = this.observe(owner, Observer { onChange(it) })

@MainThread
inline fun <T> LiveData<T>.observeData(
    fragment: Fragment,
    crossinline onChange: (T) -> Unit
) = this.observe(fragment.viewLifecycleOwner, Observer { onChange(it) })

@MainThread
inline fun <T> EventLiveData<T>.observeEvent(
    owner: LifecycleOwner,
    crossinline onChange: (T) -> Unit
) = this.observe(owner, EventObserver { onChange(it) })

@MainThread
inline fun <T> EventLiveData<T>.observeEvent(
    fragment: Fragment,
    crossinline onChange: (T) -> Unit
) = this.observe(fragment.viewLifecycleOwner, EventObserver { onChange(it) })

@MainThread
inline fun SignalLiveData.observeSignal(
    owner: LifecycleOwner,
    crossinline onChange: () -> Unit
) = this.observe(owner, SignalObserver { onChange() })

@MainThread
inline fun SignalLiveData.observeSignal(
    fragment: Fragment,
    crossinline onChange: () -> Unit
) = this.observe(fragment.viewLifecycleOwner, SignalObserver { onChange() })


