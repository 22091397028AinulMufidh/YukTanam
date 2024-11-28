package com.example.yuktanam.ui.challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Challenge(val title: String, val description: String, val progress: Int, val reward: String)

class ChallengeViewModel : ViewModel() {

    private val _challenges = MutableLiveData<List<Challenge>>().apply {
        value = listOf(
            Challenge("Scan tanaman", "Melakukan pemindaian tanaman sebanyak 1 kali", 50, "25"),
            Challenge("Tambahkan tanaman", "Tambahkan sebanyak 3 tanaman di menu beranda", 75, "75"),
            Challenge("Tambahkan tanaman", "Tambahkan sebanyak 10 tanaman di menu beranda", 30, "300"),
            Challenge("Tanaman favorite", "Tambahkan sebanyak 4 tanaman favorit Anda", 60, "60")
        )
    }
    val challenges: LiveData<List<Challenge>> = _challenges
}
