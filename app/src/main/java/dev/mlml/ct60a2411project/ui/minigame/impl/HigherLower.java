package dev.mlml.ct60a2411project.ui.minigame.impl;

import java.lang.reflect.Method;
import java.util.HashMap;

import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class HigherLower {
    private final String area1;
    private final String area2;
    private final String question;
    private final boolean isLeftCorrect;

    @SneakyThrows
    public HigherLower(String area1, String area2) {
        this.area1 = area1;
        this.area2 = area2;

        HashMap<String, CityData> cities = CityDataFetcher.fetchAreas(area1, area2).get();
        CityData city1 = cities.get(area1);
        CityData city2 = cities.get(area2);

        Statistic statistic = Statistic.values()[(int) (Math.random() * Statistic.values().length)];

        HashMap<String, String> regions = CityCodesDataFetcher.getRegions().get();

        Method getter = CityData.class.getMethod(statistic.toMethodName());
        CityData.IntegerDataEntry value1 = (CityData.IntegerDataEntry) getter.invoke(city1);
        CityData.IntegerDataEntry value2 = (CityData.IntegerDataEntry) getter.invoke(city2);

        question = String.format("Does %s have higher %s than %s?", regions.get(area1), value1.text(), regions.get(area2));

        isLeftCorrect = value1.value() > value2.value();
    }

    private enum Statistic {
        liveBirths, deaths, immigration, emigration, marriages, divorces, totalChange, population;

        public java.lang.String toMethodName() {
            return "get" + name().substring(0, 1).toUpperCase() + name().substring(1);
        }
    }
}
