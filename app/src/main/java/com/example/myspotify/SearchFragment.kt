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
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.graphics.Bitmap
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var auth: FirebaseAuth
    companion object {
        private const val CAMERA_REQUEST_CODE = 100
    }
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
        auth = FirebaseAuth.getInstance()

        // Get the current user
        val user = auth.currentUser

        // Update the username in the navigation header
        updateNavHeader(view, user)
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

        val openCameraIcon: ImageView = view.findViewById(R.id.button_camera)

        // Set an OnClickListener to open the camera
        openCameraIcon.setOnClickListener {
            // Open camera
            checkCameraPermission()
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
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        // Create an Intent to open the camera
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        } else {
            Toast.makeText(requireContext(), "No Camera App Found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // Handle the captured image
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Do something with the imageBitmap (e.g., display it in an ImageView)
            Toast.makeText(requireContext(), "Image Captured!", Toast.LENGTH_SHORT).show()
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
