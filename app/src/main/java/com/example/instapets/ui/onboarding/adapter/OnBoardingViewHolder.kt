package com.example.instapets.ui.onboarding.adapter

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instapets.databinding.OnboardingItemBinding
import com.example.instapets.ui.onboarding.model.OnBoardingModel

class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = OnboardingItemBinding.bind(view)

    fun render(model: OnBoardingModel, onGetStartedClicked: () -> Unit) {
        binding.tvOnBoardingTitle.setStringResource(model.title)
        binding.ivOnBoardingImage.setImageResource(model.image)

        model.subtitle?.let { subtitle ->
            binding.btnOnBoardingGetStarted.visibility = INVISIBLE
            binding.tvOnBoardingSubtitle.apply {
                setStringResource(subtitle)
                visibility = VISIBLE
            }

        } ?: run {
            binding.tvOnBoardingSubtitle.visibility = INVISIBLE
            binding.btnOnBoardingGetStarted.apply {
                visibility = VISIBLE
                setOnClickListener { onGetStartedClicked() }
            }
        }
    }

    private fun AppCompatTextView.setStringResource(@StringRes id: Int) {
        text = itemView.resources.getString(id)
    }
}