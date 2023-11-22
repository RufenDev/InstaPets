package com.example.instapets.ui.options

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.instapets.R.id.rbtnBothOptions
import com.example.instapets.R.id.rbtnCatOptions
import com.example.instapets.R.id.rbtnDogOptions
import com.example.instapets.core.PetTypes.*
import com.example.instapets.databinding.FragmentOptionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : Fragment() {

    private val optionsViewModel: OptionsViewModel by viewModels()
    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inf: LayoutInflater, cont: ViewGroup?, bundle: Bundle?): View {
        _binding = FragmentOptionsBinding.inflate(inf, cont, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()
    }

    private fun initUI() {
        val pet = optionsViewModel.getPet()
        binding.rgPetOption.check(
            when (pet) {
                DOG -> rbtnDogOptions
                PETS -> rbtnBothOptions
                else -> rbtnCatOptions
            }
        )
    }

    private fun initListeners() {
        binding.rgPetOption.setOnCheckedChangeListener { _, checkedId ->
            optionsViewModel.setPet(
                when (checkedId) {
                    rbtnDogOptions -> DOG
                    rbtnBothOptions -> PETS
                    else -> CAT
                }
            )
        }
    }

}