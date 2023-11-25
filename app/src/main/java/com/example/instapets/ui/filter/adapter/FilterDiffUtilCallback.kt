package com.example.instapets.ui.filter.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.instapets.domain.model.filter.FilterModel

object FilterDiffUtilCallback : DiffUtil.ItemCallback<FilterModel>() {
    override fun areItemsTheSame(oldItem: FilterModel, newItem: FilterModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilterModel, newItem: FilterModel) =
        oldItem == newItem
}