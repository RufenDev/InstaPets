package com.example.instapets.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.ui.core.views.LoadingBarViewHolder.Companion.LOADING_BAR_ID
import com.example.instapets.ui.core.views.LoadingBarViewHolder.Companion.LOADING_BAR_TYPE

class SearchAdapter() : ListAdapter<SearchPetModel, ViewHolder>(SearchDiffUtilCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int) =
        if(currentList[position].id == LOADING_BAR_ID) LOADING_BAR_TYPE
        else super.getItemViewType(position)
}