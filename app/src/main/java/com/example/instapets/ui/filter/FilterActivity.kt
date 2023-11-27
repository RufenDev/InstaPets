package com.example.instapets.ui.filter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instapets.R
import com.example.instapets.core.CoreExtensions.toPetType
import com.example.instapets.core.PetTypes
import com.example.instapets.core.PetTypes.CAT
import com.example.instapets.databinding.ActivityFilterBinding
import com.example.instapets.domain.model.filter.FilterModel
import com.example.instapets.domain.usecase.GetPetFilters
import com.example.instapets.ui.core.Extensions.visibility
import com.example.instapets.ui.filter.adapter.FilterAdapter
import com.example.instapets.ui.search.SearchFragment.Companion.FILTER_PET_PREFERENCE_KEY
import com.example.instapets.ui.search.SearchFragment.Companion.SELECTED_FILTER_ID_KEY
import com.example.instapets.ui.search.SearchFragment.Companion.SELECTED_FILTER_PET_TYPE_KEY
import com.example.instapets.ui.search.SearchFragment.Companion.FILTER_TYPE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilterActivity : AppCompatActivity() {

    @Inject
    lateinit var getPetFilters: GetPetFilters

    private lateinit var binding: ActivityFilterBinding
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var searchView: SearchView

    private var filterType = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterType = intent?.extras?.getBoolean(FILTER_TYPE_KEY, true) ?: true

        initUI()
        lifecycleScope.launch { initAPI() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_toolbar_menu, menu)

        menu?.let {
            searchView = menu.findItem(R.id.toolbarFilterSearch).actionView as SearchView
            searchView.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false
                override fun onQueryTextChange(newText: String?) =
                    filterAdapter.filter(newText.orEmpty())
            })
        }

        return true
    }

    private fun initUI() {
        setSupportActionBar(binding.filterToolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setTitle(R.string.filter_title)
        supportActionBar?.setSubtitle(R.string.filters_title)

        filterAdapter = FilterAdapter { onFilterSelected(it) }

        binding.rvFilter.apply {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(
                this@FilterActivity, LinearLayoutManager.VERTICAL, false
            )
        }

        binding.filterToolbar.setNavigationOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private suspend fun initAPI() {
        val pet = getPet()
        val list = getPetFilters(filterType, pet)

        binding.skFilterLoading.isVisible = false

        if (list.isNotEmpty()) {
            filterAdapter.submitList(list)
            binding.rvFilter.visibility(true)

        } else {
            if (!filterType && pet == PetTypes.DOG) {
                binding.ivFilterError.setImageResource(R.drawable.not_found)
                binding.tvFilterError.text = getString(R.string.error_no_categories_found)

            } else {
                binding.ivFilterError.setImageResource(R.drawable.ic_wifi_off)
                binding.tvFilterError.text = getString(R.string.error_no_connection)
            }

            binding.ivFilterError.isVisible = true
            binding.tvFilterError.isVisible = true
        }
    }

    private fun onFilterSelected(filter: FilterModel) {
        val intent = Intent()

        intent.putExtra(SELECTED_FILTER_ID_KEY, filter.id)
        intent.putExtra(SELECTED_FILTER_PET_TYPE_KEY, filter.petType)
        intent.putExtra(FILTER_TYPE_KEY, filterType)

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getPet() =
        (intent?.extras?.getInt(FILTER_PET_PREFERENCE_KEY, CAT.value) ?: CAT.value).toPetType()

}
