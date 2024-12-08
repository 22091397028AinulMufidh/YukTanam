package com.example.yuktanam.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.yuktanam.databinding.FragmentProfileBinding
import com.example.yuktanam.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Logout logic on click
        val logoutClickListener = View.OnClickListener {
            signOut()
        }
        binding.iconLogout.setOnClickListener(logoutClickListener)
        binding.titleLogout.setOnClickListener(logoutClickListener)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signOut() {
        lifecycleScope.launch {
            try {
                // Inisialisasi CredentialManager
                val credentialManager = CredentialManager.create(requireContext())

                // Logout Firebase
                auth.signOut()

                // Clear credentials state (jika diperlukan)
                credentialManager.clearCredentialState(ClearCredentialStateRequest())

                // Pindah ke LoginActivity
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            } catch (e: Exception) {
                // Tangani error jika ada
                e.printStackTrace()
            }
        }
    }
}