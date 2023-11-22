package com.example.instapets.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.instapets.databinding.FragmentSearchBinding
import com.example.instapets.ui.core.states.States
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

//    private lateinit var searchAdapter: SearchAdapter

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
//        searchAdapter = SearchAdapter()
        binding.rvSearch.apply {
//            adapter = searchAdapter
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
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
            }
        }
    }

    private suspend fun initFlows() {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
//            launch { searchViewModel.pets.collect{ searchAdapter.submitList(it) } }

            launch {
                searchViewModel.state.collect {
                    if (it != States.LOAD_MORE) {
                        //binding.rvSearch.visibility(it == States.SUCCESS)
                        binding.skSearchLoading.isVisible = it == States.REFRESH
                        binding.searchError.isVisible = it == States.ERROR
                    }
                }
            }
        }
    }

    private fun navigateToFilter(filterByBreed: Boolean) {

    }


}