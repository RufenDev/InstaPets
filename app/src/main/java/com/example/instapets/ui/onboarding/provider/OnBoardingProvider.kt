package com.example.instapets.ui.onboarding.provider

import com.example.instapets.R.drawable.logo
import com.example.instapets.R.string.onboarding_first_subtitle
import com.example.instapets.R.string.onboarding_first_title
import com.example.instapets.R.string.onboarding_fourth_title
import com.example.instapets.R.string.onboarding_second_subtitle
import com.example.instapets.R.string.onboarding_second_title
import com.example.instapets.R.string.onboarding_third_subtitle
import com.example.instapets.R.string.onboarding_third_title
import com.example.instapets.ui.onboarding.model.OnBoardingModel
import javax.inject.Inject

class OnBoardingProvider @Inject constructor() {
    operator fun invoke(): List<OnBoardingModel> {
        return listOf(
            OnBoardingModel(onboarding_first_title, onboarding_first_subtitle, logo),
            OnBoardingModel(onboarding_second_title, onboarding_second_subtitle, logo),
            OnBoardingModel(onboarding_third_title, onboarding_third_subtitle, logo, true),
            OnBoardingModel(onboarding_fourth_title, image = logo),
        )
    }
}