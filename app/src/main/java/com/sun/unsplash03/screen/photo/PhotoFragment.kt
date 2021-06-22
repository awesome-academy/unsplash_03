package com.sun.unsplash03.screen.photo

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.FragmentPhotoBinding
import com.sun.unsplash03.screen.photo.adapter.PhotoAdapter
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {

    private val photoAdapter by lazy { PhotoAdapter(::clickPhotoItem) }

    override val viewModel: PhotoViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentPhotoBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@PhotoFragment.viewLifecycleOwner
            viewModel = this@PhotoFragment.viewModel
            adapter = photoAdapter
        }
    }

    override fun bindView() {
        viewBinding.run {
            swipeRefreshMain.run {
                setOnRefreshListener {
                    isRefreshing = false
                    viewModel?.getPhotos()
                }
            }
            (recyclerPhoto.layoutManager as StaggeredGridLayoutManager).gapStrategy =
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }
    }

    override fun registerLiveData() = with(viewModel) {
        super.registerLiveData()
        photos.observe(viewLifecycleOwner, Observer(::updatePhotos))
    }

    private fun clickPhotoItem(photo: Photo) {
    }

    private fun updatePhotos(photos: MutableList<Photo>) {
        photoAdapter.updateData(photos)
    }

    companion object {
        fun newInstance() = PhotoFragment()
    }
}
