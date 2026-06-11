// ListFragment.kt
package com.example.whereknow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadLocations()
    }

    private fun loadLocations() {
        lifecycleScope.launch {
            val locations = LocationData.getLocations(requireContext())
            adapter = LocationAdapter(locations) { clickedLocation ->
                Intent(requireContext(), LocationDetailActivity::class.java).apply {
                    putExtra("LOCATION_NAME", clickedLocation.name)
                    putExtra("LATITUDE", clickedLocation.latitude)
                    putExtra("LONGITUDE", clickedLocation.longitude)
                }.also { startActivity(it) }
            }
            recyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        loadLocations() // Refresh when returning
    }
}