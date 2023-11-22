package com.example.instapets.core

object CoreExtensions {
    fun String.titleCase() =
        lowercase()
            .split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
}