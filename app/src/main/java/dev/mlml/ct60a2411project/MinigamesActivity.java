package dev.mlml.ct60a2411project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MinigamesActivity extends AppCompatActivity {

    CardView quizCard;
    CardView flashCardsCard;
    CardView qtcCard;
    CardView holCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigames);


        quizCard = findViewById(R.id.quizCard);
        flashCardsCard = findViewById(R.id.flashCardsCard);
        qtcCard = findViewById(R.id.holCard);
        holCard = findViewById(R.id.holCard);


        quizCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MinigamesActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        flashCardsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MinigamesActivity.this, FlashCardsActivity.class);
                startActivity(intent);
            }
        });

        qtcCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MinigamesActivity.this, QtcActivity.class);
                startActivity(intent);
            }
        });


        holCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MinigamesActivity.this, HolActivity.class);
                startActivity(intent);
            }
        });
    }
}
