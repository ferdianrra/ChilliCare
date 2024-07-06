package com.dicoding.chillicare.ui.authorization.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.chillicare.R
import com.dicoding.chillicare.databinding.ActivityRegisterBinding
import com.dicoding.chillicare.ui.authorization.WelcomeActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        viewModel.isSuccess.observe(this) {
            if (it) {
                val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.message.observe(this) {
            showToast(it)
        }

        binding.btnCreateAccount.setOnClickListener {
            createAccount()
        }
    }



    private fun createAccount() {
        binding.apply {
            val name = edRegisterName.text.toString()
            val email = edRegisterEmail.text.toString()
            val password = edRegisterPassword.text.toString()
            val confirmPassword = edRegisterConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else if (!password.equals(confirmPassword)) {
                showToast(getString(R.string.error_passwords_not_match))
            } else {
                viewModel.reigsterFirebase(name, email, password)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}