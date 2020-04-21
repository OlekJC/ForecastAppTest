package com.example.forecastapp.di.modules

import android.content.Context
import com.example.forecastapp.data.provider.UnitProvider
import com.example.forecastapp.data.provider.UnitProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UnitsModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideUnitProvider(): UnitProvider {
        return UnitProviderImpl(context)
    }
}