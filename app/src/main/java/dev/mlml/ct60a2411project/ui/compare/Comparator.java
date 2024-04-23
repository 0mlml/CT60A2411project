package dev.mlml.ct60a2411project.ui.compare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Comparator {
    public record ComparisonCity(
            String area,
            String name,
            int imageResId) {
    }

    @Getter
    private static List<ComparisonCity[]> history = new ArrayList<>();
    @Getter
    private static ComparisonCity[] current = new ComparisonCity[2];

    public static ComparisonCity[] consume() {
        if (current[0] == null || current[1] == null) {
            return null;
        }

        ComparisonCity[] result = current;
        history.add(result);
        current = new ComparisonCity[2];
        return result;
    }

    public static void addToCompare(String area, String name, int imageResId) {
        if (current[0] == null) {
            current[0] = new ComparisonCity(area, name, imageResId);
        } else if (current[1] == null) {
            current[1] = new ComparisonCity(area, name, imageResId);
        } else {
            current[0] = current[1];
            current[1] = new ComparisonCity(area, name, imageResId);
        }
    }
}
