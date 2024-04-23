package dev.mlml.ct60a2411project.data.impl;

import java.util.HashMap;

import lombok.Data;

@Data
public class CityData {
    HashMap<String, SingleCityData> cities;

    public CityData(HashMap<String, SingleCityData> cities) {
        this.cities = cities;
    }
}
