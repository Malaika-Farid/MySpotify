package com.example.myspotify

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MusicPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()  // Close the activity
        }

        val playPauseButton: ImageView = findViewById(R.id.playPauseButton)
        playPauseButton.setOnClickListener {
            // Toggle play/pause functionality
        }
    }
}
