package com.sun.unsplash03.screen.photo_favorite

import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sun.unsplash03.R
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.data.source.local.entity.PhotoEntity
import com.sun.unsplash03.databinding.FragmentPhotoFavoriteBinding
import com.sun.unsplash03.screen.photo_favorite.adapter.FavoritePhotoAdapter
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFavoriteFragment : BaseFragment<FragmentPhotoFavoriteBinding, PhotoFavoriteViewModel>() {

    private val photoAdapter by lazy { FavoritePhotoAdapter(::clickPhotoItem) }

    override val viewModel: PhotoFavoriteViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentPhotoFavoriteBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@PhotoFavoriteFragment.viewLifecycleOwner
            viewModel = this@PhotoFavoriteFragment.viewModel
            adapter = photoAdapter
            toolbarCollectionPhoto.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        }
    }

    override fun bindView() {
        arguments?.run {
            val data: CollectionEntity? = getParcelable(ARGUMENT_COLLECTION)
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

    private fun setCollectionData(collection: CollectionEntity) {
        viewBinding.toolbarCollectionPhoto.title = collection.title
    }

    private fun clickPhotoItem(item: PhotoEntity) {
        showAlertDialogDelete(item)
    }

    private fun updatePhotos(photos: MutableList<PhotoEntity>) {
        photoAdapter.updateData(photos)
    }

    private fun showAlertDialogDelete(photo: PhotoEntity) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(resources.getString(R.string.app_name))
            setMessage(getString(R.string.msg_delete))
            setNegativeButton(getString(R.string.cancel), null)
            setPositiveButton(
                getString(R.string.delete)
            ) { dialog, _ ->
                viewModel.deletePhotoFavorite(photo)
                dialog.dismiss()
            }
        }.show()
    }

    companion object {
        private const val ARGUMENT_COLLECTION = "ARGUMENT_COLLECTION"

        fun newInstance(collection: CollectionEntity) = PhotoFavoriteFragment().apply {
            arguments = bundleOf(ARGUMENT_COLLECTION to collection)
        }
    }
}
