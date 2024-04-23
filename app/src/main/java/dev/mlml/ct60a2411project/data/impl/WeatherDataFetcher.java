package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;

public class WeatherDataFetcher extends DataFetcher {
    private final static HashMap<String, WeatherData> cache = new HashMap<>();

    private final static String geoQueryEndpoint = "https://api.openweathermap.org/geo/1.0/direct?q=%s,FI&limit=1&appid=dcecd0d1600d4eb8f477e45da971cc7b";
    private final static String weatherQueryEndpoint = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&appid=dcecd0d1600d4eb8f477e45da971cc7b";

    public static Future<WeatherData> getWeatherData(String city) {
        FutureTask<WeatherData> futureTask = new FutureTask<>(() -> {
            if (cache.containsKey(city)) {
                return cache.get(city);
            }

            String geoQuery = String.format(Locale.ENGLISH, geoQueryEndpoint, city);
            String geoResponse = get(new URL(geoQuery)).get();
            Log.d("WeatherDataFetcher", geoResponse);

            double[] coordinates = WeatherData.parseCoordinates(geoResponse);

            String weatherQuery = String.format(Locale.ENGLISH, weatherQueryEndpoint, coordinates[0], coordinates[1]);
            String weatherResponse = get(new URL(weatherQuery)).get();

            Log.d("WeatherDataFetcher", String.format("Fetched data for %s.", city));

            WeatherData weatherData = new WeatherData(weatherResponse);

            cache.put(city, weatherData);
            return weatherData;
        });

        new Thread(futureTask).start();
        return futureTask;
    }
}
