package com.example.forecastapp.di.modules

import android.content.Context
import com.example.forecastapp.data.constants.API_KEY
import com.example.forecastapp.data.constants.BASE_URL
import com.example.forecastapp.data.network.*
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule constructor(private var context : Context){

    @Provides
    fun provideWeatherNetworkDataSource(apiService: AccuWeatherApiService) : WeatherNetworkDataSource {
        return WeatherNetworkDataSourceImpl(apiService)
    }

    @Provides
    fun provideAccuWeatherApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): AccuWeatherApiService {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(AccuWeatherApiService::class.java)
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideConnectivityInterceptor() : ConnectivityInterceptor {
        return ConnectivityInterceptorImpl(context.applicationContext)
    }

    @Provides
    fun provideOkHttpClient(
        connectivityInterceptor: ConnectivityInterceptor,
        requestInterceptor: Interceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(connectivityInterceptor)
            .build()
    }

    @Provides
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter(
                    "apikey",
                    API_KEY
                )
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
    }


}