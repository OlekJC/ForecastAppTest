package com.example.forecastapp.data.provider

class UnitsUtils {
    companion object{
        fun getUnitAbbreviation(isMetric: Boolean,metric : String,imperial : String) : String{
            return if(isMetric) metric else imperial
        }
    }
}