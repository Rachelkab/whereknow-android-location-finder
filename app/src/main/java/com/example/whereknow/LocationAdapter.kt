// LocationAdapter.kt
package com.example.whereknow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter(
    private var locations: List<Location>,
    private val onItemClick: (Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textLocationName)
        val iconView: ImageView = view.findViewById(R.id.iconLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locations[position]
        holder.textView.text = location.name
        holder.itemView.setOnClickListener { onItemClick(location) }
    }

    override fun getItemCount() = locations.size

    // New update method
    fun updateLocations(newLocations: List<Location>) {
        locations = newLocations
        notifyDataSetChanged()
    }
}