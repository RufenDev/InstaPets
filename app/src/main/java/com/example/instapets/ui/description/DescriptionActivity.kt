package com.example.instapets.ui.description

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.instapets.core.PetTypes
import com.example.instapets.databinding.ActivityDescriptionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private val descriptionViewModel: DescriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListeners()
//        lifecycleScope.launch { loadDescription() }
    }

    private fun initListeners() {
        binding.descriptionToolbar.setNavigationOnClickListener { finish() }
    }

    private fun initUI() {
        setSupportActionBar(binding.descriptionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private suspend fun loadDescription() {
        val id = intent?.extras?.getString(PET_DESCRIPTION_ID) ?: ""
        val type = intent?.extras?.getBoolean(PET_DESCRIPTION_TYPE) ?: true

        val description = descriptionViewModel.getDescription(id, type)

        if (description.id.isNotBlank()) {


        } else {

        }
    }

    companion object {
        const val PET_DESCRIPTION_ID = "pet_description_id"
        const val PET_DESCRIPTION_TYPE = "pet_description_type"
    }
}