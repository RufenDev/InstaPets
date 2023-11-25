package com.example.instapets.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instapets.R.id.homeMenuSeeDescription
import com.example.instapets.R.id.homeMenuShare
import com.example.instapets.R.menu.home_pet_menu
import com.example.instapets.databinding.FragmentHomeBinding
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.ui.core.Extensions.lastVisibleItem
import com.example.instapets.ui.core.Extensions.visibility
import com.example.instapets.ui.home.adapter.HomeAction
import com.example.instapets.ui.home.adapter.HomeAdapter
import com.example.instapets.ui.core.states.States.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter

    private var isMoreOptionsOpen = false

    override fun onCreateView(inf: LayoutInflater, cont: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inf, cont, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initListeners()

        lifecycleScope.launch { initFlows() }
    }

    private fun initUI() {
        binding.rvHome.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            homeAdapter = HomeAdapter { onHomeAction(it) }
            adapter = homeAdapter
        }
    }

    private fun initListeners() {
        binding.swipeHome.setOnRefreshListener {
            lifecycleScope.launch {
                homeViewModel.refreshingHome()
                delay(200)
                binding.swipeHome.isRefreshing = false
            }
        }

        binding.fabHomeUp.setOnClickListener { scrollToTop() }
    }

    private suspend fun initFlows() {
        repeatOnLifecycle(STARTED) {
            launch { homeViewModel.pets.collect { homeAdapter.submitList(it) } }

            launch {
                binding.rvHome.lastVisibleItem.collect {
                    homeViewModel.getRandomPets(it)
                    binding.fabHomeUp.apply {
                        if (it > 2) show() else hide()
                    }
                }
            }

            launch {
                homeViewModel.state.collect {
                    if (it != LOAD_MORE) {
                        binding.rvHome.visibility(it == SUCCESS)
                        binding.homeError.isVisible = it == ERROR
                        binding.skHomeLoading.isVisible = it == REFRESH
                    }
                }
            }
        }
    }

    private fun onHomeAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnPetLiked -> homeViewModel.petLiked(action.homePet)
            is HomeAction.OnPetSaved -> homeViewModel.petSaved(action.homePet)
            is HomeAction.OnMoreOptions -> onMoreOption(action)
            is HomeAction.SeePetDescription -> navigateToPetDescription(action.petId)
        }
    }

    private fun navigateToPetDescription(petId: String) {

    }

    private fun onMoreOption(pet: HomeAction.OnMoreOptions) {
        if (!isMoreOptionsOpen) {
            isMoreOptionsOpen = true

            val popup = PopupMenu(requireContext(), pet.button)
            popup.inflate(home_pet_menu)

            popup.setOnDismissListener { isMoreOptionsOpen = false }

            popup.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    homeMenuSeeDescription -> {
                        navigateToPetDescription(pet.homePet.id)
                        true
                    }

                    homeMenuShare -> {
                        sharePet(pet.homePet)
                        true
                    }

                    else -> false
                }
            }

            popup.show()
        }
    }

    private fun sharePet(homePet: HomePetModel) {

    }

    fun isFabVisible() = binding.fabHomeUp.isVisible

    fun scrollToTop() {
        binding.rvHome.smoothScrollToPosition(0)
    }
}