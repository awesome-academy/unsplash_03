package com.sun.unsplash03.utils.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.sun.unsplash03.widget.DialogManager
import com.sun.unsplash03.widget.DialogManagerImpl

abstract class BaseFragment<viewBinding : ViewDataBinding, viewModel : BaseViewModel> : Fragment() {

    protected abstract val viewModel: viewModel

    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!!

    var dialogManager: DialogManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogManager = DialogManagerImpl(getContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = inflateViewBinding(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        bindView()
        registerLiveData()
    }

    fun showLoading() {
        dialogManager?.showLoading()
    }

    fun hideLoading() {
        dialogManager?.hideLoading()
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding
    abstract fun setUpView()
    abstract fun bindView()

    open fun registerLiveData() {
        viewModel.run {
            isLoading.observe(viewLifecycleOwner, {
                if (it) showLoading() else hideLoading()
            })
        }
    }
}
