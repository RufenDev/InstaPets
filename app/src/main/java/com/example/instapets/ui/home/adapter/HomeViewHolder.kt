package com.example.instapets.ui.home.adapter

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.instapets.R.color.red
import com.example.instapets.R.color.title_color
import com.example.instapets.R.drawable.ic_like_empty
import com.example.instapets.R.drawable.ic_like_fill
import com.example.instapets.R.drawable.ic_save_empty
import com.example.instapets.R.drawable.ic_save_fill
import com.example.instapets.databinding.HomeItemBinding
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.ui.core.Extensions.loadImage
import com.example.instapets.ui.home.adapter.HomeAction.*

class HomeViewHolder(view: View) : ViewHolder(view) {

    private val binding = HomeItemBinding.bind(view)

    @SuppressLint("ClickableViewAccessibility")
    fun bind(pet: HomePetModel, onHomeAction: (HomeAction) -> Unit) {
        isPetLiked = pet.isPetLiked
        isPetSaved = pet.isPetSaved

        binding.tvHomePetBreed.text = pet.breeds.firstOrNull()?.title ?: ""

        binding.ivHomeImage.loadImage(pet.image)

        binding.btnHomeLike.setOnClickListener {
            isPetLiked = !isPetLiked
            onHomeAction(OnPetLiked(pet, isPetLiked))
        }

        binding.btnHomeSave.setOnClickListener {
            isPetSaved = !isPetSaved
            onHomeAction(OnPetSaved(pet))
        }

        binding.btnHomeMoreOptions.setOnClickListener {
            onHomeAction(OnMoreOptions(pet, binding.btnHomeMoreOptions))
        }

        val likeGesture = GestureDetectorCompat(itemView.context, imageGestures(pet, onHomeAction))
        binding.ivHomeImage.setOnTouchListener { _, evt ->
            likeGesture.onTouchEvent(evt)
            false
        }
    }

    private fun imageGestures(pet: HomePetModel, onHomeAction: (HomeAction) -> Unit) =
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                Toast.makeText(itemView.context, "Single", Toast.LENGTH_SHORT).show()
                onHomeAction(SeePetDescription(pet.id, pet.type))
                return false
            }
            override fun onDoubleTap(e: MotionEvent): Boolean {
                Toast.makeText(itemView.context, "Double", Toast.LENGTH_SHORT).show()
                petLikeAnimation()
                onHomeAction(OnPetLiked(pet, true))
                return false
            }
        }

    private fun petLikeAnimation() {
        isPetLiked = true
    }

    private var isPetLiked = false
        set(value) {
            binding.btnHomeLike.apply {
                setImageResource(if (value) ic_like_fill else ic_like_empty)
                setColorFilter(itemView.context.getColor(if (value) red else title_color))
            }
            field = value
        }

    private var isPetSaved = false
        set(value) {
            binding.btnHomeSave.setImageResource(if (value) ic_save_fill else ic_save_empty)
            field = value
        }
}