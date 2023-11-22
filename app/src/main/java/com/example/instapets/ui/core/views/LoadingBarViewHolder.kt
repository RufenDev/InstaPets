package com.example.instapets.ui.core.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

data class LoadingBarViewHolder(private val view: View) : ViewHolder(view){
    companion object{
        const val LOADING_BAR_ID = "loading_bar"
        const val LOADING_BAR_TYPE = -1
    }
}