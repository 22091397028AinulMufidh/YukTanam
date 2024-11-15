package com.example.yuktanam.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    // LiveData to hold the text data
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to your profile!"
    }

    val text: LiveData<String> = _text
}
