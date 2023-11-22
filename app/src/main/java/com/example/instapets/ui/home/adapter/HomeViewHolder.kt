package com.example.instapets.ui.home.adapter

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.instapets.R.color.red
import com.example.instapets.R.color.separator_color
import com.example.instapets.R.color.title_color
import com.example.instapets.R.drawable.ic_like_empty
import com.example.instapets.R.drawable.ic_like_fill
import com.example.instapets.R.drawable.ic_save_empty
import com.example.instapets.R.drawable.ic_save_fill
import com.example.instapets.R.drawable.no_image
import com.example.instapets.databinding.HomeItemBinding
import com.example.instapets.domain.model.home.HomePetModel
import com.example.instapets.ui.home.adapter.HomeAction.*

class HomeViewHolder(view: View) : ViewHolder(view) {

    private val binding = HomeItemBinding.bind(view)

    @SuppressLint("ClickableViewAccessibility")
    fun bind(pet: HomePetModel, onHomeAction: (HomeAction) -> Unit) {
        isPetLiked = pet.isPetLiked
        isPetSaved = pet.isPetSaved

        binding.tvHomePetBreed.text = pet.breeds.firstOrNull()?.name ?: ""

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

        val glideOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(binding.ivHomeImage.width, 0)
            .centerCrop()

        val glide = Glide.with(itemView.context)
        if (pet.image.endsWith(".gif")) {
            glide.asGif()
                .load(pet.image)
                .placeholder(separator_color)
                .error(no_image)
                .apply(glideOptions)
                .into(binding.ivHomeImage)

        } else {
            glide.asBitmap()
                .load(pet.image)
                .placeholder(separator_color)
                .error(no_image)
                .apply(glideOptions)
                .into(binding.ivHomeImage)
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
                onHomeAction(SeePetDescription(pet.id))
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