# ForecastAppTest

In this repository I'm coding ForecastApp with MVVM architecture. This app is based on ResoCoder tutorial
https://www.youtube.com/watch?v=yDaaM3u389I&list=PLB6lc7nQ1n4jTLDyU2muTBo8xk0dg0D_w

Since this tutorial is from 2018, few things are deprecated. 

(Ex. Getting instance of NetworkInfo to get information about Internet avaliability - I replaced it with class ConnectivityMonitor which uses callbacks for API29 to get info asynchronously)
I'm also planning to replace Kodein DI framework with Dagger2.

This App uses AccuWeather API. If you plan to compile this app, please generate your own API key since mine is limited to 50 calls per day.

If you have any suggestions what I can do better, please don't hesitate to write it.
