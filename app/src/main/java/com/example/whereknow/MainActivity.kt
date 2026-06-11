package com.example.whereknow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whereknow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners for your buttons
        binding.btnLocation.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
        binding.btnSavedLocations.setOnClickListener {
            startActivity(Intent(this, SavedLocationsActivity::class.java))
        }




    }
}