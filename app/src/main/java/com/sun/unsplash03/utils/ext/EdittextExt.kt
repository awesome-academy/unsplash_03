package com.sun.unsplash03.utils.ext

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.showKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun EditText.hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    clearFocus()
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.moveCursorToEnd() {
    setSelection(this.text.toString().length)
}
