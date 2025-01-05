package com.example.myspotify

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.io.IOException


class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private var isPlaying = false
    private var songDuration = 0
    private var currentPosition = 0
    private lateinit var currentTimeTextView: TextView
    private lateinit var totalTimeTextView: TextView

    private val playlist: MutableList<String> = mutableListOf() // To keep track of songs in the playlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        handler = Handler()
        firestore = FirebaseFirestore.getInstance()
        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()  // Close the activity
        }

        val songTitle = intent.getStringExtra("songTitle")
        val artistName = intent.getStringExtra("artistName")
        val songUrl = intent.getStringExtra("songUrl")
        val audioUrl = intent.getStringExtra("audioUrl")
        val totalTime = intent.getStringExtra("totalTime")
        val albumName = intent.getStringExtra("albumName")
        val imageURL = intent.getStringExtra("imageURL")

        // Set the song title and artist name
        val titleTextView: TextView = findViewById(R.id.songTitle)
        val artistTextView: TextView = findViewById(R.id.artistName)
        val playlistTextView: TextView = findViewById(R.id.playlistName)
        titleTextView.text = songTitle
        artistTextView.text = artistName
        playlistTextView.text = albumName


        val albumArtImageView: ImageView = findViewById(R.id.albumArt)

        // If album art URL is provided, load it using Picasso or Glide
        if (audioUrl != null) {
            Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.placeholder_image) // Set a placeholder image while loading
                .into(albumArtImageView)
        } else {
            albumArtImageView.setImageResource(R.drawable.error_image) // Set a default or error image
        }

        // Initialize media player and start playing the song
        mediaPlayer = MediaPlayer()

        try {
            mediaPlayer.setDataSource(songUrl)
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start() // Start playing once it's prepared
                isPlaying = true // Update play state
                startSeekBarUpdate() // Start updating seekbar
            }
            mediaPlayer.prepareAsync() // Prepare asynchronously
        } catch (e: IOException) {
            Toast.makeText(applicationContext, "IOException occurred: ${e.message}", Toast.LENGTH_LONG).show()
        }

        mediaPlayer.setOnPreparedListener {
            songDuration = mediaPlayer.duration
            val progressBar: SeekBar = findViewById(R.id.progressBar)
            progressBar.max = songDuration
            totalTimeTextView = findViewById(R.id.totalTime)
            totalTimeTextView.text = formatTime(songDuration)
        }

        // Handle play/pause button
        val playPauseButton: ImageView = findViewById(R.id.playPauseButton)
        playPauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()  // Pause the song
                playPauseButton.setImageResource(R.drawable.ic_play)  // Change to play icon
                isPlaying = false
            } else {
                mediaPlayer.start()  // Resume playing the song
                playPauseButton.setImageResource(R.drawable.ic_pause)  // Change to pause icon
                isPlaying = true
                startSeekBarUpdate() // Ensure seek bar continues updating when playing
            }
        }
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser // Get the current user
        val userEmail = user?.email?.replace(".", "_") ?: "" // Replace dot with underscore for document ID

        val addButton: ImageView = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            // Ensure songId is assigned correctly (replace "songTitle" with the correct song ID from your song object)
            val songId = songTitle ?: "NEWSONG"  // Here, you can replace "songTitle" with the actual song's ID or title
            // Call the function to add the song to the playlist
            addSongToPlaylist(userEmail, songId)
        }

// Remove button click listener
        val removeButton: ImageView = findViewById(R.id.removeButton)
        removeButton.setOnClickListener {
            // Ensure songId is assigned correctly (replace "songTitle" with the correct song ID from your song object)
            val songId = songTitle ?: "NEWSONG"  // Here, you can replace "songTitle" with the actual song's ID or title
            // Call the function to remove the song from the playlist
            removeSongFromPlaylist(userEmail, songId)
        }

        // SeekBar listener to update the current position
        val progressBar: SeekBar = findViewById(R.id.progressBar)
        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress) // Move the song to the selected position
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    private fun addSongToPlaylist(userEmail: String, songId: String) {
        val playlistRef = firestore.collection("Playlists").document(userEmail)

        // Use arrayUnion to add the song reference to the Songs array
        playlistRef.update("Songs", FieldValue.arrayUnion(songId))
            .addOnSuccessListener {
                Toast.makeText(this, "Song added to playlist!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add song to playlist: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to remove a song from the user's playlist
    private fun removeSongFromPlaylist(userEmail: String, songId: String) {
        val playlistRef = firestore.collection("Playlists").document(userEmail)

        // Show a toast when starting the song removal
        Toast.makeText(this, "Attempting to remove song with ID: $songId", Toast.LENGTH_SHORT).show()

        playlistRef.update("Songs", FieldValue.arrayRemove(songId))
            .addOnSuccessListener {
                // Show a toast on success
                Toast.makeText(this, "Song removed from playlist!", Toast.LENGTH_SHORT).show()

                // Add any additional UI updates here after the operation completes successfully
            }
            .addOnFailureListener { e ->
                // Show a toast on failure
                Toast.makeText(this, "Failed to remove song from playlist: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun startSeekBarUpdate() {
        val progressBar: SeekBar = findViewById(R.id.progressBar)

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer.isPlaying) {
                    currentPosition = mediaPlayer.currentPosition
                    progressBar.progress = currentPosition
                    currentTimeTextView = findViewById(R.id.startTime)
                    currentTimeTextView.text = formatTime(currentPosition)
                    handler.postDelayed(this, 1000) // Update every second
                }
            }
        }, 0)
    }


    private fun formatTime(timeInMillis: Int): String {
        val minutes = timeInMillis / 1000 / 60
        val seconds = timeInMillis / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onPause() {
        super.onPause()
        // Release the media player when the activity is paused
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
        mediaPlayer.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the media player when the activity is destroyed
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}