package com.sun.unsplash03.screen.photo_favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sun.unsplash03.R
import com.sun.unsplash03.data.source.local.entity.PhotoEntity
import com.sun.unsplash03.databinding.ItemFavoritePhotoBinding

class FavoritePhotoAdapter(private val onClickItem: (photo: PhotoEntity) -> Unit) :
    ListAdapter<PhotoEntity, FavoritePhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    fun updateData(data: MutableList<PhotoEntity>?) {
        data?.let {
            submitList(data.toMutableList())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = DataBindingUtil.inflate<ItemFavoritePhotoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite_photo,
            parent,
            false
        )
        return PhotoViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PhotoDiffCallback : DiffUtil.ItemCallback<PhotoEntity>() {

        override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity) =
            oldItem == newItem
    }

    class PhotoViewHolder(
        private val binding: ItemFavoritePhotoBinding,
        private val onClickItem: (photo: PhotoEntity) -> Unit,
        private val itemViewModel: ItemPhotoFavoriteViewModel = ItemPhotoFavoriteViewModel(
            onClickItem
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = itemViewModel
        }

        fun bind(photo: PhotoEntity?) {
            itemViewModel.setData(photo)
            binding.executePendingBindings()
        }
    }
}
