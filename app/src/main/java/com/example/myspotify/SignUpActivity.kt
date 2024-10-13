package com.example.myspotify

import android.content.Intent  // Import Intent
import android.os.Bundle
import android.widget.Button    // Import Button
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Find the button by ID
        val btnSignUpFree: Button = findViewById(R.id.signupButton)
        btnSignUpFree.setOnClickListener {
            // Navigate to SignUpFreeActivity
            val intent = Intent(this, SignUpFreeActivity::class.java)
            startActivity(intent)
        }

        val btnSignUpLogin: Button = findViewById(R.id.signupLogInButton)
        btnSignUpLogin.setOnClickListener {
            // Navigate to SignUpFreeActivity
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}
