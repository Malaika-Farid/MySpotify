package com.example.myspotify

import android.content.Intent
import android.widget.ImageView

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GenderSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_selection)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val button1: Button = findViewById(R.id.btnFemale)
        val button2: Button = findViewById(R.id.btnMale)
        val button3: Button = findViewById(R.id.btnPreferNotToSay)

        // Set click listeners for the buttons
        button1.setOnClickListener {
            // Navigate to Activity1
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            // Navigate to Activity2
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            // Navigate to Activity3
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

    }
}
