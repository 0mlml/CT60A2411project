package dev.mlml.ct60a2411project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    CardView searchCard;
    CardView exploreCard;
    CardView minigamesCard;
    CardView compareCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchCard = findViewById(R.id.searchCard);
        exploreCard = findViewById(R.id.exploreCard);
        minigamesCard = findViewById(R.id.minigamesCard);
        compareCard = findViewById(R.id.compareCard);


        searchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        exploreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        minigamesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MinigamesActivity.class);
                startActivity(intent);
            }
        });


        compareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CompareActivity.class);
                startActivity(intent);
            }
        });
    }
}
