// LocationDao.kt
package com.example.whereknow

import androidx.room.*

@Dao
interface LocationDao {
    @Insert
    suspend fun insert(location: Location)

    @Query("SELECT * FROM locations ORDER BY name ASC")
    suspend fun getAllLocations(): List<Location>
}