package com.example.myspotify

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use 'view.findViewById' instead of 'findViewById'
        val backArrow: ImageView = view.findViewById(R.id.backArrow)

        backArrow.setOnClickListener {
            // Close the fragment or navigate back
            requireActivity().finish()
        }
    }
}
