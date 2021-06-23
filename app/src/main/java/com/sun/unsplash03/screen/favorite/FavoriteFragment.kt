package com.sun.unsplash03.screen.favorite

import android.view.LayoutInflater
import com.sun.unsplash03.databinding.FragmentFavoriteBinding
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    override val viewModel: FavoriteViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentFavoriteBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@FavoriteFragment.viewLifecycleOwner
            viewModel = this@FavoriteFragment.viewModel
        }
    }

    override fun bindView() {
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}
