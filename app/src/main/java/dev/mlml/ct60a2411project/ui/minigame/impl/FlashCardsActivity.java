package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import lombok.SneakyThrows;

public class FlashCardsActivity extends AppCompatActivity {
    private FlashCards flashCards;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        FlashCards flashCards = new FlashCards();
        initializeGame(flashCards);

        CoatOfArmsFetcher.populateCache(flashCards.getMunicipalities());

        GridView gv = findViewById(R.id.flashCardsGridView);
        FlashCardsAdapter adapter = new FlashCardsAdapter(this, flashCards.getMunicipalities(), flashCards);
        gv.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                ((TextView) findViewById(R.id.flashcardsScoreTextView)).setText(flashCards.getScore());
                ((TextView) findViewById(R.id.flashcardsQuestionTextView)).setText(flashCards.getQuestion());
            }
        });
    }

    private void initializeGame(FlashCards flashCards) {
        this.flashCards = flashCards;

        ((TextView) findViewById(R.id.flashcardsScoreTextView)).setText(flashCards.getScore());
        ((TextView) findViewById(R.id.flashcardsQuestionTextView)).setText(flashCards.getQuestion());
    }
}
