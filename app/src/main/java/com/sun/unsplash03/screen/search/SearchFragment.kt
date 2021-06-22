package com.sun.unsplash03.screen.search

import android.graphics.Color
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.FragmentSearchBinding
import com.sun.unsplash03.screen.collection.adapter.CollectionAdapter
import com.sun.unsplash03.screen.collection_photo.CollectionPhotoFragment
import com.sun.unsplash03.screen.detail.DetailFragment
import com.sun.unsplash03.screen.photo.adapter.PhotoAdapter
import com.sun.unsplash03.utils.base.BaseFragment
import com.sun.unsplash03.utils.ext.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private val photoAdapter by lazy { PhotoAdapter(::clickPhotoItem) }
    private val collectionAdapter by lazy { CollectionAdapter(::clickItemCollection) }

    override val viewModel: SearchViewModel by viewModel()
    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentSearchBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@SearchFragment.viewLifecycleOwner
            viewModel = this@SearchFragment.viewModel
            adapterPhoto = photoAdapter
            adapterCollection = collectionAdapter
        }
    }

    override fun bindView() {
        arguments?.run {
            viewModel.setTypeSearch(getInt(ARGUMENT_TYPE_SEARCH))
        }
        viewBinding.run {
            (recyclerPhotoSearch.layoutManager as StaggeredGridLayoutManager).gapStrategy =
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            searchView.run {
                requestFocus()
                setOnQueryTextFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        showKeyboard(requireActivity())
                    }
                }
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            this@SearchFragment.viewModel.searchContent(query)
                            viewBinding.searchView.clearFocus()
                        }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?) = false
                })
                toolbarSearch.setNavigationOnClickListener {
                    parentFragmentManager.popBackStack()
                    hideKeyboard(requireActivity())
                }
            }
        }
    }

    override fun registerLiveData() = with(viewModel) {
        super.registerLiveData()
        photos.observe(viewLifecycleOwner, Observer(::updatePhotos))
        collections.observe(viewLifecycleOwner, Observer(::updateCollections))
    }

    private fun clickPhotoItem(item: Photo) {
        replaceFragment(R.id.containerFrameLayout, DetailFragment.newInstance(item))
    }

    private fun updatePhotos(photos: MutableList<Photo>) {
        viewBinding.run {
            recyclerCollectionSearch.toGone()
            recyclerPhotoSearch.toVisible()
        }
        photoAdapter.updateData(photos)
    }

    private fun updateCollections(collections: MutableList<Collection>) {
        viewBinding.run {
            recyclerCollectionSearch.toVisible()
            recyclerPhotoSearch.toGone()
        }
        collectionAdapter.submitList(collections)
    }

    private fun clickItemCollection(collection: Collection) {
        replaceFragment(R.id.containerFrameLayout, CollectionPhotoFragment.newInstance(collection))
    }

    companion object {
        private const val ARGUMENT_TYPE_SEARCH = "ARGUMENT_TYPE_SEARCH"

        fun newInstance(typeSearch: Int) = SearchFragment().apply {
            arguments = bundleOf(ARGUMENT_TYPE_SEARCH to typeSearch)
        }
    }
}
