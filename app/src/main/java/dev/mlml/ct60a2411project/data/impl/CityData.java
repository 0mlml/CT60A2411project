package dev.mlml.ct60a2411project.data.impl;

import java.util.HashMap;

import dev.mlml.ct60a2411project.data.GenericData;
import lombok.Data;

@Data
public class CityData extends GenericData {
    HashMap<String, SingleCityData> cities;

    public CityData(HashMap<String, SingleCityData> cities) {
        this.cities = cities;
    }
}
