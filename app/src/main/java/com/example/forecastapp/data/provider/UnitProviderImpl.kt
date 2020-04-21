package com.example.forecastapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.forecastapp.internal.UnitSystem

class UnitProviderImpl(context: Context) : UnitProvider {
    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString("UNIT_SYSTEM",UnitSystem.IMPERIAL.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}