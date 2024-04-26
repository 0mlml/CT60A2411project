package dev.mlml.ct60a2411project.ui.minigame.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import lombok.Getter;
import lombok.SneakyThrows;

public class FlashCards {
    @Getter
    private final List<String> municipalities;
    @Getter
    private final List<String> unconsumedMunicipalities;
    @Getter
    private int score = 0;

    @SneakyThrows
    public FlashCards() {
        List<String> municipalities = new ArrayList<>(CityCodesDataFetcher.getRegions().get().values().stream().toList());
        Collections.shuffle(municipalities);
        this.municipalities = municipalities.subList(0, 30);
        this.unconsumedMunicipalities = new ArrayList<>(this.municipalities);
        Collections.shuffle(this.unconsumedMunicipalities);
    }

    public String getQuestion() {
        return String.format(Locale.getDefault(), "Click on the coat of arms of %s", unconsumedMunicipalities.get(0));
    }

    public String getScore() {
        return String.format(Locale.getDefault(), "Score: %d", score);
    }

    public void consume(String municipality) {
        boolean correct = municipality.equals(unconsumedMunicipalities.get(0));
        unconsumedMunicipalities.remove(0);
        if (correct) {
            score++;
        }
    }
}
