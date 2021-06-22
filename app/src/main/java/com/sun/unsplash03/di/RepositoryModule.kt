package com.sun.unsplash03.di

import com.sun.unsplash03.data.repository.PhotoRepository
import com.sun.unsplash03.data.repository.PhotoRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PhotoRepository> {
        PhotoRepositoryImpl(get())
    }
}
