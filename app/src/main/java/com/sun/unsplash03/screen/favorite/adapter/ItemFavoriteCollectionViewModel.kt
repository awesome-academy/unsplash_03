package com.sun.unsplash03.screen.favorite.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sun.unsplash03.BR
import com.sun.unsplash03.data.source.local.entity.CollectionEntity

class ItemFavoriteCollectionViewModel(
    private val onItemClick: (collection: CollectionEntity) -> Unit,
    private val onDeleteClick: (collection: CollectionEntity) -> Unit,
    private val onEditClick: (collection: CollectionEntity) -> Unit
) : BaseObservable() {

    @Bindable
    var collection: CollectionEntity? = null

    fun setData(data: CollectionEntity?) {
        data?.let {
            collection = it
            notifyPropertyChanged(BR.collection)
        }
    }

    fun onItemClicked(view: View) {
        collection?.let {
            onItemClick(it)
        }
    }

    fun onDeleteClicked(view: View) {
        collection?.let {
            onDeleteClick(it)
        }
    }

    fun onEditClicked(view: View) {
        collection?.let {
            onEditClick(it)
        }
    }
}
