// LocationData.kt
package com.example.whereknow

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LocationData {
    suspend fun getLocations(context: Context): List<Location> {
        return withContext(Dispatchers.IO) {
            AppDatabase.getDatabase(context).locationDao().getAllLocations()
        }
    }
}