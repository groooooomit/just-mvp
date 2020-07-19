package just.mvp.ktx

import just.mvp.base.IPresenter
import just.mvp.base.IView
import just.mvp.base.Presenters

inline fun <V : IView, reified P : IPresenter<V>> V.bind(noinline creator: () -> P): P =
    Presenters.bind(this, P::class.java, creator)