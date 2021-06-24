package com.sun.unsplash03.screen.collection.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sun.unsplash03.BR
import com.sun.unsplash03.data.model.Collection

class ItemCollectionViewModel(private val onClickItem: (collection: Collection) -> Unit) :
    BaseObservable() {

    @Bindable
    var collection: Collection? = null

    fun setData(data: Collection?) {
        data?.let {
            collection = it
            notifyPropertyChanged(BR.collection)
        }
    }

    fun onItemClicked(view: View) {
        collection?.let {
            onClickItem(it)
        }
    }
}
