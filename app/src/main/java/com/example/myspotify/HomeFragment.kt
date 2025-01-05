package com.example.myspotify
import android.media.MediaPlayer
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.GravityCompat
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import com.example.myspotify.adapters.SongAdapter
import com.example.myspotify.models.Song
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference

class HomeFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var firestore: FirebaseFirestore
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Get the current user
        val user = auth.currentUser

        // Update the username in the navigation header
        updateNavHeader(view, user)
//        testFirestoreConnection()
        fetchSongsData(view)
        drawerLayout = view.findViewById(R.id.drawer_layout)
        navView = view.findViewById(R.id.nav_view)
        val circleButton: Button = view.findViewById(R.id.button_circle)

        // Open the navigation drawer
        circleButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    startActivity(Intent(requireActivity(), AddAccountActivity::class.java))
                    true
                }
                R.id.nav_item2 -> {
                    replaceFragment(WhatsNewFragment())
                    true
                }
                R.id.nav_item3 -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.nav_item4 -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun updateNavHeader(view : View ,user: FirebaseUser?) {
        // Find the NavigationView
        val navView: NavigationView = view.findViewById(R.id.nav_view)

        // Access the header view
        val headerView = navView.getHeaderView(0)

        // Find the currUser TextView in the header layout
        val currUserTextView: TextView = headerView.findViewById(R.id.currUser)

        // Update the TextView with the user's display name
        currUserTextView.text = user?.displayName ?: "Guest"
    }

    private fun fetchSongsData(view: View) {
        try {
            Toast.makeText(requireContext(), "Fetching songs data...", Toast.LENGTH_SHORT).show()

            val recyclerView1: RecyclerView = view.findViewById(R.id.recycler_recommendations1)
            val recyclerView2: RecyclerView = view.findViewById(R.id.recycler_recommendations2)
            // Assume you have a RecyclerView in your layout
            val songsList = mutableListOf<Song>() // Song is your data class

            firestore.collection("Songs")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    try {
                        val tasks = mutableListOf<Task<com.google.firebase.firestore.DocumentSnapshot>>() // For artist fetches

                        for (document in querySnapshot.documents) {
                            val title = document.getString("Title") ?: "Unknown Title"
                            val imageUrl = document.getString("CoverImageURL") ?: "No Image"
                            val audioURL = document.getString("AudioURL") ?: "Not Found"
                            val album = document.getString("Album") ?: "Unknown Album"
                            val duration = document.getLong("Duration")?.toInt() ?: 0
                            val genre = document.getString("Genre") ?: "Unknown Genre"
                            val releaseDate = document.getTimestamp("ReleaseDate")
                            val artistRef = document.get("ArtistID") as? DocumentReference

                            if (artistRef != null) {
                                // Fetch artist document
                                val task = artistRef.get()
                                tasks.add(task) // Add the task to the list
                                task.addOnSuccessListener { artistDoc ->
                                    val artistName = artistDoc.getString("Name") ?: "Unknown Artist"

                                    // Add the song to the list
                                    songsList.add(
                                        Song(
                                            title = title,
                                            album = album,
                                            artistId = artistName,
                                            audioUrl = audioURL,
                                            imageUrl = imageUrl,
                                            duration = duration,
                                            genre = genre,
                                            releaseDate = releaseDate
                                        )
                                    )
                                }
                            } else {
                                // Add the song to the list with a default artist
                                songsList.add(
                                    Song(
                                        title = title,
                                        album = album,
                                        artistId = "Unknown Artist",
                                        audioUrl = audioURL,
                                        imageUrl = imageUrl,
                                        duration = duration,
                                        genre = genre,
                                        releaseDate = releaseDate
                                    )
                                )
                            }
                        }

                        // Wait for all artist fetch tasks to complete
                        Tasks.whenAllComplete(tasks).addOnCompleteListener {
                            // Initialize RecyclerView adapter
                            val adapter = SongAdapter(requireContext(), songsList) { song ->
                                playSong(song)  // When a song is clicked, play the song
                            }
                            recyclerView1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            recyclerView1.adapter = adapter
                            recyclerView2.adapter = adapter
                            Toast.makeText(requireContext(), "Songs fetched successfully", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("Firestore", "Error processing documents", e)
                        Toast.makeText(requireContext(), "Error processing songs data", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error fetching songs", e)
                    Toast.makeText(requireContext(), "Failed to fetch songs data", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Log.e("Firestore", "Unexpected error in fetchSongsData", e)
            Toast.makeText(requireContext(), "Unexpected error occurred", Toast.LENGTH_SHORT).show()
        }
    }
    private fun playSong(song: Song) {
        try {
            // Stop the previous song if it's playing
            mediaPlayer?.stop()
            mediaPlayer?.reset()

            // Prepare the new song to play
            mediaPlayer = MediaPlayer().apply {
                setDataSource(requireContext(), Uri.parse(song.audioUrl))  // Use the song's audio URL
                prepare()  // Prepare the media player
                start()  // Start playing the song
            }
            Toast.makeText(requireContext(), "Playing: ${song.title}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MediaPlayer", "Error playing song", e)
            Toast.makeText(requireContext(), "Error playing song", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }



    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun testFirestoreConnection() {
        firestore.collection("Songs")
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    // Show Toast instead of Log.d
                    Toast.makeText(requireContext(), "Got documents: ${documents.size()}", Toast.LENGTH_SHORT).show()
                } else {
                    // Show Toast instead of Log.d
                    Toast.makeText(requireContext(), "No documents found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                // Show Toast instead of Log.w
                Toast.makeText(requireContext(), "Error getting documents.", Toast.LENGTH_SHORT).show()
            }
    }

}