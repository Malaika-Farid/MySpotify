package com.example.myspotify

import android.os.Bundle
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class LogInActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            // Close the current activity and go back
            finish()
        }
    }
}