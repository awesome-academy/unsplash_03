package com.sun.unsplash03.utils.base

import com.sun.unsplash03.utils.scheduler.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository {

    protected suspend fun <R> withResultContext(
        context: CoroutineContext = Dispatchers.IO,
        requestBlock: suspend CoroutineScope.() -> R,
        errorBlock: (suspend CoroutineScope.(Exception) -> DataResult.Error)? = null
    ): DataResult<R> = withContext(context) {
        return@withContext try {
            DataResult.Success(requestBlock())
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext errorBlock?.invoke(this, e)
                ?: DataResult.Error(e)
        }
    }

    protected suspend fun <R> withResultContext(
        context: CoroutineContext = Dispatchers.IO,
        requestBlock: suspend CoroutineScope.() -> R
    ): DataResult<R> = withResultContext(context, requestBlock, null)
}