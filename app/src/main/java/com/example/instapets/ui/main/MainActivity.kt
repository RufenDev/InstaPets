package com.example.instapets.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView.OVER_SCROLL_NEVER
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.instapets.R
import com.example.instapets.R.id.libraryFragment
import com.example.instapets.R.id.optionFragment
import com.example.instapets.R.id.searchFragment
import com.example.instapets.R.menu.toolbar_menu
import com.example.instapets.R.string.app_name
import com.example.instapets.R.string.library_title
import com.example.instapets.R.string.options_title
import com.example.instapets.R.string.search_title
import com.example.instapets.databinding.ActivityMainBinding
import com.example.instapets.ui.main.adapter.MainViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainViewPagerAdapter

    private var toolbarChangeDisplayButton: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(toolbar_menu, menu)
        menu?.let { initToolbarUI(it) }
        return true
    }

    private fun initUI() {
        setSupportActionBar(binding.mainToolbar)

        viewPagerAdapter = MainViewPagerAdapter(this)
        binding.mainViewPager.apply {
            getChildAt(0).overScrollMode = OVER_SCROLL_NEVER
            adapter = viewPagerAdapter
        }
    }

    private fun initListeners() {
        binding.mainViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val destinationID = viewPagerAdapter.getFragmentId(position)

                binding.mainBottomNavigation.selectedItemId = destinationID

                changeToolbarItems(destinationID)

                super.onPageSelected(position)
            }
        })

        binding.mainBottomNavigation.setOnItemSelectedListener { destination ->
            binding.mainViewPager.currentItem =
                viewPagerAdapter.getFragmentPosition(destination.itemId)

            changeToolbarItems(destination.itemId)
            true
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isNotInFirstPage = binding.mainViewPager.currentItem > 0

                if (isNotInFirstPage) {
                    //Return to first page
                    binding.mainViewPager.currentItem = 0

                } else if (viewPagerAdapter.isFabVisibleInHomeFragment()) {
                    viewPagerAdapter.scrollHomeToTop()

                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }

            }
        })
    }

    private fun initToolbarUI(menu: Menu) {
        toolbarChangeDisplayButton = menu.findItem(R.id.toolbarChangeDisplayButton)
        toolbarChangeDisplayButton?.setOnMenuItemClickListener { itemButton ->
            itemButton.apply {
                isChecked = !isChecked
                setIcon(
                    if (isChecked) R.drawable.ic_display_small
                    else R.drawable.ic_display_large
                )
                changeDisplay(isChecked)
            }
            true
        }

        val currentFragmentId = viewPagerAdapter.getFragmentId(binding.mainViewPager.currentItem)
        changeToolbarItems(currentFragmentId)
    }

    private fun changeToolbarItems(@IdRes fragmentID: Int) {
        var changeDisplayVisibility = false

        //Check the current fragment and set the visibility of the toolbar items and title
        supportActionBar?.title = when (fragmentID) {
            libraryFragment -> {
                changeDisplayVisibility = true
                getString(library_title)
            }

            searchFragment -> getString(search_title)
            optionFragment -> getString(options_title)
            else -> getString(app_name)
        }

        toolbarChangeDisplayButton?.isVisible = changeDisplayVisibility
    }

    private fun changeDisplay(largeDisplay: Boolean) {

    }
}