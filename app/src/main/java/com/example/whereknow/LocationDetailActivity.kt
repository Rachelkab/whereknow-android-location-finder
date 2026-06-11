package com.example.whereknow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LocationDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_detail)

        // Get data from Intent
        val locationName = intent.getStringExtra("LOCATION_NAME") ?: "Unknown Location"
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)

        // Set location details
        findViewById<TextView>(R.id.txtLocationName).text = locationName
        findViewById<TextView>(R.id.txtCoordinates).text =
            "Lat: %.4f, Lng: %.4f".format(latitude, longitude)

        // Set up "View on Map" button
        findViewById<Button>(R.id.btnViewOnMap).setOnClickListener {
            Intent(this, MapsActivity::class.java).apply {
                putExtra("TARGET_LATITUDE", latitude)
                putExtra("TARGET_LONGITUDE", longitude)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }.also { startActivity(it) }
        }
    }
}