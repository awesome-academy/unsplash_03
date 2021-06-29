package com.sun.unsplash03.screen.favorite

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sun.unsplash03.R
import com.sun.unsplash03.data.source.local.entity.CollectionEntity
import com.sun.unsplash03.databinding.DialogInsertCollectionBinding
import com.sun.unsplash03.databinding.FragmentFavoriteBinding
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    private var titleCollection = ""

    override val viewModel: FavoriteViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentFavoriteBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@FavoriteFragment.viewLifecycleOwner
            viewModel = this@FavoriteFragment.viewModel
        }
        setupPermissions()
    }

    override fun bindView() {
        viewBinding.floatingActionButtonAdd.setOnClickListener { showAddCollectionDialog() }
    }

    override fun registerLiveData() = with(viewModel) {
        super.registerLiveData()
        collections.observe(viewLifecycleOwner, Observer(::updateCollectionsData))
    }

    private fun updateCollectionsData(collections: MutableList<CollectionEntity>) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, dataImage: Intent?) {
        super.onActivityResult(requestCode, resultCode, dataImage)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && dataImage != null) {
            dataImage.data?.let {
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = activity?.contentResolver?.query(
                    it,
                    filePathColumn, null, null, null
                )
                cursor?.moveToFirst()
                val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                columnIndex?.let {
                    val picturePath = cursor.getString(columnIndex)
                    showAddCollectionDialog(titleCollection, picturePath)
                }
                cursor?.close()
            }
        }
    }

    private fun showAddCollectionDialog(name: String = "", imagePath: String = "") {
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            val binding = DataBindingUtil.inflate<DialogInsertCollectionBinding>(
                LayoutInflater.from(requireContext()),
                R.layout.dialog_insert_collection,
                null,
                false
            )
            setContentView(binding.root)
            window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
            binding.run {
                if (name.isNotEmpty()) editTextTitleCollection.setText(name)
                if (imagePath.isNotEmpty()) editTextCoverCollection.setText(imagePath)
                editTextCoverCollection.setOnClickListener {
                    titleCollection = binding.editTextTitleCollection.text.toString()
                    pickImage()
                    dismiss()
                }
                buttonCancel.setOnClickListener { dismiss() }
                buttonAddCollection.setOnClickListener {
                    val title = editTextTitleCollection.text.toString().trim()
                    val cover = editTextCoverCollection.text.toString().trim()
                    if (title.isNotEmpty() && cover.isNotEmpty()) {
                        val collection = CollectionEntity(title = title, coverPath = cover)
                        viewModel.insertCollection(collection)
                        dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.fill_all_require),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }.show()
    }

    private fun clickItem(collection: CollectionEntity) {
    }

    private fun clickDelete(collection: CollectionEntity) {
    }

    private fun clickEdit(collection: CollectionEntity) {
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 888
        private const val PERMISSION_REQUEST_CODE = 101

        fun newInstance() = FavoriteFragment()
    }
}
