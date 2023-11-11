package com.example.instapets.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.instapets.R
import com.example.instapets.R.id.libraryFragment
import com.example.instapets.R.id.searchFragment
import com.example.instapets.R.menu.toolbar_menu
import com.example.instapets.R.string.app_name
import com.example.instapets.R.string.library_title
import com.example.instapets.R.string.search_title
import com.example.instapets.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var toolbarSearch: MenuItem? = null
    private var toolbarChangeDisplay: MenuItem? = null
    private var toolbarSearchView: SearchView? = null

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

        val nav = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = nav.navController
        binding.mainBottomNavigation.setupWithNavController(navController)
    }

    private fun initListeners() {
        binding.mainSwipe.setOnRefreshListener {
            lifecycleScope.launch {
                delay(1200)
                binding.mainSwipe.isRefreshing = false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            changeToolbarItems(destination.id)
        }
    }

    private fun initToolbarUI(menu: Menu) {
        toolbarSearch = menu.findItem(R.id.toolbarSearch)

        toolbarSearchView = toolbarSearch?.actionView as SearchView
        toolbarSearchView?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })

        toolbarChangeDisplay = menu.findItem(R.id.toolbarChangeDisplay)
        toolbarChangeDisplay?.setOnMenuItemClickListener {
            it.apply {
                isChecked = !isChecked
                setIcon(
                    if (isChecked) R.drawable.ic_display_small
                    else R.drawable.ic_display_large
                )
                changeDisplay(isChecked)
            }
            true
        }

        navController.currentDestination?.id?.let { changeToolbarItems(it) }
    }

    private fun changeToolbarItems(@IdRes fragmentID: Int) {
        //Visibility for each item in the toolbar
        var search = false
        var changeDisplay = false

        //Check the current fragment and set the visibility of the toolbar items and title
        supportActionBar?.title = when (fragmentID) {
            searchFragment -> {
                search = true
                 getString(search_title)
            }

            libraryFragment -> {
                changeDisplay = true
                search = true
                getString(library_title)
            }

            else -> getString(app_name)
        }

        //Collapse
        toolbarSearch?.collapseActionView()
        binding.mainAppBarLayout.setExpanded(true, true)

        //Visibility
        toolbarSearch?.isVisible = search
        toolbarChangeDisplay?.isVisible = changeDisplay
    }

    private fun changeDisplay(largeDisplay:Boolean) {

    }

}