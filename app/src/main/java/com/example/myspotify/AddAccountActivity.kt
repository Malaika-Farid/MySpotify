package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class AddAccountActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)


        val backArrow: ImageView = findViewById(R.id.Cancel)
        backArrow.setOnClickListener {
            finish()
        }

        val btnSignUpLogin: Button = findViewById(R.id.signupButton)
        btnSignUpLogin.setOnClickListener {
            val intent = Intent(this, SignUpFreeActivity::class.java)
            startActivity(intent)
        }
    }
}