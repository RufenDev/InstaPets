package com.example.instapets.ui.options

import androidx.lifecycle.ViewModel
import com.example.instapets.core.PetTypes
import com.example.instapets.ui.core.preferences.ConfigurationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
    private val configurations: ConfigurationPreferences,
) : ViewModel() {

    fun getPet() = configurations.loadPet()

    fun setPet(pet: PetTypes) { configurations.savePet(pet) }
}