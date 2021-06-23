package com.sun.unsplash03.screen.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(fragmentManager: FragmentManager, private val fragments: List<Fragment>) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}
