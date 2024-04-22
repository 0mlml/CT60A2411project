package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import dev.mlml.ct60a2411project.data.GenericData;
import lombok.Data;

@Data
public class CityCodesData extends GenericData {
    private class Statistics {
        String title;
        List<Variable> variables;
    }

    private class Variable {
        String code;
        String text;
        List<String> values;
        List<String> valueTexts;
        Boolean time;
        Boolean elimination;
        String map;
    }

    protected record VariableDataEntry(
            String text,
            List<String> values,
            List<String> valueTexts) {
    }

    private Statistics statistics;

    private VariableDataEntry years;
    private VariableDataEntry regions;
    private VariableDataEntry informations;

    public String getAreaByName(String name) {
        return regions.valueTexts.get(regions.values.indexOf(name));
    }

    public CityCodesData(String json) {
        Gson gson = new Gson();
        statistics = gson.fromJson(json, Statistics.class);

        Log.d("CityCodesData", String.format("Fetched %s.", statistics.title));

        for (Variable variable : statistics.variables) {
            VariableDataEntry currentDataEntry = new VariableDataEntry(variable.text, variable.values, variable.valueTexts);
            switch (variable.code) {
                case "Vuosi" ->
                        years = currentDataEntry;
                case "Alue" ->
                        regions = currentDataEntry;
                case "Tiedot" ->
                        informations = currentDataEntry;
            }
        }
    }
}
