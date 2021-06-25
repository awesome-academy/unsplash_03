package com.sun.unsplash03.screen.collection

import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Collection
import com.sun.unsplash03.databinding.FragmentCollectionBinding
import com.sun.unsplash03.screen.collection.adapter.CollectionAdapter
import com.sun.unsplash03.screen.collection_photo.CollectionPhotoFragment
import com.sun.unsplash03.utils.base.BaseFragment
import com.sun.unsplash03.utils.ext.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionFragment : BaseFragment<FragmentCollectionBinding, CollectionViewModel>() {

    private val collectionAdapter by lazy { CollectionAdapter(::clickItemCollection) }

    override val viewModel: CollectionViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentCollectionBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@CollectionFragment.viewLifecycleOwner
            viewModel = this@CollectionFragment.viewModel
            adapter = collectionAdapter
        }
    }

    override fun bindView() {
    }

    override fun registerLiveData() = with(viewModel) {
        super.registerLiveData()
        collections.observe(viewLifecycleOwner, Observer(::updateCollections))
    }

    private fun updateCollections(collections: MutableList<Collection>) {
        collectionAdapter.submitList(collections)
    }

    private fun clickItemCollection(collection: Collection) {
        replaceFragment(R.id.containerFrameLayout, CollectionPhotoFragment.newInstance(collection))
    }

    companion object {
        fun newInstance() = CollectionFragment()
    }
}
