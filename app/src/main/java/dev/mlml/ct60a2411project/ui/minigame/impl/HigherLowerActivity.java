package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import lombok.SneakyThrows;

public class HigherLowerActivity extends AppCompatActivity {
    private HigherLower higherLower;
    private int score = 0;

    @SneakyThrows
    private String[] getAreas(String exclude) {
        String[] ret = new String[2];
        ArrayList<String> possible = new ArrayList<>(CityCodesDataFetcher.getRegions().get().keySet());
        possible.remove(exclude);
        ret[0] = possible.get((int) (Math.random() * possible.size()));
        possible.remove(ret[0]);
        ret[1] = possible.get((int) (Math.random() * possible.size()));
        return ret;
    }

    @SneakyThrows
    private String[] getAreas() {
        String[] ret = new String[2];
        ArrayList<String> possible = new ArrayList<>(CityCodesDataFetcher.getRegions().get().keySet());
        ret[0] = possible.get((int) (Math.random() * possible.size()));
        possible.remove(ret[0]);
        ret[1] = possible.get((int) (Math.random() * possible.size()));
        return ret;
    }

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_higherorlower);

        String[] areas = getAreas();
        HigherLower higherLower = new HigherLower(areas[0], areas[1]);

        initializeGame(higherLower, false);

        findViewById(R.id.higherLowerChooseLeftButton).setOnClickListener(view -> {
            onClick(true);
        });
        findViewById(R.id.higherLowerChooseRightButton).setOnClickListener(view -> {
            onClick(false);
        });
    }

    private void onClick(boolean fromLeft) {
        boolean correct = fromLeft == higherLower.isLeftCorrect();
        HigherLower hl;
        if (correct) {
            hl = new HigherLower(fromLeft ? higherLower.getArea1() : higherLower.getArea2(), getAreas(fromLeft ? higherLower.getArea1() : higherLower.getArea2())[1]);
        } else {
            String[] nAreas = getAreas();
            hl = new HigherLower(nAreas[0], nAreas[1]);
        }
        initializeGame(hl, correct);
    }

    @SneakyThrows
    private void initializeGame(HigherLower hl, boolean previousCorrect) {
        if (previousCorrect) {
            score++;
        } else {
            score = 0;
        }

        ((TextView) findViewById(R.id.higherLowerScore)).setText(String.valueOf(score));

        String name1 = CityCodesDataFetcher.getRegions().get().get(hl.getArea1());
        String name2 = CityCodesDataFetcher.getRegions().get().get(hl.getArea2());

        ((ImageView) findViewById(R.id.higherLowerCoatOfArmsLeft)).setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(name1).get());
        ((ImageView) findViewById(R.id.higherLowerCoatOfArmsRight)).setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(name2).get());

        ((TextView) findViewById(R.id.guessTheCityQuestionText)).setText(hl.getQuestion());
        higherLower = hl;
    }

}
