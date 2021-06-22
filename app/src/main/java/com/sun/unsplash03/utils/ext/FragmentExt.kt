package com.sun.unsplash03.utils.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false,
    tag: String = fragment::class.java.simpleName) {
    activity?.supportFragmentManager?.apply {
        beginTransaction()
            .addToBackStack(tag)
            .replace(containerId, fragment, tag)
            .commit()
    }
}
