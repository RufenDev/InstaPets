package com.example.instapets.core

// Recomendación: Ser una sealed class para que los when() no funcionen si los types aumentan
enum class PetTypes(val value:Int) {
    CAT(0),
    DOG(1),
    PETS(2)
}