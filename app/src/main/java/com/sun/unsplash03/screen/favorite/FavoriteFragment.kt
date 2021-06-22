package com.sun.unsplash03.screen.favorite

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
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
import com.sun.unsplash03.screen.favorite.adapter.FavoriteCollectionAdapter
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    private var titleCollection = ""
    private var collectionUpdateId = 0
    private var isUpdateMode = false

    private val collectionAdapter by lazy {
        FavoriteCollectionAdapter(
            ::clickItem,
            ::clickDelete,
            ::clickEdit
        )
    }

    override val viewModel: FavoriteViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentFavoriteBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@FavoriteFragment.viewLifecycleOwner
            viewModel = this@FavoriteFragment.viewModel
            adapter = collectionAdapter
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
        collectionAdapter.submitList(collections)
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
                    val entity = CollectionEntity(title = titleCollection, coverPath = picturePath)
                    showAddCollectionDialog(entity)
                }
                cursor?.close()
            }
        }
    }

    private fun showAddCollectionDialog(
        collection: CollectionEntity? = null
    ) {
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
            if (isUpdateMode) binding.buttonAddCollection.text = getString(R.string.update)
            setContentView(binding.root)
            window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
            binding.run {
                collection?.run {
                    if (id != 0) collectionUpdateId = id
                    if (title.isNotEmpty()) editTextTitleCollection.setText(title)
                    if (coverPath.isNotEmpty()) editTextCoverCollection.setText(coverPath)
                }
                editTextCoverCollection.setOnClickListener {
                    titleCollection = binding.editTextTitleCollection.text.toString()
                    pickImage()
                    dismiss()
                }
                buttonCancel.setOnClickListener {
                    dismiss()
                    isUpdateMode = false
                }
                buttonAddCollection.setOnClickListener {
                    val title = editTextTitleCollection.text.toString().trim()
                    val cover = editTextCoverCollection.text.toString().trim()
                    if (title.isNotEmpty() && cover.isNotEmpty()) {
                        val entity: CollectionEntity
                        if (isUpdateMode) {
                            entity = CollectionEntity(
                                id = collectionUpdateId,
                                title = title,
                                coverPath = cover
                            )
                            viewModel.updateCollection(entity)
                        } else {
                            entity = CollectionEntity(title = title, coverPath = cover)
                            viewModel.insertCollection(entity)
                        }
                        dismiss()
                        isUpdateMode = false
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

    private fun showAlertDialogDelete(collection: CollectionEntity) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(resources.getString(R.string.app_name))
            setMessage(getString(R.string.msg_delete))
            setNegativeButton(getString(R.string.cancel), null)
            setPositiveButton(
                getString(R.string.delete)
            ) { dialog, _ ->
                viewModel.deleteCollection(collection)
                dialog.dismiss()
            }
        }.show()
    }

    private fun clickItem(collection: CollectionEntity) {
    }

    private fun clickDelete(collection: CollectionEntity) {
        showAlertDialogDelete(collection)
    }

    private fun clickEdit(collection: CollectionEntity) {
        isUpdateMode = true
        showAddCollectionDialog(collection)
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
