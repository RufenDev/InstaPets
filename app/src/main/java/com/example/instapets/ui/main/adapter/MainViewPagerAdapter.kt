package com.example.instapets.ui.main.adapter

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instapets.R
import com.example.instapets.ui.home.HomeFragment
import com.example.instapets.ui.library.LibraryFragment
import com.example.instapets.ui.search.SearchFragment

class MainViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragmentList = listOf(
        MainViewPagerAdapterModel(HomeFragment(), R.id.homeFragment),
        MainViewPagerAdapterModel(SearchFragment(), R.id.searchFragment),
        MainViewPagerAdapterModel(LibraryFragment(), R.id.libraryFragment)
    )

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position].fragment

    fun getFragmentId(position: Int) = fragmentList[position].id

    fun getFragmentPosition(@IdRes id: Int) = fragmentList
        .indexOfFirst { fragment -> fragment.id == id }
        .takeIf { resultIndex -> resultIndex >= 0 } ?: 0

    private data class MainViewPagerAdapterModel(val fragment: Fragment, @IdRes val id: Int)
}