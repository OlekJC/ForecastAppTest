package com.example.forecastapp.data.network

import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "***REMOVED***"
const val BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/"

//Query
// GET /forecasts/v1/daily/1day/274663?apikey=%20%09***REMOVED*** HTTP/1.1


interface AccuWeatherApiService {

    //@GET("forecasts/v1/daily/1day/274663?apikey=%20%09***REMOVED***")
    //suspend fun getCurrentWeather() : CurrentWeatherResponse

    @GET("daily/1day/274663")
    suspend fun getCurrentWeather(
        @Query("language") language: String = "pl",
        @Query("metric") metric: String = "true"
    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): AccuWeatherApiService {
            val requestInterceptor = Interceptor { chain ->
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

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AccuWeatherApiService::class.java)
        }
    }


//    @GET("/forecasts/v1/daily/1day/274663") //?apikey=***REMOVED***
//    fun getCurrentWeather(
//        //@Query("apikey") apiKey : String
//    ) : Deferred<CurrentWeatherResponse>

    /*companion object{
        operator fun invoke(): AccuWeatherApiService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apikey", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://dataservice.accuweather.com")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AccuWeatherApiService::class.java)

        }*/

}