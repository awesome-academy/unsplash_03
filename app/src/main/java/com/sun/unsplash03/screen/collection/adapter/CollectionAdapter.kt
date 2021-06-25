package com.sun.unsplash03.screen.collection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.databinding.ItemCollectionBinding

class CollectionAdapter(private val onItemClick: (collection: Collection) -> Unit) :
    ListAdapter<Collection, CollectionAdapter.CollectionViewHolder>(CollectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding = DataBindingUtil.inflate<ItemCollectionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_collection,
            parent,
            false
        )
        return CollectionViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CollectionDiffCallback() : DiffUtil.ItemCallback<Collection>() {

        override fun areItemsTheSame(oldItem: Collection, newItem: Collection) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection) =
            oldItem == newItem
    }

    class CollectionViewHolder(
        private val binding: ItemCollectionBinding,
        private val onItemClick: ((collection: Collection) -> Unit),
        private val itemViewModel: ItemCollectionViewModel = ItemCollectionViewModel(onItemClick)
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = itemViewModel
        }

        fun bind(collection: Collection?) {
            itemViewModel.setData(collection)
            binding.executePendingBindings()
        }
    }
}
