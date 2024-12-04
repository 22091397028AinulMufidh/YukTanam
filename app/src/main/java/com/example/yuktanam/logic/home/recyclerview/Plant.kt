package com.example.yuktanam.logic.home.recyclerview

data class Plant(
    val id: String,
    val name: String,
    val origin: String,
    val imageResource: Int,
    var isFavorite: Boolean
)
