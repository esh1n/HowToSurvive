package com.esh1n.guidtoarchapp.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

object UiUtils {
    fun getCategoryImage(context: Context, iconId: String, prefix: String = "ic_"): Int {
        val res = "${prefix}category_$iconId"
        return context.resources.getIdentifier(res, "drawable", context.packageName)
    }

    fun getImageByPath(context: Context, iconId: String): Int {
        return context.resources.getIdentifier(iconId, "drawable", context.packageName)
    }

    fun View.click(action: (() -> Unit)?) {
        setOnClickListener { action?.invoke() }
    }

    inline fun <reified T : RecyclerView.Adapter<out RecyclerView.ViewHolder>> RecyclerView.adapter() =
        adapter as T

    class SpannableLinkInfo(private val text: String) {

        fun setTo(textView: TextView, clickAction: () -> Unit) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.setText(
                getSpannable(textView.context, clickAction),
                TextView.BufferType.SPANNABLE
            )
        }

        private fun getSpannable(context: Context, clickAction: () -> Unit): SpannableString {
            val startPos = 0
            val endPos = text.length
            val spannableString = SpannableString(text)

            if (startPos < 0 || endPos < 0) {
                return SpannableString(text)
            }

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    clickAction()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }
            spannableString.setSpan(
                clickableSpan,
                startPos,
                endPos,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannableString
        }
    }
}

fun Activity.openBrowser(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes messageResId: Int) {
    showToast(requireContext().getString(messageResId))
}

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

fun Context.toPixelsFromResource(@DimenRes dimenRes: Int) =
    resources.getDimensionPixelSize(dimenRes)

fun Fragment.toPixelsFromResource(@DimenRes dimenRes: Int) =
    resources.getDimensionPixelSize(dimenRes)

fun Context.getDimensionFromAttribute(attrId: Int): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrId))
    return typedArray.getDimensionPixelSize(0, 0).apply { typedArray.recycle() }
}

fun Context.toPixels(dps: Int) = (dps * density).toInt()

val Context.density
    get() = resources.displayMetrics.density

@ColorInt
fun Context.getIntColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

@ColorInt
fun Context.getColorFromAttribute(attrId: Int): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrId))
    return typedArray.getColor(0, 0).apply { typedArray.recycle() }
}

