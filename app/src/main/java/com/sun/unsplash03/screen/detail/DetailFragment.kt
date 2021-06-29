package com.sun.unsplash03.screen.detail

import android.app.Dialog
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sun.unsplash03.R
import com.sun.unsplash03.data.model.Photo
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.data.source.local.entity.PhotoEntity
import com.sun.unsplash03.databinding.DialogAddPhotoFavoriteBinding
import com.sun.unsplash03.databinding.FragmentDetailBinding
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private var collections: List<CollectionEntity>? = null
    private var photo: Photo? = null

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
                showDialogAddPhoto()
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    override fun registerLiveData() = with(viewModel) {
        super.registerLiveData()
        collections.observe(viewLifecycleOwner, {
            this@DetailFragment.collections = it
        })
        photo.observe(viewLifecycleOwner, {
            this@DetailFragment.photo = it
        })
    }

    private fun showDialogAddPhoto() {
        collections?.let { listCollections ->
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            Dialog(requireContext()).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                val binding = DataBindingUtil.inflate<DialogAddPhotoFavoriteBinding>(
                    LayoutInflater.from(requireContext()),
                    R.layout.dialog_add_photo_favorite,
                    null,
                    false
                )
                setContentView(binding.root)
                window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    listCollections
                ).apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                binding.run {
                    spinnerCollections.adapter = adapter
                    buttonCancel.setOnClickListener {
                        dismiss()
                    }
                    buttonAddPhoto.setOnClickListener {
                        photo?.let {
                            val position = binding.spinnerCollections.selectedItemPosition
                            val entity = PhotoEntity(it.id, it.urls.small, listCollections[position].id)
                            viewModel.insertPhotoFavorite(entity)
                            dismiss()
                            Toast.makeText(context, getString(R.string.photo_add), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }.show()
        } ?: run {
            Toast.makeText(context, getString(R.string.msg_empty_collections), Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val ARGUMENT_IMAGE = "ARGUMENT_IMAGE"

        fun newInstance(photo: Photo) = DetailFragment().apply {
            arguments = bundleOf(ARGUMENT_IMAGE to photo)
        }
    }
}
