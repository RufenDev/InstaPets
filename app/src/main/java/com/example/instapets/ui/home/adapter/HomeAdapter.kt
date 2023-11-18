package com.example.instapets.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.instapets.R.layout.home_item
import com.example.instapets.R.layout.loading_bar_item
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.ui.core.LoadingBarViewHolder
import com.example.instapets.ui.home.viewmodel.HomeViewModel.Companion.LOADING_TYPE
import com.example.instapets.ui.home.viewmodel.HomeViewModel.Companion.loadingBar

class HomeAdapter(
    private val onHomeAction: (HomeAction) -> Unit
) : ListAdapter<HomePetModel, ViewHolder>(HomeDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == LOADING_TYPE) {
            LoadingBarViewHolder(inflater.inflate(loading_bar_item, parent, false))
        } else {
            HomeViewHolder(inflater.inflate(home_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HomeViewHolder) {
            holder.bind(getItem(position), onHomeAction)
        }
    }

    override fun getItemViewType(position: Int) =
        if (currentList[position] == loadingBar) LOADING_TYPE
        else super.getItemViewType(position)

}