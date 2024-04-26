package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;

/**
 * This class extends DataFetcher and is responsible for fetching weather data.
 * It includes a method for getting weather data for a specific city.
 */
public class WeatherDataFetcher extends DataFetcher {
    private final static HashMap<String, WeatherData> cache = new HashMap<>(); // Cache for storing fetched weather data

    private final static String geoQueryEndpoint = "https://api.openweathermap.org/geo/1.0/direct?q=%s,FI&limit=1&appid=dcecd0d1600d4eb8f477e45da971cc7b"; // The endpoint for fetching geographic data
    private final static String weatherQueryEndpoint = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&appid=dcecd0d1600d4eb8f477e45da971cc7b"; // The endpoint for fetching weather data

    /**
     * This method populates the cache with weather data for a list of city names.
     * It iterates over the list of names and fetches the weather data for each city.
     * It then adds the weather data to the cache.
     *
     * @param names The list of city names to fetch weather data for.
     */
    public static void populateCache(List<String> names) {
        for (String name : names) {
            getWeatherData(name);
        }
    }

    /**
     * This method gets weather data for a specific city.
     * If the data is already in the cache, it returns the cached data.
     * Otherwise, it fetches the geographic data, then fetches the weather data, and adds the weather data to the cache.
     *
     * @param city The city to get weather data for.
     * @return A Future of the fetched WeatherData.
     */
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

            WeatherData weatherData = new WeatherData(weatherResponse, coordinates);

            cache.put(city, weatherData);
            return weatherData;
        });

        new Thread(futureTask).start();
        return futureTask;
    }
}