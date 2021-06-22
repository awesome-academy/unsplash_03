package com.sun.unsplash03.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sun.unsplash03.utils.ext.loadImageWithUrl

object BindingUtils {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            imageView.loadImageWithUrl(it)
        }
    }

    @JvmStatic
    @BindingAdapter("app:adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter(value = ["app:isLoad", "app:onLoadMore"])
    fun RecyclerView.onScrollListener(
        isLoad: Boolean,
        loadMoreListener: LoadMoreRecyclerViewListener
    ) {
        clearOnScrollListeners()
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recycler, dx, dy)
                if (dy > 0) {
                    when (recycler.layoutManager) {
                        is LinearLayoutManager -> {
                            (recycler.layoutManager as LinearLayoutManager).run {
                                if (!isLoad && findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                                    loadMoreListener.onLoadData()
                                }
                            }
                        }
                        is StaggeredGridLayoutManager -> {
                            (recycler.layoutManager as StaggeredGridLayoutManager).run {
                                val lastVisibleItemPosition =
                                    getLastVisibleItem(findLastVisibleItemPositions(null))
                                if (!isLoad && (childCount + lastVisibleItemPosition) >= itemCount) {
                                    loadMoreListener.onLoadData()
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    @JvmStatic
    @BindingAdapter("app:onRefresh")
    fun SwipeRefreshLayout.onRefreshListener(
        refreshListener: SwipeRefreshListener
    ) {
        setOnRefreshListener {
            refreshListener.onRefresh()
            isRefreshing = false
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}
