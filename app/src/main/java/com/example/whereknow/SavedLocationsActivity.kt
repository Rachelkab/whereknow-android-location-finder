package com.example.whereknow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedLocationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_locations)

        // Load the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListFragment())
            .commit()
    }
}

        // Adapter will be added in Step 3 (not yet!)

