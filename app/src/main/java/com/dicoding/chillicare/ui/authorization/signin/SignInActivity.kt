package com.dicoding.chillicare.ui.authorization.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.dicoding.chillicare.R
import com.dicoding.chillicare.data.entitity.UserModel
import com.dicoding.chillicare.databinding.ActivitySignInBinding
import com.dicoding.chillicare.ui.MainActivity
import com.dicoding.chillicare.ui.ViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private val viewModel by viewModels<SignInViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.token.observe(this) {
            if (it != null) {
                Log.e("LoginActivity", it)
                viewModel.saveSession(UserModel(it, true))
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.message.observe(this) {
            showToast(it)
        }

        binding.btnSignIn.setOnClickListener {
            loginAccount()
        }
    }

    private fun loginAccount() {
        binding.apply {
            val email = edSignInEmail.text.toString()
            val password = edSignInPassword.text.toString()
            if (email.isEmpty()|| password.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else {
                viewModel.loginFirebase(email, password)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}