package com.example.yuktanam.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yuktanam.R

data class FavoriteItem(val name: String, val type: String, val imageResId: Int)

class FavoriteViewModel : ViewModel() {

    // Sample list of favorite items
    private val _favoriteItems = MutableLiveData<List<FavoriteItem>>().apply {
        value = listOf(
            FavoriteItem("Leon", "Monstera", R.drawable.plant),
            FavoriteItem("Deona", "Anthurium", R.drawable.tropical),
        )
    }
    val favoriteItems: LiveData<List<FavoriteItem>> = _favoriteItems
}
