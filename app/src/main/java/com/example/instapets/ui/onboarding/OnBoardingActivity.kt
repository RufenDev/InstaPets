package com.example.instapets.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.instapets.core.PetTypes
import com.example.instapets.databinding.ActivityOnBoardingBinding
import com.example.instapets.ui.core.Animations.fadeIn
import com.example.instapets.ui.core.Animations.fadeOut
import com.example.instapets.ui.core.preferences.ConfigurationPreferences
import com.example.instapets.ui.onboarding.adapter.OnBoardingAdapter
import com.example.instapets.ui.onboarding.provider.OnBoardingProvider
import com.example.instapets.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    @Inject lateinit var onBoardingProvider: OnBoardingProvider
    @Inject lateinit var configurationPreferences: ConfigurationPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (configurationPreferences.getSkipOnBoarding()) {
            navigateToMain()

        } else {
            splash.setKeepOnScreenCondition {
                initUI()
                initListeners()
                false
            }
        }
    }

    private fun initUI() {
        onBoardingAdapter = OnBoardingAdapter(
            onBoardingProvider(),
            { onGetStartedClicked() },
            { onPetPreferenceSelected(it) }
        )

        binding.vpOnBoarding.apply {
            adapter = onBoardingAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
        }

        binding.ciOnBoarding.setViewPager(binding.vpOnBoarding)
    }

    private fun initListeners() {
        binding.btnOnBoardingSkip.setOnClickListener {
            binding.vpOnBoarding.setCurrentItem(onBoardingProvider().size, true)
        }

        binding.vpOnBoarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val isInLastPosition = position == onBoardingProvider().size - 1

                binding.btnOnBoardingSkip.apply {
                    if (isInLastPosition) fadeOut()
                    else fadeIn()
                }

                super.onPageSelected(position)
            }
        })

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isNotInFirstPosition = binding.vpOnBoarding.currentItem > 0

                if (isNotInFirstPosition) {
                    //Return to first position
                    binding.vpOnBoarding.setCurrentItem(0, true)

                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })
    }

    private fun navigateToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun onGetStartedClicked() {
        configurationPreferences.saveSkipOnboardingOption()
        navigateToMain()
    }

    private fun onPetPreferenceSelected(pet: PetTypes) {
        configurationPreferences.savePet(pet)
    }
}