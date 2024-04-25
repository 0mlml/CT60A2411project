package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import dev.mlml.ct60a2411project.ui.MainActivity;
import lombok.SneakyThrows;

public class QuizResultActivity extends AppCompatActivity {
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_result);

        String area = getIntent().getStringExtra("area");
        String name = CityCodesDataFetcher.getRegions().get().get(area);

        ((ImageView) findViewById(R.id.quizResCityCoatOfArms)).setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(name).get());
        ((TextView) findViewById(R.id.quizResCityNameTextView)).setText(name);
        ((TextView) findViewById(R.id.quizResScoreTextView)).setText(getIntent().getStringExtra("score"));

        findViewById(R.id.quizResHomeButton).setOnClickListener(view -> {
            Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
