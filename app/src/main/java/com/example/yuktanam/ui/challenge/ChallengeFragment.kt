package com.example.yuktanam.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yuktanam.databinding.FragmentChallengeBinding

class ChallengeFragment : Fragment() {

    private var _binding: FragmentChallengeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val challengeViewModel =
            ViewModelProvider(this).get(ChallengeViewModel::class.java)

        _binding = FragmentChallengeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        challengeViewModel.challenges.observe(viewLifecycleOwner) { challenges ->
            val adapter = ChallengeAdapter(challenges)
            binding.rvChallenges.adapter = adapter
            binding.rvChallenges.layoutManager = LinearLayoutManager(context)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}