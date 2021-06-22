package com.sun.unsplash03.di

import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.data.source.remote.PhotoRemoteImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<PhotoDataSource.Remote> {
        PhotoRemoteImpl(get())
    }
}
