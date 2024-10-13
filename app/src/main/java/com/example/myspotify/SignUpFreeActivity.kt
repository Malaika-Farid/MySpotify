package com.example.myspotify

import android.widget.ImageView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SignUpFreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_free)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            // Close the current activity and go back
            finish()
        }
    }
}
