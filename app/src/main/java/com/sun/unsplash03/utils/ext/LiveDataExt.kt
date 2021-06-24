package com.sun.unsplash03.utils.ext

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: MutableList<T>) {
    val value = this.value ?: mutableListOf()
    value.addAll(item)
    this.postValue(value)
}
