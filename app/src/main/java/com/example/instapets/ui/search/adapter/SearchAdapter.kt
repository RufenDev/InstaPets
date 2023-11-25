package com.example.instapets.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.instapets.R
import com.example.instapets.domain.model.search.SearchPetModel
import com.example.instapets.ui.core.Extensions.loadImage
import com.example.instapets.ui.core.views.LoadingBarViewHolder.Companion.LOADING_BAR_ID
import com.example.instapets.ui.core.views.LoadingBarViewHolder.Companion.LOADING_BAR_TYPE

class SearchAdapter(
    private val onSearchItemClicked: (SearchPetModel) -> Unit,
) : ListAdapter<SearchPetModel, ViewHolder>(SearchDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.search_item, parent, false)

        return object : ViewHolder(inflater){}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = holder.itemView as? AppCompatImageButton

        image?.apply {
            val pet = currentList[position]

            loadImage(pet.image)
            setOnClickListener { onSearchItemClicked(pet) }
        }
    }

    override fun getItemViewType(position: Int) =
        if (currentList[position].id == LOADING_BAR_ID) LOADING_BAR_TYPE
        else super.getItemViewType(position)
}