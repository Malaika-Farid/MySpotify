package com.example.myspotify

import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.FirebaseFirestore

class CreatePasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)

        auth = FirebaseAuth.getInstance()
        val backArrow: ImageView = findViewById(R.id.backArrow)
        val passwordEditText: EditText = findViewById(R.id.passwordInput)
        val btnSignUpLogin: Button = findViewById(R.id.nextButton)
        // Handle back navigation
        backArrow.setOnClickListener {
            finish()
        }
        // Handle sign-up logic and navigation
        btnSignUpLogin.setOnClickListener {
            val email = intent.getStringExtra("EMAIL") // Retrieve the email from the previous activity
            val password = passwordEditText.text.toString().trim()
            if (email.isNullOrEmpty()) {
                Toast.makeText(this, "Email is missing. Please go back and try again.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    // Navigate to GenderSelectionActivity
                    val intent = Intent(this, GenderSelectionActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Registration failed
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
