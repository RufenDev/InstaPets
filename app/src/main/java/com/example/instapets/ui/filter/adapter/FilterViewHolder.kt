package com.example.instapets.ui.filter.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.instapets.R
import com.example.instapets.databinding.FilterItemBinding
import com.example.instapets.domain.model.filter.FilterModel

class FilterViewHolder(view : View) : ViewHolder(view) {

    private val binding = FilterItemBinding.bind(view)

    fun bind(filter: FilterModel, onFilterSelected: (FilterModel) -> Unit){
        binding.tvFilterList.text = filter.title

        binding.ivFilterList.setImageResource(
            if(filter.petType) R.drawable.cat else R.drawable.dog
        )

        itemView.setOnClickListener { onFilterSelected(filter) }
    }

    fun hideSeparator(){
        binding.filterSeparator.isVisible = false
    }

}