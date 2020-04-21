package com.example.forecastapp.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastapp.R
import com.example.forecastapp.data.constants.CELSIUS_SIGN
import com.example.forecastapp.data.db.entity.CurrentWeatherResponse
import com.example.forecastapp.data.db.entity.TODAY_INDEX
import com.example.forecastapp.di.components.DaggerAppComponent
import com.example.forecastapp.di.modules.NetworkModule
import com.example.forecastapp.di.modules.RepositoryModule
import com.example.forecastapp.di.modules.UnitsModule
import com.example.forecastapp.di.modules.ViewModelModule
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CurrentWeatherViewModelFactory
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule(requireContext()))
            .repositoryModule(RepositoryModule(requireContext()))
            .viewModelModule(ViewModelModule())
            .unitsModule(UnitsModule(requireContext()))
            .build()
            .inject(this)

        super.onActivityCreated(savedInstanceState)
        constant_labels.visibility=View.GONE
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() {
        setActionBar()
        CoroutineScope(IO).launch {
            val currentWeather = viewModel.weather.await()
            withContext(Main) {
                currentWeather.observe(viewLifecycleOwner, Observer {
                    if (it == null) return@Observer
                    group_loading.visibility = View.GONE
                    constant_labels.visibility=View.VISIBLE
                    setDayForecast(it)
                    setDayIcon(it)
                    setNightForecast(it)
                    setNightIcon(it)
                    /*GlideApp.with(this@CurrentWeatherFragment)
                        .load()*/
                })
            }
        }
    }

    private fun setActionBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getText(R.string.warsaw_city)
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = getText(R.string.today)
    }

    private fun setNightIcon(currentWeather: CurrentWeatherResponse) {
        val weather = currentWeather.dailyForecasts[TODAY_INDEX]
        val mDrawableName = "ic_${weather.night.icon}"
        val resID = resources.getIdentifier(mDrawableName, "drawable", requireContext().packageName)
        imageView_condition_night.setImageResource(resID)
    }

    private fun setDayIcon(currentWeather: CurrentWeatherResponse) {
        val weather = currentWeather.dailyForecasts[TODAY_INDEX]
        val mDrawableName = "ic_${weather.day.icon}"
        val resID = resources.getIdentifier(mDrawableName, "drawable", requireContext().packageName)
        imageView_condition_day.setImageResource(resID)
    }

    private fun setDayForecast(currentWeather: CurrentWeatherResponse) {
        val weather = currentWeather.dailyForecasts[TODAY_INDEX]
        //Set headline
        textView_condition_day.text = weather.day.iconPhrase
        //Set temperature
        textView_temperature_day.text = "${weather.temperature.maximum.value} $CELSIUS_SIGN"
        //Has Precipitation
        textView_precipitation_day.text =
            if (weather.day.hasPrecipitation) "Prognozowane opady" else "Brak opadów"
    }

    private fun setNightForecast(currentWeather: CurrentWeatherResponse) {
        val weather = currentWeather.dailyForecasts[TODAY_INDEX]
        //Set headline
        textView_condition_night.text = weather.night.iconPhrase
        //Set temperature
        textView_temperature_night.text = "${weather.temperature.minimum.value} $CELSIUS_SIGN"
        //Has Precipitation
        textView_precipitation_night.text =
            if (weather.night.hasPrecipitation) "Prognozowane opady" else "Brak opadów"
    }


    private fun getWeatherText(response: CurrentWeatherResponse): String {
        val tMax = response.dailyForecasts[TODAY_INDEX].temperature.maximum.value
        val phrase = response.dailyForecasts[TODAY_INDEX].day.iconPhrase
        return "Temperatura w Warszawie wynosi $tMax C\n $phrase"
    }
}
