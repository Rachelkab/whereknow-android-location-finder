package com.example.whereknow

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.whereknow.databinding.ActivityMapsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.fabSaveLocation.setOnClickListener {
            showLocationNameDialog()
        }
    }

    private fun showLocationNameDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_name_location, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editLocationName)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Name Your Location")
            .setPositiveButton("Save") { _, _ ->
                val name = editTextName.text.toString().trim()
                if (name.isNotEmpty()) {
                    coroutineScope.launch {
                        saveLocationWithName(name)
                    }
                } else {
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private suspend fun saveLocationWithName(name: String) {
        val currentLatLng = mMap.cameraPosition.target
        val location = Location(
            name = name,
            latitude = currentLatLng.latitude,
            longitude = currentLatLng.longitude
        )

        withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(this@MapsActivity).locationDao().insert(location)
        }

        runOnUiThread {
            Toast.makeText(this, "Saved: $name", Toast.LENGTH_SHORT).show()
            // Add marker for the new location
            mMap.addMarker(
                MarkerOptions()
                    .position(currentLatLng)
                    .title(name)
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap.apply {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }

        val targetLat = intent.getDoubleExtra("TARGET_LATITUDE", Double.NaN)
        val targetLng = intent.getDoubleExtra("TARGET_LONGITUDE", Double.NaN)

        if (!targetLat.isNaN() && !targetLng.isNaN()) {
            val target = LatLng(targetLat, targetLng)
            mMap.addMarker(
                MarkerOptions()
                    .position(target)
                    .title("Saved Location")
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(target, 15f))
        } else {
            // Load saved locations and show markers
            coroutineScope.launch {
                val locations = withContext(Dispatchers.IO) {
                    AppDatabase.getDatabase(this@MapsActivity).locationDao().getAllLocations()
                }
                locations.forEach { location ->
                    mMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(location.latitude, location.longitude))
                            .title(location.name)
                    )
                }
            }
        }
    }
}