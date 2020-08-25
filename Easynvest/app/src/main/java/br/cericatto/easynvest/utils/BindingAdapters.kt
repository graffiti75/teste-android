package br.cericatto.easynvest.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import br.cericatto.easynvest.R

enum class EasyApiStatus { LOADING, ERROR, DONE }

@BindingAdapter("easyApiStatus")
fun bindEasyApiStatus(statusImageView: ImageView, status: EasyApiStatus?) {
    when (status) {
        EasyApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        EasyApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        EasyApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("easyButtonEnabled")
fun bindButtonEnabled(button: Button, enabled: Boolean) {
    val context = button.context
    var backgroundId = R.drawable.border_round_ripple_grey__grey_background
    if (enabled) backgroundId = R.drawable.border_round_ripple_purple__purple_background
    button.background = ContextCompat.getDrawable(context, backgroundId)
    button.isEnabled = enabled
}

@BindingAdapter("easyColoredTextView")
fun bindColoredTextView(textView: TextView, suffix: String) {
    val context = textView.context
    val text = context.getString(R.string.total_investment__value)
    val split = text.split("$")
    val start = text.indexOf("$", 0)

    val outputText = "${split[0]}$$suffix"
    val spannable = SpannableString(outputText)
    val color = ContextCompat.getColor(context, R.color.teal_400)
    spannable.setSpan(
        ForegroundColorSpan(color),
        start - 1, outputText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.text = spannable
}