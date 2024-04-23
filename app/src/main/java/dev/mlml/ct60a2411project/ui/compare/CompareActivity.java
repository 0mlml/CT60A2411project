package dev.mlml.ct60a2411project.ui.compare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.ui.MainActivity;
import dev.mlml.ct60a2411project.ui.search.SearchActivity;

public class CompareActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] pair = Comparator.consume();
        if (pair == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("showCompareFailDialog", true);
            startActivity(intent);
            return;
        }
        Log.d("CompareActivity", "Comparing " + pair[0] + " and " + pair[1]);

        CityData cd;
        try {
            cd = CityDataFetcher.fetchAreas(pair[0], pair[1]).get();
        } catch (Exception e) {
            e.printStackTrace();
            new AlertDialog.Builder(this).setTitle("Error fetching data").setMessage("An error occurred while fetching data. Please try again.").setPositiveButton("OK", (dialog, which) -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }



        setContentView(R.layout.activity_compare);
    }
}
