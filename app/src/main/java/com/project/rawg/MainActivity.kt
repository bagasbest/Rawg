package com.project.rawg

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.rawg.databinding.ActivityMainBinding
import com.project.rawg.home.HomeActivity

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        updateUI(auth.currentUser)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        binding.registerBtn.setOnClickListener {
            gotoRegisterPage()
        }
    }

    private fun gotoRegisterPage() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty() || !email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
            showToast(getString(R.string.please_input_valid_email))
            return
        }

        if (password.isEmpty() || password.length < 6) {
            showToast(getString(R.string.please_input_valid_password))
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        Firebase
            .auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                binding.progressBar.visibility = View.GONE
                if (it.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    showToast(it.exception?.message ?: getString(R.string.sign_in_failed))
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}