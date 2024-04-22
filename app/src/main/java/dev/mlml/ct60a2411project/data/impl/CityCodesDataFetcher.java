package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;
import lombok.Getter;

public class CityCodesDataFetcher extends DataFetcher<CityCodesData> {
    private final String endpoint = "https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px";

    @Getter
    private final HashMap<String, String> regions = new HashMap<>();

    public CityCodesDataFetcher() {
        Log.d("CityCodesDataFetcher", "Fetching data.");
        data = fetchData();

        for (String region : data.getRegions().values()) {
            regions.put(region, data.getRegions().valueTexts().get(data.getRegions().values().indexOf(region)));
        }

        Log.d("CityCodesDataFetcher", String.format("Fetched %d regions.", regions.size()));
    }

    public Future<HashMap<String, String>> getRegions() {
        FutureTask<HashMap<String, String>> futureTask = new FutureTask<>(() -> {
            while (regions.isEmpty()) {
                Thread.sleep(100);
            }
            return regions;
        });
        new Thread(futureTask).start();
        return futureTask;
    }

    @Override
    protected CityCodesData fetchData() {
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