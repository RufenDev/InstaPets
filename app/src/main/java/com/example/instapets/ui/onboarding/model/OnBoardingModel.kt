package com.example.instapets.ui.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

typealias StringResID = Int

data class OnBoardingModel(
    @StringRes
    val title:StringResID,

    @StringRes
    val subtitle:StringResID? = null,

    @DrawableRes
    val image:StringResID,

    val showPreferences:Boolean = false
)