package com.sun.unsplash03.screen.detail

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.FragmentDetailBinding
import com.sun.unsplash03.screen.search.SearchFragment
import com.sun.unsplash03.utils.base.BaseFragment
import com.sun.unsplash03.utils.ext.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentDetailBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@DetailFragment.viewLifecycleOwner
            viewModel = this@DetailFragment.viewModel
        }
    }

    override fun bindView() {
        (activity as AppCompatActivity).setSupportActionBar(viewBinding.toolbarDetail)
        viewBinding.toolbarDetail.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        setHasOptionsMenu(true)
        arguments?.run {
            val data: Photo? = getParcelable(ARGUMENT_IMAGE)
            viewModel.setPhoto(data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.downloadItem -> {
            }
            R.id.favoriteItem -> {
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARGUMENT_IMAGE = "ARGUMENT_IMAGE"

        fun newInstance(photo: Photo) = DetailFragment().apply {
            arguments = bundleOf(ARGUMENT_IMAGE to photo)
        }
    }
}
