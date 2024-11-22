package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)



        // Find the "Get Start" button and set the click listener
        val startButton: Button = findViewById(R.id.start)
        startButton.setOnClickListener {
            // Navigate to DisplayNotes activity
            val intent = Intent(this@Home, DisplayNotes::class.java)
            startActivity(intent)
        }
    }
}
