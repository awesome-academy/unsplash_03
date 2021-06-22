package com.sun.unsplash03.screen.search

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
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
import com.sun.unsplash03.utils.provider.HistorySuggestionProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private val photoAdapter by lazy { PhotoAdapter(::clickPhotoItem) }
    private val collectionAdapter by lazy { CollectionAdapter(::clickItemCollection) }
    private var suggestions: SearchRecentSuggestions? = null
    private var searchManager: SearchManager? = null

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
        searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        suggestions = SearchRecentSuggestions(
            requireContext(),
            HistorySuggestionProvider.AUTHORITY,
            HistorySuggestionProvider.MODE
        )
    }

    override fun bindView() {
        arguments?.run {
            viewModel.setTypeSearch(getInt(ARGUMENT_TYPE_SEARCH))
        }
        viewBinding.run {
            (recyclerPhotoSearch.layoutManager as StaggeredGridLayoutManager).gapStrategy =
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            searchView.run {
                setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
                requestFocus()
                isQueryRefinementEnabled = true
                setOnQueryTextFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        showKeyboard(requireActivity())
                    }
                }
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            this@SearchFragment.viewModel.searchContent(query)
                            suggestions?.saveRecentQuery(it, null)
                            viewBinding.searchView.clearFocus()
                        }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?) = false
                })
                setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                    override fun onSuggestionSelect(position: Int) = true

                    override fun onSuggestionClick(position: Int): Boolean {
                        setQuery(getQuerySearchView(position), true)
                        return true
                    }
                })
            }
            toolbarSearch.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
                it.hideKeyboard(requireActivity())
            }
            requireActivity().intent.run {
                getStringExtra(SearchManager.QUERY)?.let {
                    suggestions?.saveRecentQuery(it, null)
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

    private fun getQuerySearchView(position: Int): String {
        val selectedView = viewBinding.searchView.suggestionsAdapter
        val cursor = selectedView.getItem(position) as Cursor
        val index = cursor.getColumnIndexOrThrow(SearchManager.SUGGEST_COLUMN_TEXT_1)
        return cursor.getString(index).toString()
    }

    companion object {
        private const val ARGUMENT_TYPE_SEARCH = "ARGUMENT_TYPE_SEARCH"

        fun newInstance(typeSearch: Int) = SearchFragment().apply {
            arguments = bundleOf(ARGUMENT_TYPE_SEARCH to typeSearch)
        }
    }
}
