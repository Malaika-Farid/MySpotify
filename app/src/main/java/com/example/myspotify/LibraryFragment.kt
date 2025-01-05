package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myspotify.adapters.ArtistAdapter
import com.example.myspotify.models.Artist
import com.example.myspotify.models.Song
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LibraryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artistAdapter: ArtistAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewArtists)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        auth = FirebaseAuth.getInstance()

        // Get the current user
        val user = auth.currentUser

        // Update the username in the navigation header
        updateNavHeader(view, user)
        artistAdapter = ArtistAdapter(emptyList())
        recyclerView.adapter = artistAdapter

        // Fetch data from Firestore
        fetchArtistsFromFirestore()

        drawerLayout = view.findViewById(R.id.drawer_layout)
        navView = view.findViewById(R.id.nav_view)

        val circleButton: Button = view.findViewById(R.id.button_circle)
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
    private fun updateNavHeader(view: View, user: FirebaseUser?) {
        val navView: NavigationView = view.findViewById(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val currUserTextView: TextView = headerView.findViewById(R.id.currUser)
        currUserTextView.text = user?.displayName ?: "Guest"
        val buttonCirle : Button = headerView.findViewById(R.id.button_circle)
        val firstLetter = user?.displayName?.take(1)?.uppercase() ?: "G" // "G" for Guest
        buttonCirle.text = firstLetter
    }
    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun fetchArtistsFromFirestore() {
        Toast.makeText(context, "Fetching artists...", Toast.LENGTH_SHORT).show()
        Log.d("LibraryFragment", "Starting Firestore fetch.")

        firestore.collection("Artists")
            .get()
            .addOnSuccessListener { documents ->
                Toast.makeText(context, "Artists fetched successfully!", Toast.LENGTH_SHORT).show()
                Log.d("LibraryFragment", "Fetched ${documents.size()} artists.")

                val artists = documents.mapNotNull {
                    try {
                        it.toObject(Artist::class.java).also {
                            Log.d("LibraryFragment", "Artist fetched: $it")
                        }
                    } catch (e: Exception) {
                        Log.e("LibraryFragment", "Error mapping artist: ${e.message}")
                        null
                    }
                }

                if (artists.isNotEmpty()) {
                    artistAdapter.updateArtists(artists)
                    Toast.makeText(context, "Displaying ${artists.size} artists.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No artists found in Firestore.", Toast.LENGTH_SHORT).show()
                    Log.d("LibraryFragment", "No artists found.")
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to fetch artists: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("LibraryFragment", "Error fetching artists from Firestore: ${e.message}")
            }
    }
}
