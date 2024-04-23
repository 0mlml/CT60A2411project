package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;
import lombok.Getter;

/**
 * This class extends DataFetcher and is responsible for fetching city data.
 * It includes methods for formatting the request body, fetching data for a specific area, and fetching data for multiple areas.
 */
public class CityDataFetcher extends DataFetcher {
    @Getter
    private final static HashMap<String, CityData> cache = new HashMap<>(); // Cache for storing fetched city data

    private final static String endpoint = "https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"; // The endpoint for fetching city data
    private final static String requestBody = "{\"query\":[{\"code\":\"Vuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"2022\"]}},{\"code\":\"Alue\",\"selection\":{\"filter\":\"item\",\"values\":[%s]}},{\"code\":\"Tiedot\",\"selection\":{\"filter\":\"item\",\"values\":[\"vm01\",\"vm11\",\"vm41\",\"vm42\",\"vm2126\",\"vm3136\",\"kokmuutos\",\"vaesto\"]}}],\"response\":{\"format\":\"json\"}}"; // The request body for fetching city data

    /**
     * This method formats the request body with the specified areas.
     *
     * @param areas The areas to include in the request body.
     * @return The formatted request body.
     */
    private static String formatRequestBody(String... areas) {
        StringBuilder areaString = new StringBuilder();
        for (String area : areas) {
            areaString.append("\"").append(area).append("\",");
        }
        return String.format(requestBody, areaString);
    }

    /**
     * This method fetches data for a specific area.
     * If the data is already in the cache, it returns the cached data.
     * Otherwise, it fetches the data and adds it to the cache.
     *
     * @param area The area to fetch data for.
     * @return A Future of the fetched CityData.
     */
    public static Future<CityData> fetchArea(String area) {
        FutureTask<CityData> futureTask = new FutureTask<>(() -> {
            Log.d("CityDataFetcher", String.format("Fetching data for %s.", area));
            if (cache.containsKey(area)) {
                Log.d("CityDataFetcher", String.format("Data for %s already in cache.", area));
                return cache.get(area);
            }
            fetchAreas(area).get();
            return cache.get(area);
        });
        new Thread(futureTask).start();
        return futureTask;
    }

    /**
     * This method fetches data for multiple areas.
     * It does not check the cache before fetching the data.
     * After fetching the data, it adds the data to the cache.
     *
     * @param areas The areas to fetch data for.
     * @return A Future of a map of the fetched CityData, keyed by area.
     */
    public static Future<HashMap<String, CityData>> fetchAreas(String... areas) {
        FutureTask<HashMap<String, CityData>> futureTask = new FutureTask<>(() -> {
            Log.d("CityDataFetcher", String.format("Fetching data for %s. Using bulk fetch, so there is no cache check.", String.join(", ", areas)));
            URL url;
            try {
                url = new URL(endpoint);
            } catch (Exception e) {
                Log.e("CityDataFetcher", "Failed to create URL.");
                return null;
            }

            String requestBody = formatRequestBody(areas);
            String response;
            try {
                response = post(url, requestBody).get();
            } catch (Exception e) {
                Log.e("CityDataFetcher", "Failed to fetch data.");
                return null;
            }
            cache.putAll(CityData.parseData(response));
            return cache;
        });
        new Thread(futureTask).start();
        return futureTask;
    }
}