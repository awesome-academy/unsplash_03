package com.sun.unsplash03.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sun.unsplash03.utils.scheduler.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val exception = MutableLiveData<Exception>()

    protected fun <T> launchTaskSync(
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) = viewModelScope.launch {
        isLoading.postValue(true)
        when (val asynchronousTasks = onRequest(this)) {
            is DataResult.Success -> onSuccess(asynchronousTasks.data)
            is DataResult.Error -> onError(asynchronousTasks.exception)
        }
        isLoading.postValue(false)
    }

    protected fun <T> viewModelScope(
        liveData: MutableLiveData<T>?,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null,
        onRequest: suspend CoroutineScope.() -> DataResult<T>
    ) {
        viewModelScope.launch {
            isLoading.postValue(true)
            when (val asynchronousTasks = onRequest(this)) {
                is DataResult.Success -> {
                    onSuccess?.invoke(asynchronousTasks.data) ?: kotlin.run {
                        liveData?.value = asynchronousTasks.data
                    }
                }
                is DataResult.Error -> {
                    onError?.invoke(asynchronousTasks.exception) ?: kotlin.run {
                        exception.value = asynchronousTasks.exception
                    }
                }
            }
            isLoading.postValue(false)
        }
    }
}
