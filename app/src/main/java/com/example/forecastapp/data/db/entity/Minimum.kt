package com.example.forecastapp.data.db.entity


import com.google.gson.annotations.SerializedName

data class Minimum(
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
    @SerializedName("Value")
    val value: Int
)