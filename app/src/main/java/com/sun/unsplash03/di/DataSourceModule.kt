package com.sun.unsplash03.di

import androidx.room.Room
import com.sun.unsplash03.data.source.PhotoDataSource
import com.sun.unsplash03.data.source.PhotoLocalImpl
import com.sun.unsplash03.data.source.local.AppDatabase
import com.sun.unsplash03.data.source.local.dao.CollectionDAO
import com.sun.unsplash03.data.source.remote.PhotoRemoteImpl
import com.sun.unsplash03.utils.Constants
import org.koin.dsl.module

val dataSourceModule = module {
    single<PhotoDataSource.Remote> { PhotoRemoteImpl(get()) }

    single<PhotoDataSource.Local> { PhotoLocalImpl(get()) }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration().build()
    }

    fun provideCollectionDao(db: AppDatabase): CollectionDAO = db.getCollectionDao()

    single {
        provideCollectionDao(get())
    }
}
