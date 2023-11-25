package com.example.instapets.ui.core

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instapets.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

object Extensions {

    val RecyclerView.lastVisibleItem: Flow<Int>
        get() = callbackFlow {
            val layout = layoutManager as LinearLayoutManager

            val listener =
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        launch { send(layout.findLastVisibleItemPosition()) }
                    }
                }

            addOnScrollListener(listener)
            awaitClose { removeOnScrollListener(listener) }

        }.conflate()

    fun View.visibility(show: Boolean) {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    fun AppCompatImageButton.loadImage(url : String, ){
        val glide = Glide.with(context)
        if (url.endsWith(".gif")) {
            glide.asGif()
                .load(url)
                .placeholder(R.color.separator_color)
                .error(R.drawable.no_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)

        } else {
            glide.asBitmap()
                .load(url)
                .placeholder(R.color.separator_color)
                .error(R.drawable.no_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }

}

