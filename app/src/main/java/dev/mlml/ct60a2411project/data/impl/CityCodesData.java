package dev.mlml.ct60a2411project.data.impl;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import lombok.Data;

/**
 * This class represents the data for city codes.
 * It includes nested classes for Statistics and Variable, and a record for VariableDataEntry.
 * It also includes methods for getting area by name and initializing the data from a JSON string.
 */
@Data
public class CityCodesData {
    private Statistics statistics;
    private VariableDataEntry years;
    private VariableDataEntry regions;
    private VariableDataEntry informations;

    /**
     * This constructor initializes the city codes data from a JSON string.
     * It uses Gson to parse the JSON into a Statistics object.
     * It also logs the title of the fetched data and initializes the years, regions, and informations data entries.
     *
     * @param json The JSON string to initialize the data from.
     */
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

    /**
     * This method returns the area by its name.
     * If the area is not found, it logs a warning and returns null.
     *
     * @param name The name of the area.
     * @return The area if found, null otherwise.
     */
    public String getAreaByName(String name) {
        int index = regions.valueTexts.indexOf(name);
        if (index == -1) {
            Log.w("CityCodesData", String.format("Area %s not found.", name));
            return null;
        }
        return regions.values.get(index);
    }

    /**
     * This record represents an entry in the variable data.
     * It includes text, values, and valueTexts.
     */
    protected record VariableDataEntry(
            String text,
            List<String> values,
            List<String> valueTexts) {
    }

    /**
     * This class represents the statistics of the city codes data.
     * It includes a title and a list of variables.
     */
    private class Statistics {
        String title;
        List<Variable> variables;
    }

    /**
     * This class represents a variable in the city codes data.
     * It includes a code, text, values, valueTexts, time, elimination, and map.
     */
    private class Variable {
        String code;
        String text;
        List<String> values;
        List<String> valueTexts;
        Boolean time;
        Boolean elimination;
        String map;
    }
}