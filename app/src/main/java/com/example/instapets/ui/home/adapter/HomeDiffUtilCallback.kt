package com.example.instapets.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.instapets.domain.model.home.HomePetModel

object HomeDiffUtilCallback : DiffUtil.ItemCallback<HomePetModel>() {

    override fun areItemsTheSame(oldItem: HomePetModel, newItem: HomePetModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: HomePetModel, newItem: HomePetModel) =
        oldItem == newItem

}