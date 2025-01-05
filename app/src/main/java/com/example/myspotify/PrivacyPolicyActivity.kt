package com.example.myspotify

import android.content.Intent
import android.widget.ImageView

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore // Initialize Firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        // Initialize Firestore and FirebaseAuth
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish() // Close the activity
        }

        val btnSignUpLogin: Button = findViewById(R.id.createAccount)
        btnSignUpLogin.setOnClickListener {
            val userName: EditText = findViewById(R.id.userName)
            val user = auth.currentUser // Get the current user

            if (user == null) {
                // If the user is not signed in
                Toast.makeText(this, "User not signed in", Toast.LENGTH_SHORT).show()
            } else {
                user.let {
                    val userEmail = it.email?.replace(".", "_") ?: "" // Replace dot with underscore for document ID
                    createUserPlaylist(userEmail) // Create the user's playlist
                }

                // Update user profile with the username
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(userName.text.toString().trim())
                    .build()

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Username updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(this, "Error updating username", Toast.LENGTH_SHORT).show()
                        }
                    }

                // Navigate to MainActivity after user sign-up and playlist creation
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Close the current activity
            }
        }
    }

    private fun createUserPlaylist(userEmail: String) {
        val playlistRef = firestore.collection("Playlists").document(userEmail)

        // Check if the playlist document exists already (this should only happen when the user signs up)
        playlistRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                // Create the playlist document with an empty songs array
                playlistRef.set(mapOf("Songs" to emptyList<String>()))
                    .addOnSuccessListener {
                        Toast.makeText(this, "Playlist created successfully!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to create playlist: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}

