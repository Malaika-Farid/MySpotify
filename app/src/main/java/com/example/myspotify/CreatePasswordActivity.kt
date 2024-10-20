package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class CreatePasswordActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val btnSignUpLogin: Button = findViewById(R.id.nextButton)
        btnSignUpLogin.setOnClickListener {
            val intent = Intent(this, GenderSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}