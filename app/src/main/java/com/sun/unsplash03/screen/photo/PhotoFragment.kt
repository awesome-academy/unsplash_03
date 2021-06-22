package com.sun.unsplash03.screen.photo

import android.view.LayoutInflater
import com.sun.unsplash03.databinding.FragmentPhotoBinding
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel>() {

    override val viewModel: PhotoViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentPhotoBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@PhotoFragment.viewLifecycleOwner
            viewModel = this@PhotoFragment.viewModel
        }
    }

    override fun bindView() {
    }

    companion object {
        fun newInstance() = PhotoFragment()
    }
}
