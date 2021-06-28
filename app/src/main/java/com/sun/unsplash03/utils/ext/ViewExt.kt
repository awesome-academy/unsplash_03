package com.sun.unsplash03.utils.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.showKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun View.hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    clearFocus()
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}
