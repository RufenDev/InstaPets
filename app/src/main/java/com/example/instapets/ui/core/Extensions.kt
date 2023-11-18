package com.example.instapets.ui.core

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    fun View.visibility(show:Boolean){
        visibility = if(show) View.VISIBLE else View.INVISIBLE
    }
}

