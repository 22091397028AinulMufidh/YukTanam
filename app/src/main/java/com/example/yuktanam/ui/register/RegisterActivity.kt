package com.example.yuktanam.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.yuktanam.databinding.ActivityRegisterBinding
import com.example.yuktanam.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol untuk registrasi
        binding.btnRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val passwordConfirm = binding.confirmPasswordEditText.text.toString()

            // Validasi input
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != passwordConfirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Memanggil registerUser dari ViewModel
                registerViewModel.registerUser(
                    name = name,
                    email = email,
                    password = password,
                    passwordConfirm = passwordConfirm,
                    context = this, // Tambahkan context untuk navigasi
                    onSuccess = { message ->
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        // Navigasi ke LoginActivity setelah registrasi berhasil
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    },
                    onError = { errorMessage ->
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        // TEXT BUTTON LOGIN
        val textLogin = binding.textLogin
        textLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}