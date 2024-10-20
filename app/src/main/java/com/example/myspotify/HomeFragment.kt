package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import android.widget.Button
import android.widget.LinearLayout

class HomeFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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
                    // Handle the third item
                    true
                }
                else -> false
            }
        }

//        val headerLayout = LayoutInflater.from(context).inflate(R.layout.nav_header, null)
//
//        // Set click listener on the entire header layout
//        headerLayout.setOnClickListener {
//            // Create an instance of the ProfileFragment
//            val profileFragment = ProfileFragment()
//
//            // Use FragmentManager to replace the current fragment with ProfileFragment
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, profileFragment) // Ensure this ID exists in your activity layout
//                .addToBackStack(null) // Add to back stack to allow back navigation
//                .commit()
//        }
//
//        val parentLayout = view.findViewById<LinearLayout>(R.id.nav_view) // Adjust to your layout
//        parentLayout.addView(headerLayout)

    }
}
