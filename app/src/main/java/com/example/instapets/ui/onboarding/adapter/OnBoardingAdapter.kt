package com.example.instapets.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instapets.R
import com.example.instapets.ui.onboarding.model.OnBoardingModel

class OnBoardingAdapter(
    private val onBoardingList: List<OnBoardingModel>,
    private val onGetStartedClicked: () -> Unit
) : RecyclerView.Adapter<OnBoardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnBoardingViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.onboarding_item, parent, false)
    )

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.render(onBoardingList[position], onGetStartedClicked)
    }

    override fun getItemCount() = onBoardingList.size
}