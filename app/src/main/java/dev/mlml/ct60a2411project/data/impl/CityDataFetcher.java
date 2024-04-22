package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import java.net.URL;
import java.util.HashMap;

import dev.mlml.ct60a2411project.data.DataFetcher;

public class CityDataFetcher extends DataFetcher<CityData> {
    private final String endpoint = "https://pxdata.stat.fi:443/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px";
    private final String requestBody = "{\"query\":[{\"code\":\"Vuosi\",\"selection\":{\"filter\":\"item\",\"values\":[\"2022\"]}},{\"code\":\"Alue\",\"selection\":{\"filter\":\"item\",\"values\":[%s]}},{\"code\":\"Tiedot\",\"selection\":{\"filter\":\"item\",\"values\":[\"vm01\",\"vm11\",\"vm41\",\"vm42\",\"vm2126\",\"vm3136\",\"kokmuutos\",\"vaesto\"]}}],\"response\":{\"format\":\"json\"}}";

    public CityDataFetcher() {
        data = new CityData(new HashMap<>());
    }

    private String formatRequestBody(String... areas) {
        StringBuilder areaString = new StringBuilder();
        for (String area : areas) {
            areaString.append("\"").append(area).append("\",");
        }
        return String.format(requestBody, areaString);
    }

    @Override
    protected CityData fetchData() {
        return data;
    }

    protected CityData fetchAreas(String... areas) {
        Log.d("CityDataFetcher", String.format("Fetching data for %d areas.", areas.length));
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
    }
}
