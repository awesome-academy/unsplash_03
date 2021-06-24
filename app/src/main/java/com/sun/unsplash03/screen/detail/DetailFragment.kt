package com.sun.unsplash03.screen.detail

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.databinding.FragmentDetailBinding
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentDetailBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@DetailFragment.viewLifecycleOwner
            viewModel = this@DetailFragment.viewModel
            toolbarDetail.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun bindView() {
        arguments?.run {
            val data: Photo? = getParcelable(ARGUMENT_IMAGE)
            viewModel.setPhoto(data)
        }
    }

    companion object {
        private const val ARGUMENT_IMAGE = "ARGUMENT_IMAGE"

        fun newInstance(photo: Photo) = DetailFragment().apply {
            arguments = bundleOf(ARGUMENT_IMAGE to photo)
        }
    }
}
