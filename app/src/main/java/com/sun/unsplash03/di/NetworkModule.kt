package com.sun.unsplash03.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sun.unsplash03.data.source.remote.ApiService
import com.sun.unsplash03.data.source.remote.ServiceGenerator
import com.sun.unsplash03.data.source.remote.interceptor.InterceptorImpl
import com.sun.unsplash03.utils.Constants
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

private fun buildHttpLog() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

val networkModule = module {
    single<Gson> {
        GsonBuilder().create()
    }

    single<Interceptor> {
        InterceptorImpl(application = get())
    }

    single {
        ServiceGenerator.generate(
            baseUrl = Constants.BASE_URL,
            serviceClass = ApiService::class.java,
            gson = get(),
            interceptors = listOf(buildHttpLog(), get())
        )
    }
}
