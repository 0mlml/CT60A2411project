package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.ui.MainActivity;
import lombok.SneakyThrows;

public class FlashCardsResultActivity extends AppCompatActivity {
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flashcards_result);

        ((TextView) findViewById(R.id.flashcardsResScoreTextView)).setText(getIntent().getStringExtra("score"));

        findViewById(R.id.flashcardsResHomeButton).setOnClickListener(view -> {
            Intent intent = new Intent(FlashCardsResultActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
