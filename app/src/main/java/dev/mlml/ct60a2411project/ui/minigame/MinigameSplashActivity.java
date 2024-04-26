package dev.mlml.ct60a2411project.ui.minigame;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.ui.minigame.impl.FlashCardsActivity;
import dev.mlml.ct60a2411project.ui.minigame.impl.HigherLowerActivity;
import dev.mlml.ct60a2411project.ui.minigame.impl.QuizActivity;

public class MinigameSplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigames);

        findViewById(R.id.quizCard).setOnClickListener(view -> {
            Intent intent = new Intent(MinigameSplashActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.flashcardCard).setOnClickListener(view -> {
            Intent intent = new Intent(MinigameSplashActivity.this, FlashCardsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.higherLowerCard).setOnClickListener(view -> {
            Intent intent = new Intent(MinigameSplashActivity.this, HigherLowerActivity.class);
            startActivity(intent);
        });
    }
}
