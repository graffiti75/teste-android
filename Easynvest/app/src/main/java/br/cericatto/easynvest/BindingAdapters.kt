package br.cericatto.easynvest

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

enum class EasyApiStatus { LOADING, ERROR, DONE }

@BindingAdapter("easyApiStatus")
fun bindStatus(statusImageView: ImageView, status: EasyApiStatus?) {
    when (status) {
        EasyApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        EasyApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        EasyApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}