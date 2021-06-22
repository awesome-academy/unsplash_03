package com.sun.unsplash03.screen.search

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.FragmentSearchBinding
import com.sun.unsplash03.screen.detail.DetailFragment
import com.sun.unsplash03.screen.photo.adapter.PhotoAdapter
import com.sun.unsplash03.utils.BottomType
import com.sun.unsplash03.utils.base.BaseFragment
import com.sun.unsplash03.utils.ext.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private val photoAdapter by lazy { PhotoAdapter(::clickPhotoItem) }

    override val viewModel: SearchViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentSearchBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@SearchFragment.viewLifecycleOwner
            viewModel = this@SearchFragment.viewModel
            adapter = photoAdapter
        }
    }

    override fun bindView() {
        arguments?.run {
            when (getInt(ARGUMENT_TYPE_SEARCH)) {
                BottomType.HOME.ordinal -> {
                }
                else -> {
                }
            }
        }
    }

    override fun registerLiveData() = with(viewModel) {
        photos.observe(viewLifecycleOwner, Observer(::updatePhotos))
    }

    private fun clickPhotoItem(item: Photo) {
        replaceFragment(R.id.containerFrameLayout, DetailFragment.newInstance(item))
    }

    private fun updatePhotos(photos: MutableList<Photo>) {
        photoAdapter.updateData(photos)
    }

    companion object {
        private const val ARGUMENT_TYPE_SEARCH = "ARGUMENT_TYPE_SEARCH"

        fun newInstance(typeSearch: Int) = SearchFragment().apply {
            arguments = bundleOf(
                ARGUMENT_TYPE_SEARCH to typeSearch
            )
        }
    }
}
