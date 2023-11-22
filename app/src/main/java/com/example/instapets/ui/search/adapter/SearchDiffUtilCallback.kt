package com.example.instapets.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.instapets.domain.model.search.SearchPetModel

object SearchDiffUtilCallback : DiffUtil.ItemCallback<SearchPetModel>(){
    override fun areItemsTheSame(oldItem: SearchPetModel, newItem: SearchPetModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SearchPetModel, newItem: SearchPetModel) =
        oldItem == newItem
}