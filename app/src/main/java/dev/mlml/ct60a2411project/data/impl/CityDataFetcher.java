package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;
import lombok.Getter;

public class CityDataFetcher extends DataFetcher {
    @Getter
    private final static CityData data;

    private final static String endpoint = "https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px";
    private final static String requestBody = "{\"query\":[{\"code\":\"Vuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"2022\"]}},{\"code\":\"Alue\",\"selection\":{\"filter\":\"item\",\"values\":[%s]}},{\"code\":\"Tiedot\",\"selection\":{\"filter\":\"item\",\"values\":[\"vm01\",\"vm11\",\"vm41\",\"vm42\",\"vm2126\",\"vm3136\",\"kokmuutos\",\"vaesto\"]}}],\"response\":{\"format\":\"json\"}}";

    static {
        data = new CityData(new HashMap<>());
    }

    private static String formatRequestBody(String... areas) {
        StringBuilder areaString = new StringBuilder();
        for (String area : areas) {
            areaString.append("\"").append(area).append("\",");
        }
        return String.format(requestBody, areaString);
    }

    public static Future<CityData> fetchAreas(String... areas) {
        FutureTask<CityData> futureTask = new FutureTask<>(() -> {
            Log.d("CityDataFetcher", String.format("Fetching data for %s.", String.join(", ", areas)));
            URL url;
            try {
                url = new URL(endpoint);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            String requestBody = formatRequestBody(areas);
            String response;
            try {
                response = post(url, requestBody).get();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            data.getCities().putAll(SingleCityData.parseData(response));
            return data;
        });
        new Thread(futureTask).start();
        return futureTask;
    }
}
