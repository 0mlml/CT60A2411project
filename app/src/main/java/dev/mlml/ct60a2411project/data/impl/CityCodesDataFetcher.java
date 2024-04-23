package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;
import lombok.Getter;

public class CityCodesDataFetcher extends DataFetcher {
    @Getter
    private static CityCodesData data;

    private final static String endpoint = "https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px";

    @Getter
    private final static HashMap<String, String> regionsMap = new HashMap<>(); // region code -> region name

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            Log.w("CityCodesDataFetcher", "Already initialized.");
            return;
        }
        initialized = true;

        Log.d("CityCodesDataFetcher", "Fetching data.");
        data = fetchData();

        if (data == null) {
            Log.e("CityCodesDataFetcher", "Failed to fetch data.");
            return;
        }

        for (String region : data.getRegions().values()) {
            regionsMap.put(region, data.getRegions().valueTexts().get(data.getRegions().values().indexOf(region)));
        }

        Log.d("CityCodesDataFetcher", String.format("Fetched %d regions.", regionsMap.size()));
    }

    public static Future<HashMap<String, String>> getRegions() {
        FutureTask<HashMap<String, String>> futureTask = new FutureTask<>(() -> {
            while (regionsMap.isEmpty()) {
                if (!initialized) {
                    init();
                }
                Thread.sleep(100);
            }
            return regionsMap;
        });
        new Thread(futureTask).start();
        return futureTask;
    }

    private static CityCodesData fetchData() {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String response;
        try {
            response = get(url).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (response == null) {
            Log.e("CityCodesDataFetcher", "Failed to fetch data.");
            return null;
        }

        return new CityCodesData(response);
    }
}
