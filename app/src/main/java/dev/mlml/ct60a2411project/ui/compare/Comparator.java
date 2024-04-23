package dev.mlml.ct60a2411project.ui.compare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Comparator {
    @Getter
    private static List<String[]> history = new ArrayList<>();
    @Getter
    private static String[] current = new String[2];

    public static String[] consume() {
        if (current[0] == null || current[1] == null) {
            return null;
        }

        String[] result = current;
        history.add(result);
        current = new String[2];
        return result;
    }

    public static void addToCompare(String area) {
        if (current[0] == null) {
            current[0] = area;
        } else if (current[1] == null) {
            current[1] = area;
        } else {
            current[0] = current[1];
            current[1] = area;
        }
    }
}
