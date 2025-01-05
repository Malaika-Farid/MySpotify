package com.example.myspotify

import android.content.Intent
import android.widget.ImageView
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignUpFreeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_free)
        auth = FirebaseAuth.getInstance()

        val backArrow: ImageView = findViewById(R.id.backArrow)
        val emailEditText: EditText = findViewById(R.id.emailInput)
        val btnSignUpLogin: Button = findViewById(R.id.nextButton)

        // Handle back navigation
        backArrow.setOnClickListener {
            finish()
        }

        // Handle email input and move to the next screen
        btnSignUpLogin.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CreatePasswordActivity::class.java)
                intent.putExtra("EMAIL", email) // Pass the email to the next activity
                startActivity(intent)
            }
        }
    }
}
