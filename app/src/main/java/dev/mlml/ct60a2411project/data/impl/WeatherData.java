package dev.mlml.ct60a2411project.data.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import lombok.Data;

@Data
public class WeatherData {
    public static class CoordinateData {
        double lat;
        double lon;
    }

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

    public static class Coord {
        double lon;
        double lat;
    }

    public static class Weather {
        int id;
        String main;
        String description;
        String icon;
    }

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

    public static class Wind {
        double speed;
        int deg;
        double gust;
    }

    public static class Clouds {
        int all;  // Cloudiness, %
    }

    public static class Sys {
        String country;
        long sunrise;
        long sunset;
    }

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

    public static double[] parseCoordinates(String json) {
        Gson gson = new Gson();
        Type locationListType = new TypeToken<List<CoordinateData>>() {
        }.getType();
        List<CoordinateData> locs = gson.fromJson(json, locationListType);
        return new double[]{locs.get(0).lat, locs.get(0).lon};
    }

    public WeatherData(String json) {
        Gson gson = new Gson();
        WeatherInfo data = gson.fromJson(json, WeatherInfo.class);

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
}
