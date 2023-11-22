package com.example.instapets.ui.core.preferences

import android.content.SharedPreferences
import com.example.instapets.core.PetTypes
import com.example.instapets.core.PetTypes.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.SharedPreferences.OnSharedPreferenceChangeListener as OnSharedPreferenceChangeListener1

class ConfigurationPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    fun getSkipOnBoarding() = sharedPreferences.getBoolean(SKIP_ONBOARDING_KEY, false)

    fun saveSkipOnboardingOption() {
        sharedPreferences.edit().putBoolean(SKIP_ONBOARDING_KEY, true).apply()
    }

    fun savePet(pet: PetTypes) {
        sharedPreferences.edit().putInt(PET_PREFERENCE_KEY, pet.value).apply()
    }

    fun loadPet() = PetTypes.values().first {
        it.value == sharedPreferences.getInt(PET_PREFERENCE_KEY, CAT.value)
    }


    val petFlow: Flow<PetTypes>
        get() = callbackFlow {
            val listener = OnSharedPreferenceChangeListener1 { preference, key ->
                launch {
                    val petSaved = PetTypes.values().first {
                        it.value == preference.getInt(key, CAT.value)
                    }
                    send(petSaved)
                }
            }

            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
            awaitClose {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
            }

        }.distinctUntilChanged()

    companion object {
        const val PET_PREFERENCE_KEY = "pet_preference"
        const val SKIP_ONBOARDING_KEY = "skip_onboarding"
    }
}