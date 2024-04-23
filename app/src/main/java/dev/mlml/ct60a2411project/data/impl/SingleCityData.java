package dev.mlml.ct60a2411project.data.impl;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;

@Data
public class SingleCityData {
    private class StatisticsData {
        List<Column> columns;
        List<DataEntry> data;
    }

    private class Column {
        String code;
        String text;
        String comment;
        String type;
    }

    public class DataEntry {
        List<String> key;
        List<String> values;
    }

    public record IntegerDataEntry(
            String text,
            String comment,
            int value) {
    }

    private String area;
    private IntegerDataEntry liveBirths; // In data: vm01
    private IntegerDataEntry deaths; // In data: vm11
    private IntegerDataEntry immigration; // In data: vm41
    private IntegerDataEntry emigration; // In data: vm42
    private IntegerDataEntry marriages; // In data: vm2126
    private IntegerDataEntry divorces; // In data: vm3136
    private IntegerDataEntry totalChange; // In data: kokmuutos
    private IntegerDataEntry population; // In data: vaesto

    public SingleCityData() {
    }

    public static HashMap<String, SingleCityData> parseData(String json) {
        Gson gson = new Gson();
        StatisticsData statisticsData = gson.fromJson(json, StatisticsData.class);
        HashMap<String, SingleCityData> data = new HashMap<>();

        List<Column> columns = statisticsData.columns;
        List<List<String>> dataValues = new ArrayList<>();
        for (int i = 0; i < statisticsData.data.size(); i++) {
            dataValues.add(Stream.concat(statisticsData.data.get(i).key.stream(), statisticsData.data.get(i).values.stream()).collect(Collectors.toList()));
            data.put(dataValues.get(dataValues.size() - 1).get(1), new SingleCityData());
        }

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            for (int j = 0; j < dataValues.size(); j++) {
                SingleCityData currentCityData = data.get(dataValues.get(j).get(1));
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
}
