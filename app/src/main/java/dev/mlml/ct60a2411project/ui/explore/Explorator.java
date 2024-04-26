package dev.mlml.ct60a2411project.ui.explore;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;

import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.WeatherData;
import dev.mlml.ct60a2411project.data.impl.WeatherDataFetcher;
import lombok.SneakyThrows;

public class Explorator {
    private static List<FlatCity> cities;

    public static List<SortedCity> explore(SortingCritera criteria) {
        if (cities == null) {
            init();
        }

        List<SortedCity> mapped = cities.stream().map(fc -> switch (criteria) {
            case Hottest ->
                    new SortedCity(fc.name(), String.format(Locale.getDefault(), "%.1fC", fc.wd().getTemperature()), fc.wd().getTemperature());
            case Populated ->
                    new SortedCity(fc.name(), String.valueOf(fc.cd().getPopulation().value()), fc.cd().getPopulation().value());
            case Immigrated ->
                    new SortedCity(fc.name(), String.valueOf(fc.cd().getImmigration().value()), fc.cd().getImmigration().value());
            case Coldest ->
                    new SortedCity(fc.name(), String.format(Locale.getDefault(), "%.1fC", fc.wd().getTemperature()), fc.wd().getTemperature() * -1);
            case Unpopulated ->
                    new SortedCity(fc.name(), String.valueOf(fc.cd().getPopulation().value()), fc.cd().getPopulation().value() * -1);
            case Emigrated ->
                    new SortedCity(fc.name(), String.valueOf(fc.cd().getEmigration().value()), fc.cd().getEmigration().value());
            case Married ->
                    new SortedCity(fc.name(), String.valueOf(fc.cd().getMarriages().value()), fc.cd().getMarriages().value());
            case Northest ->
                    new SortedCity(fc.name(), String.valueOf(fc.wd().getLat()), fc.wd().getLat());
            case Highest ->
                    new SortedCity(fc.name(), String.valueOf(fc.wd().getSeaLevel()), fc.wd().getSeaLevel());
            case Divorced ->
                    new SortedCity(fc.name(), String.valueOf(fc.cd().getDivorces().value()), fc.cd().getDivorces().value());
            case Southest ->
                    new SortedCity(fc.name(), String.valueOf(fc.wd().getLat()), fc.wd().getLat() * -1);
            case Lowest ->
                    new SortedCity(fc.name(), String.valueOf(fc.wd().getSeaLevel()), fc.wd().getSeaLevel() * -1);
        }).collect(ArrayList::new, List::add, List::addAll);

        mapped.sort(Comparator.comparingDouble(SortedCity::value).reversed());
        return mapped;
    }

    @SneakyThrows
    private static void init() {
        HashMap<String, String> regions = CityCodesDataFetcher.getRegions().get();
        CityDataFetcher.populateCache(regions.keySet().stream().toList());
        WeatherDataFetcher.populateCache(regions.values().stream().toList());
        cities = regions.entrySet().stream().map(entry -> {
            String area = entry.getKey();
            String name = entry.getValue();
            try {
                Future<CityData> cd = CityDataFetcher.fetchArea(area);
                Future<WeatherData> wd = WeatherDataFetcher.getWeatherData(name);
                return new FlatCity(name, cd.get(), wd.get());
            } catch (Exception e) {
                Log.e("Explorator", "Error fetching data.", e);
            }
            return null;
        }).toList();
    }

    public enum SortingCritera {
        Hottest, Populated, Immigrated, Coldest, Unpopulated, Emigrated, Married, Northest, Highest, Divorced, Southest, Lowest;

        public String toId() {
            return String.format("explore%sButton", this.name());
        }
    }

    public record FlatCity(
            String name,
            CityData cd,
            WeatherData wd) {
    }

    public record SortedCity(
            String name,
            String attribute,
            double value) {
    }
}
