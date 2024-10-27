package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myspotify.R
import com.example.myspotify.adapters.ArtistAdapter
import com.example.myspotify.models.Artist
import com.google.android.material.navigation.NavigationView


class LibraryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artistAdapter: ArtistAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

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

        // Sample artist list
        val artists = listOf(
            Artist("Ali Zafar", "Artist", R.drawable.ali_zafar),
            Artist("Arijit Singh", "Artist", R.drawable.arijit_singh),
            Artist("Atif Aslam", "Artist", R.drawable.atif_aslam)
        )

        artistAdapter = ArtistAdapter(artists)
        recyclerView.adapter = artistAdapter

        drawerLayout = view.findViewById(R.id.drawer_layout) // Ensure your DrawerLayout has this ID
        navView = view.findViewById(R.id.nav_view)

        val circleButton: Button = view.findViewById(R.id.button_circle)
        circleButton.setOnClickListener {
            // Open the drawer when the circle button is clicked
            drawerLayout.openDrawer(GravityCompat.START)
        }


        // Optionally, set up the navigation view item selection listener
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation view item clicks here
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    val intent = Intent(requireActivity(), AddAccountActivity::class.java) // or activity
                    startActivity(intent)
                    true
                }
                R.id.nav_item2 -> {
                    // Handle the second item
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
    private fun replaceFragment(fragment: Fragment) {
        // Get the parent fragment manager or activity's fragment manager
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Use the ID of your container
            .addToBackStack(null) // Optional: add to backstack
            .commit()
    }
}
