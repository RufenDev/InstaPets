package com.example.instapets.ui.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instapets.databinding.FragmentSearchBinding
import com.example.instapets.domain.model.filter.FilterModel
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.ui.core.Extensions.lastVisibleItem
import com.example.instapets.ui.core.Extensions.visibility
import com.example.instapets.ui.core.states.States
import com.example.instapets.ui.filter.FilterActivity
import com.example.instapets.ui.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(inf: LayoutInflater, cont: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inf, cont, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initListeners()
        lifecycleScope.launch { initFlows() }
    }

    private fun initUI() {
        searchAdapter = SearchAdapter { navigateToDescription(it) }
        binding.rvSearch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(
                requireContext(),
                3,
                GridLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun initListeners() {
        binding.btnFilterBreed.setOnClickListener {
            navigateToFilter(true)
        }

        binding.btnFilterCategory.setOnClickListener {
            navigateToFilter(false)
        }

        binding.swipeSearch.setOnRefreshListener {
            lifecycleScope.launch {
                searchViewModel.refreshSearch()
                delay(200)
                binding.swipeSearch.isRefreshing = false
            }
        }

        binding.fabSearchUp.setOnClickListener {
            binding.rvSearch.smoothScrollToPosition(0)
        }
    }

    private suspend fun initFlows() {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch { searchViewModel.pets.collect { searchAdapter.submitList(it) } }

            launch {
                searchViewModel.state.collect {
                    if (it != States.LOAD_MORE) {
                        binding.rvSearch.visibility(it == States.SUCCESS)
                        binding.skSearchLoading.isVisible = it == States.REFRESH
                        binding.searchError.isVisible = it == States.ERROR
                    }
                }
            }

            launch {
                binding.rvSearch.lastVisibleItem.collect {
                    searchViewModel.getPets(it)
                    binding.fabSearchUp.apply { if (it >= 18) show() else hide() }
                }
            }
        }
    }

    private fun navigateToFilter(filterByBreed: Boolean) {
        val intent = Intent(activity, FilterActivity::class.java)
        intent.putExtra(FILTER_TYPE_KEY, filterByBreed)
        intent.putExtra(FILTER_PET_PREFERENCE_KEY, searchViewModel.petPreference.value)
        filterResponse.launch(intent)
    }

    private fun navigateToDescription(pet: SearchPetModel) {

    }

    private val filterResponse = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {

            val filterId = (it.data?.extras?.getString(SELECTED_FILTER_ID_KEY)).orEmpty()
            val filterPetType = it.data?.extras?.getBoolean(SELECTED_FILTER_PET_TYPE_KEY) ?: true
            searchViewModel.filter = FilterModel(id = filterId, petType = filterPetType)

            val filterType = it.data?.extras?.getBoolean(FILTER_TYPE_KEY) ?: true
            searchViewModel.filterType = filterType

            lifecycleScope.launch { searchViewModel.refreshSearch() }
        }
    }

    companion object {
        const val FILTER_TYPE_KEY = "filter_type"
        const val FILTER_PET_PREFERENCE_KEY = "filter_pet_preference"

        const val SELECTED_FILTER_ID_KEY = "selected_filter_id"
        const val SELECTED_FILTER_PET_TYPE_KEY = "selected_filter_pet"
    }

}