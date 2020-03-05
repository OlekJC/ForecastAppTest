package com.example.forecastapp.data.db.entity


import androidx.room.Embedded
import com.example.forecastapp.data.db.entity.Maximum
import com.example.forecastapp.data.db.entity.Minimum
import com.google.gson.annotations.SerializedName

data class Temperature(
    @Embedded(prefix = "max_")
    val maximum: Maximum,
    @Embedded(prefix = "min_")
    val minimum: Minimum
)