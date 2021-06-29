package com.sun.unsplash03.screen.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sun.unsplash03.R
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.databinding.ItemFavoriteCollectionBinding

class FavoriteCollectionAdapter(
    private val onItemClick: (collection: CollectionEntity) -> Unit,
    private val onDeleteClick: (collection: CollectionEntity) -> Unit,
    private val onEditClick: (collection: CollectionEntity) -> Unit
) : ListAdapter<CollectionEntity, FavoriteCollectionAdapter.FavoriteCollectionViewHolder>(
    CollectionDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCollectionViewHolder {
        val binding = DataBindingUtil.inflate<ItemFavoriteCollectionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite_collection,
            parent,
            false
        )
        return FavoriteCollectionViewHolder(binding, onItemClick, onDeleteClick, onEditClick)
    }

    override fun onBindViewHolder(holder: FavoriteCollectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CollectionDiffCallback() : DiffUtil.ItemCallback<CollectionEntity>() {

        override fun areItemsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CollectionEntity, newItem: CollectionEntity) =
            oldItem == newItem
    }

    class FavoriteCollectionViewHolder(
        private val binding: ItemFavoriteCollectionBinding,
        private val onItemClick: (collection: CollectionEntity) -> Unit,
        private val onDeleteClick: (collection: CollectionEntity) -> Unit,
        private val onEditClick: (collection: CollectionEntity) -> Unit,
        private val itemViewModel: ItemFavoriteCollectionViewModel = ItemFavoriteCollectionViewModel(
            onItemClick,
            onDeleteClick,
            onEditClick
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = itemViewModel
        }

        fun bind(collection: CollectionEntity?) {
            itemViewModel.setData(collection)
            binding.executePendingBindings()
        }
    }
}
