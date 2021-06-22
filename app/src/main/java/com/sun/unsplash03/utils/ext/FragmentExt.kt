package com.sun.unsplash03.utils.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun Fragment.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false,
    tag: String = fragment::class.java.simpleName) {
    activity?.supportFragmentManager?.apply {
        beginTransaction()
            .addToBackStack(tag)
            .add(containerId, fragment, tag)
            .commit()
    }
}
