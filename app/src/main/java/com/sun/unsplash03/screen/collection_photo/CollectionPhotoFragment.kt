package com.sun.unsplash03.screen.collection_photo

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.FragmentCollectionPhotoBinding
import com.sun.unsplash03.screen.detail.DetailFragment
import com.sun.unsplash03.screen.photo.adapter.PhotoAdapter
import com.sun.unsplash03.utils.base.BaseFragment
import com.sun.unsplash03.utils.ext.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionPhotoFragment :
    BaseFragment<FragmentCollectionPhotoBinding, CollectionPhotoViewModel>() {

    private val photoAdapter by lazy { PhotoAdapter(::clickPhotoItem) }

    override val viewModel: CollectionPhotoViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentCollectionPhotoBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@CollectionPhotoFragment.viewLifecycleOwner
            viewModel = this@CollectionPhotoFragment.viewModel
            adapter = photoAdapter
            toolbarCollectionPhoto.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        }
    }

    override fun bindView() {
        arguments?.run {
            val data: Collection? = getParcelable(ARGUMENT_COLLECTION)
            viewModel.setCollection(data)
        }
        (viewBinding.recyclerCollectionPhoto.layoutManager as StaggeredGridLayoutManager).gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
    }

    override fun registerLiveData() = with(viewModel) {
        super.registerLiveData()
        collection.observe(viewLifecycleOwner, ::setCollectionData)
        photos.observe(viewLifecycleOwner, ::updatePhotos)
    }

    private fun setCollectionData(collection: Collection) {
        viewBinding.toolbarCollectionPhoto.title = collection.title
        viewModel.getPhotosCollection(collection.id)
    }

    private fun clickPhotoItem(item: Photo) {
        replaceFragment(R.id.containerFrameLayout, DetailFragment.newInstance(item))
    }

    private fun updatePhotos(photos: MutableList<Photo>) {
        photoAdapter.updateData(photos)
    }

    companion object {
        private const val ARGUMENT_COLLECTION = "ARGUMENT_COLLECTION"

        fun newInstance(collection: Collection) = CollectionPhotoFragment().apply {
            arguments = bundleOf(ARGUMENT_COLLECTION to collection)
        }
    }
}
