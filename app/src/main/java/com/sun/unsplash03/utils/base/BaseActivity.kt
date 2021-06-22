package com.sun.unsplash03.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.sun.unsplash03.R
import com.sun.unsplash03.widget.DialogManager
import com.sun.unsplash03.widget.DialogManagerImpl

abstract class BaseActivity<viewBinding : ViewDataBinding, viewModel : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: viewModel
    protected lateinit var viewBinding: viewBinding

    lateinit var dialogManager: DialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ThemeUnsplash03)
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding(layoutInflater)
        dialogManager = DialogManagerImpl(this)
        setContentView(viewBinding.root)
        initView()
        initData()
    }

    override fun onStart() {
        super.onStart()
        registerLiveData()
    }

    fun showLoading() {
        dialogManager.showLoading()
    }

    fun hideLoading() {
        dialogManager.hideLoading()
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding
    abstract fun initView()
    abstract fun initData()

    open fun registerLiveData() {
        viewModel.run {
            isLoading.observe(this@BaseActivity, {
                if (it) showLoading() else hideLoading()
            })
        }
    }
}
