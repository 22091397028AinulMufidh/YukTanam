package com.example.yuktanam.ui.challenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yuktanam.R

class ChallengeAdapter(private val challenges: List<Challenge>) :
    RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    inner class ChallengeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_challenge_title)
        val description: TextView = view.findViewById(R.id.tv_challenge_description)
        val progress: ProgressBar = view.findViewById(R.id.progress_challenge)
        val reward: TextView = view.findViewById(R.id.tv_challenge_reward)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.challenge_item, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.title.text = challenge.title
        holder.description.text = challenge.description
        holder.progress.progress = challenge.progress
        holder.reward.text = challenge.reward
    }

    override fun getItemCount() = challenges.size
}