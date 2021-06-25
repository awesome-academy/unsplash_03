package com.sun.unsplash03.di

import com.sun.unsplash03.screen.collection.CollectionViewModel
import com.sun.unsplash03.screen.collection_photo.CollectionPhotoViewModel
import com.sun.unsplash03.screen.detail.DetailViewModel
import com.sun.unsplash03.screen.favorite.FavoriteViewModel
import com.sun.unsplash03.screen.home.HomeViewModel
import com.sun.unsplash03.screen.main.MainViewModel
import com.sun.unsplash03.screen.photo.PhotoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
    viewModel { PhotoViewModel(get()) }
    viewModel { CollectionViewModel(get()) }
    viewModel { FavoriteViewModel() }
    viewModel { DetailViewModel() }
    viewModel { CollectionPhotoViewModel(get()) }
}
