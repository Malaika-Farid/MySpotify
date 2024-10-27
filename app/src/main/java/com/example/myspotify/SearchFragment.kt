package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView


class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val myImageView: ImageView = view.findViewById(R.id.ali_zafar)

        // Set an OnClickListener to open the new activity
        myImageView.setOnClickListener {
            val intent = Intent(activity, MusicPlayerActivity::class.java)
            startActivity(intent)
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
