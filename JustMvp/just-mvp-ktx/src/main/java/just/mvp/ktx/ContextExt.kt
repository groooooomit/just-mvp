package just.mvp.ktx

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "EXPERIMENTAL_FEATURE_WARNING")
inline class ToastLength internal constructor(internal val length: Int) {
    companion object {
        val SHORT = ToastLength(Toast.LENGTH_SHORT)
        val LONG = ToastLength(Toast.LENGTH_LONG)
    }
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "EXPERIMENTAL_FEATURE_WARNING")
inline class ToastGravity internal constructor(internal val gravity: Int) {
    companion object {
        val TOP = ToastGravity(Gravity.TOP)
        val CENTER = ToastGravity(Gravity.CENTER)
        val BOTTOM = ToastGravity(Gravity.BOTTOM)
    }
}

fun Context.toast(
    text: CharSequence,
    duration: ToastLength = ToastLength.SHORT,
    gravity: ToastGravity = ToastGravity.BOTTOM
) = Toast.makeText(this, text, duration.length)
    .apply { setGravity(gravity.gravity, xOffset, yOffset) }
    .show()

//
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "EXPERIMENTAL_FEATURE_WARNING")
inline class SnackLength internal constructor(internal val length: Int) {
    companion object {
        val SHORT = SnackLength(Snackbar.LENGTH_SHORT)
        val LONG = SnackLength(Snackbar.LENGTH_LONG)
        val INDEFINITE = SnackLength(Snackbar.LENGTH_INDEFINITE)
    }
}

private val actionStub: (View, Snackbar) -> Unit = { _, snackbar -> snackbar.dismiss() }

fun View.snack(
    text: CharSequence,
    duration: SnackLength = SnackLength.SHORT,
    actionText: CharSequence?,
    action: (View, Snackbar) -> Unit = actionStub
) = Snackbar.make(this, text, duration.length)
    .also { bar -> actionText?.apply { bar.setAction(this) { view -> action(view, bar) } } }
    .show()

fun Activity.snack(
    text: CharSequence,
    duration: SnackLength = SnackLength.SHORT,
    actionText: CharSequence?,
    action: (View, Snackbar) -> Unit = actionStub
) = window?.decorView?.snack(text, duration, actionText, action)

fun Fragment.snack(
    text: CharSequence,
    duration: SnackLength = SnackLength.SHORT,
    actionText: CharSequence?,
    action: (View, Snackbar) -> Unit = actionStub
) = view?.snack(text, duration, actionText, action)
