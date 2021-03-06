package br.cericatto.easynvest.utils

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun initViewAnimation(view: View, animation: Animation) {
    view.startAnimation(animation)
}

fun AppCompatActivity.initViewAnimation(viewId: Int, animation: Animation) {
    findViewById<View>(viewId).startAnimation(animation)
}

fun Context.anim(animationId: Int): Animation {
    return AnimationUtils.loadAnimation(this, animationId)
}

fun AppCompatActivity.hideKeyboardOnStartup() {
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
}

fun Context.hideKeyboard(view: View) {
    if (view != null) {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Context.showKeyboard(view: View) {
    if (view.requestFocus()) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}