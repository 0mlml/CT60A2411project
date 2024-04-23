package dev.mlml.ct60a2411project.data.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import lombok.Data;

/**
 * This class represents the data for weather.
 * It includes nested classes for CoordinateData, WeatherInfo, Coord, Weather, Main, Wind, Clouds, and Sys.
 * It also includes methods for parsing the coordinates from a JSON string.
 */
@Data
public class WeatherData {
    private final double lat;
    private final double lon;
    private final double temperature;
    private final double feelsLike;
    private final double minTemperature;
    private final double maxTemperature;
    private final int pressure;
    private final int humidity;
    private final int seaLevel;
    private final int groundLevel;
    private final int visibility;
    private final double windSpeed;
    private final int windDirection;
    private final double windGust;
    private final int cloudiness;
    private final String country;
    private final long sunrise;
    private final long sunset;
    /**
     * This constructor initializes the weather data from a JSON string and an array of coordinates.
     * It uses Gson to parse the JSON into a WeatherInfo object.
     * It then initializes the weather data from the WeatherInfo object and the coordinates.
     *
     * @param json   The JSON string to initialize the data from.
     * @param coords The coordinates to initialize the data from.
     */
    public WeatherData(String json, double[] coords) {
        Gson gson = new Gson();
        WeatherInfo data = gson.fromJson(json, WeatherInfo.class);

        lat = coords[0];
        lon = coords[1];

        temperature = data.main.temp;
        feelsLike = data.main.feels_like;
        minTemperature = data.main.temp_min;
        maxTemperature = data.main.temp_max;
        pressure = data.main.pressure;
        humidity = data.main.humidity;
        seaLevel = data.main.sea_level;
        groundLevel = data.main.grnd_level;
        visibility = data.visibility;
        windSpeed = data.wind.speed;
        windDirection = data.wind.deg;
        windGust = data.wind.gust;
        cloudiness = data.clouds.all;
        country = data.sys.country;
        sunrise = data.sys.sunrise;
        sunset = data.sys.sunset;
    }

    /**
     * This method parses the coordinates from a JSON string.
     * It uses Gson to parse the JSON into a list of CoordinateData objects.
     *
     * @param json The JSON string to parse the coordinates from.
     * @return An array of the latitude and longitude of the first CoordinateData object in the list.
     */
    public static double[] parseCoordinates(String json) {
        Gson gson = new Gson();
        Type locationListType = new TypeToken<List<CoordinateData>>() {
        }.getType();
        List<CoordinateData> locs = gson.fromJson(json, locationListType);
        return new double[]{locs.get(0).lat, locs.get(0).lon};
    }

    /**
     * This class represents the coordinate data for weather.
     * It includes latitude and longitude.
     */
    public static class CoordinateData {
        double lat;
        double lon;
    }

    /**
     * This class represents the weather information.
     * It includes coordinate, weather, main, wind, clouds, sys, date time of calculation, timezone, visibility, base, name, id, and cod.
     */
    public static class WeatherInfo {
        Coord coord;
        List<Weather> weather;
        Main main;
        Wind wind;
        Clouds clouds;
        Sys sys;
        long dt;           // Date time of calculation
        long timezone;
        int visibility;
        String base;
        String name;
        int id;
        int cod;
    }

    /**
     * This class represents the coordinate in the weather information.
     * It includes longitude and latitude.
     */
    public static class Coord {
        double lon;
        double lat;
    }

    /**
     * This class represents the weather in the weather information.
     * It includes id, main, description, and icon.
     */
    public static class Weather {
        int id;
        String main;
        String description;
        String icon;
    }

    /**
     * This class represents the main in the weather information.
     * It includes temperature, feels_like, temp_min, temp_max, pressure, humidity, sea_level, and grnd_level.
     */
    public static class Main {
        double temp;
        double feels_like;
        double temp_min;
        double temp_max;
        int pressure;
        int humidity;
        int sea_level;
        int grnd_level;
    }

    /**
     * This class represents the wind in the weather information.
     * It includes speed, deg, and gust.
     */
    public static class Wind {
        double speed;
        int deg;
        double gust;
    }

    /**
     * This class represents the clouds in the weather information.
     * It includes all (cloudiness, %).
     */
    public static class Clouds {
        int all;  // Cloudiness, %
    }

    /**
     * This class represents the sys in the weather information.
     * It includes country, sunrise, and sunset.
     */
    public static class Sys {
        String country;
        long sunrise;
        long sunset;
    }
}