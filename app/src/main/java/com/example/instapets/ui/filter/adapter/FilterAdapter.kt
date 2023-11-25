package com.example.instapets.ui.filter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.instapets.R
import com.example.instapets.domain.model.filter.FilterModel

class FilterAdapter(
    private val onFilterSelected: (FilterModel) -> Unit
) : ListAdapter<FilterModel, FilterViewHolder>(FilterDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
    )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(currentList[position], onFilterSelected)

        val isTheLastItem = currentList.size.minus(1) == position
        if(isTheLastItem) holder.hideSeparator()
    }

    fun filter(text: String): Boolean {
        return text.isBlank()
    }

}