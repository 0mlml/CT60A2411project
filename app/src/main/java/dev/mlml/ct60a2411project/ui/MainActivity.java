package dev.mlml.ct60a2411project.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.ui.compare.CompareActivity;
import dev.mlml.ct60a2411project.ui.explore.ExploreActivity;
import dev.mlml.ct60a2411project.ui.minigame.MinigameSplashActivity;
import dev.mlml.ct60a2411project.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            CityCodesDataFetcher.init();
        }).start();

        if (getIntent().getBooleanExtra("showCompareFailDialog", false)) {
            new AlertDialog.Builder(this).setTitle("No comparison entries found").setMessage("Please continue to the search to find areas to compare.").setPositiveButton("Continue", (dialog, which) -> {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }

        findViewById(R.id.searchCard).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.exploreCard).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.minigamesCard).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MinigameSplashActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.compareCard).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CompareActivity.class);
            startActivity(intent);
        });
    }
}
