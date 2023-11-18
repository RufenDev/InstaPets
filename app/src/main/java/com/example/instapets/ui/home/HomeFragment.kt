package com.example.instapets.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instapets.databinding.FragmentHomeBinding
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.ui.core.Extensions.lastVisibleItem
import com.example.instapets.ui.core.Extensions.visibility
import com.example.instapets.ui.home.adapter.HomeAction
import com.example.instapets.ui.home.adapter.HomeAdapter
import com.example.instapets.ui.home.viewmodel.HomeState.*
import com.example.instapets.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(inf: LayoutInflater, cont: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inf, cont, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initFlows()
        initListeners()
    }

    private fun initUI() {
        binding.rvHome.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            homeAdapter = HomeAdapter { onHomeAction(it) }
            adapter = homeAdapter
        }
    }

    private fun initFlows() {

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                homeViewModel.state.collect {
                    if(it != HOME_LOAD_MORE){
                        binding.rvHome.visibility(it == HOME_SUCCESS)
                        binding.homeError.isVisible = it == HOME_ERROR
                        binding.homeShimmerLoading.isVisible = it == HOME_REFRESH
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                homeViewModel.pets.collect {
                    homeAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                binding.rvHome.lastVisibleItem.collect {
                    homeViewModel.getRandomPets(lastPosition = it)
                }
            }
        }
    }

    private fun initListeners() {
        binding.homeSwipe.setOnRefreshListener {
            lifecycleScope.launch {
                homeViewModel.getRandomPets(refreshing = true)
                delay(500)
                binding.homeSwipe.isRefreshing = false
            }
        }
    }

    private fun onHomeAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnPetLiked -> onPetLiked(action.homePet)
            is HomeAction.OnPetSaved -> onPetSaved(action.homePet)
            is HomeAction.OnMoreOptions -> onMoreOption(action.homePet)
            is HomeAction.SeePetDescription -> navigateToPetDescription(action.homePet)
        }
    }

    private fun navigateToPetDescription(homePet: HomePetModel) {

    }


    private fun onPetLiked(pet: HomePetModel) {

    }

    private fun onPetSaved(pet: HomePetModel) {

    }

    private fun onMoreOption(homePet: HomePetModel) {

    }
}