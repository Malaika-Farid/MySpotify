package com.example.myspotify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay of 2 seconds (2000ms) before switching to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()  // Closes the SplashActivity so it doesn't stay in the back stack
        }, 2000)
    }
}
