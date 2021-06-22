package com.sun.unsplash03.screen.photo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.ItemPhotoBinding

class PhotoAdapter(private val onClickItem: (photo: Photo) -> Unit) :
    ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    fun updateData(data: MutableList<Photo>?) {
        data?.let {
            submitList(data.toMutableList())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = DataBindingUtil.inflate<ItemPhotoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_photo,
            parent,
            false
        )
        return PhotoViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {

        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }

    class PhotoViewHolder(
        private val binding: ItemPhotoBinding,
        private val onClickItem: (photo: Photo) -> Unit,
        private val itemViewModel: ItemPhotoViewModel = ItemPhotoViewModel(onClickItem)
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = itemViewModel
        }

        fun bind(photo: Photo?) {
            itemViewModel.setData(photo)
            binding.executePendingBindings()
        }
    }
}
