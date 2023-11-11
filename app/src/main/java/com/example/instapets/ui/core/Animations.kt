package com.example.instapets.ui.core

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

object Animations {
    private const val fadeDuration:Long = 100

    fun View.fadeOut(){
        if(this.visibility == View.VISIBLE){
            val fade = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f)
            fade.duration = fadeDuration

            fade.doOnEnd {
                this.visibility = View.INVISIBLE
            }

            fade.start()
        }
    }

    fun View.fadeIn(){
        if(this.visibility == View.INVISIBLE){
            val fade = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f)
            fade.duration = fadeDuration

            fade.doOnStart {
                this.visibility = View.VISIBLE
            }

            fade.start()
        }
    }
}