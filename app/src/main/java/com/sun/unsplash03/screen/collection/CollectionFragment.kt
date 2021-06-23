package com.sun.unsplash03.screen.collection

import android.view.LayoutInflater
import com.sun.unsplash03.databinding.FragmentCollectionBinding
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionFragment : BaseFragment<FragmentCollectionBinding, CollectionViewModel>() {

    override val viewModel: CollectionViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentCollectionBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@CollectionFragment.viewLifecycleOwner
            viewModel = this@CollectionFragment.viewModel
        }
    }

    override fun bindView() {
    }

    companion object {
        fun newInstance() = CollectionFragment()
    }
}
