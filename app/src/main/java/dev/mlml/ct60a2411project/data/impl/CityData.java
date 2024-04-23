package dev.mlml.ct60a2411project.data.impl;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;

/**
 * This class represents the data for a city.
 * It includes nested classes for StatisticsData, Column, and DataEntry, and a record for IntegerDataEntry.
 * It also includes methods for parsing the data from a JSON string.
 */
@Data
public class CityData {
    private String area;
    private IntegerDataEntry liveBirths; // In data: vm01
    private IntegerDataEntry deaths; // In data: vm11
    private IntegerDataEntry immigration; // In data: vm41
    private IntegerDataEntry emigration; // In data: vm42
    private IntegerDataEntry marriages; // In data: vm2126
    private IntegerDataEntry divorces; // In data: vm3136
    private IntegerDataEntry totalChange; // In data: kokmuutos
    private IntegerDataEntry population; // In data: vaesto
    public CityData() {
    }

    /**
     * This method parses the city data from a JSON string.
     * It uses Gson to parse the JSON into a StatisticsData object.
     * It then iterates over the columns and data entries to populate the city data.
     *
     * @param json The JSON string to parse the data from.
     * @return A map of city data, keyed by area.
     */
    public static HashMap<String, CityData> parseData(String json) {
        Gson gson = new Gson();
        StatisticsData statisticsData = gson.fromJson(json, StatisticsData.class);
        HashMap<String, CityData> data = new HashMap<>();

        List<Column> columns = statisticsData.columns;
        List<List<String>> dataValues = new ArrayList<>();
        for (int i = 0; i < statisticsData.data.size(); i++) {
            dataValues.add(Stream.concat(statisticsData.data.get(i).key.stream(), statisticsData.data.get(i).values.stream()).collect(Collectors.toList()));
            data.put(dataValues.get(dataValues.size() - 1).get(1), new CityData());
        }

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            for (int j = 0; j < dataValues.size(); j++) {
                CityData currentCityData = data.get(dataValues.get(j).get(1));
                switch (column.code) {
                    case "Alue" ->
                            currentCityData.area = dataValues.get(j).get(i);
                    case "vm01" ->
                            currentCityData.liveBirths = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "vm11" ->
                            currentCityData.deaths = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "vm41" ->
                            currentCityData.immigration = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "vm42" ->
                            currentCityData.emigration = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "vm2126" ->
                            currentCityData.marriages = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "vm3136" ->
                            currentCityData.divorces = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "kokmuutos" ->
                            currentCityData.totalChange = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                    case "vaesto" ->
                            currentCityData.population = new IntegerDataEntry(column.text, column.comment, Integer.parseInt(dataValues.get(j).get(i)));
                }
            }
        }

        return data;
    }

    /**
     * This record represents an entry in the integer data.
     * It includes text, comment, and value.
     */
    public record IntegerDataEntry(
            String text,
            String comment,
            int value) {
    }

    /**
     * This class represents the statistics data for a city.
     * It includes a list of columns and data entries.
     */
    private class StatisticsData {
        List<Column> columns;
        List<DataEntry> data;
    }

    /**
     * This class represents a column in the statistics data.
     * It includes a code, text, comment, and type.
     */
    private class Column {
        String code;
        String text;
        String comment;
        String type;
    }

    /**
     * This class represents a data entry in the statistics data.
     * It includes a key and a list of values.
     */
    public class DataEntry {
        List<String> key;
        List<String> values;
    }
}