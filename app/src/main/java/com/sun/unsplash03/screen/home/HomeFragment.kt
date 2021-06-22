package com.sun.unsplash03.screen.home

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.sun.unsplash03.R
import com.sun.unsplash03.databinding.FragmentHomeBinding
import com.sun.unsplash03.screen.collection.CollectionFragment
import com.sun.unsplash03.screen.favorite.FavoriteFragment
import com.sun.unsplash03.screen.photo.PhotoFragment
import com.sun.unsplash03.utils.BottomType
import com.sun.unsplash03.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModel()

    override fun inflateViewBinding(inflater: LayoutInflater) =
        FragmentHomeBinding.inflate(inflater)

    override fun setUpView() {
        viewBinding.run {
            lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            viewModel = this@HomeFragment.viewModel
        }
    }

    override fun bindView() {
        (activity as AppCompatActivity).setSupportActionBar(viewBinding.toolbarHome)
        setHasOptionsMenu(true)
        val listFragments = listOf(
            PhotoFragment.newInstance(),
            CollectionFragment.newInstance(),
            FavoriteFragment.newInstance()
        )
        viewBinding.run {
            viewPagerHome.adapter = HomePagerAdapter(childFragmentManager, listFragments)
            bottomNavigationMain.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeItem -> {
                        viewPagerHome.currentItem = BottomType.HOME.ordinal
                    }
                    R.id.collectionItem -> {
                        viewPagerHome.currentItem = BottomType.COLLECTION.ordinal
                    }
                    R.id.favoriteItem -> {
                        viewPagerHome.currentItem = BottomType.FAVORITE.ordinal
                    }
                    else -> Unit
                }
                true
            }
            viewPagerHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        BottomType.HOME.ordinal -> bottomNavigationMain.menu.findItem(R.id.homeItem).isChecked =
                            true
                        BottomType.COLLECTION.ordinal -> bottomNavigationMain.menu.findItem(R.id.collectionItem).isChecked =
                            true
                        BottomType.FAVORITE.ordinal -> bottomNavigationMain.menu.findItem(R.id.favoriteItem).isChecked =
                            true
                        else -> Unit
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchItem -> {
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
