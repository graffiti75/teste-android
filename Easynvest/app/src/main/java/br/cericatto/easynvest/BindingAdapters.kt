package br.cericatto.easynvest

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

enum class EasyApiStatus { LOADING, ERROR, DONE }

@BindingAdapter("easyApiStatus")
fun bindStatus(statusImageView: ImageView, status: EasyApiStatus?) {
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

@BindingAdapter("easyEnabled")
fun bindEnabled(button: Button, enabled: Boolean) {
    val context = button.context
    if (enabled) {
        button.background = ContextCompat.getDrawable(context, R.drawable.border_round_ripple_purple__purple_background)
    } else {
        button.background = ContextCompat.getDrawable(context, R.drawable.border_round_ripple_grey__grey_background)
    }
}