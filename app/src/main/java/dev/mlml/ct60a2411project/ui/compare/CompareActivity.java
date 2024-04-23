package dev.mlml.ct60a2411project.ui.compare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.ui.MainActivity;

public class CompareActivity extends AppCompatActivity {
    private static String generateInfoText(String area) {
        StringBuilder sb = new StringBuilder();
        try {
            CityData cityData = CityDataFetcher.fetchArea(area).get();
            sb.append("Population: ").append(cityData.getPopulation().value()).append("\n");
            sb.append("Births: ").append(cityData.getLiveBirths().value()).append("\n");
            sb.append("Deaths: ").append(cityData.getDeaths().value()).append("\n");
            sb.append("Immigration: ").append(cityData.getImmigration().value()).append("\n");
            sb.append("Emigration: ").append(cityData.getEmigration().value()).append("\n");
            sb.append("Marriages: ").append(cityData.getMarriages().value()).append("\n");
            sb.append("Divorces: ").append(cityData.getDivorces().value()).append("\n");
            sb.append("Population change: ").append(cityData.getTotalChange().value()).append("\n");
        } catch (Exception e) {
            Log.e("CompareActivity", "Error fetching data.", e);
            sb.append("Error fetching data.");
        }

        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Comparator.ComparisonCity[] pair = Comparator.consume();
        if (pair == null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("showCompareFailDialog", true);
            startActivity(intent);
            return;
        }
        Log.d("CompareActivity", "Comparing " + pair[0] + " and " + pair[1]);

        setContentView(R.layout.activity_compare);

        ((ImageView) findViewById(R.id.leftAreaCoatOfArms)).setImageResource(pair[0].imageResId());
        ((ImageView) findViewById(R.id.rightAreaCoatOfArms)).setImageResource(pair[1].imageResId());

        ((TextView) findViewById(R.id.leftAreaTitle)).setText(pair[0].name());
        ((TextView) findViewById(R.id.rightAreaTitle)).setText(pair[1].name());

        ((TextView) findViewById(R.id.leftAreaTextBox)).setText(generateInfoText(pair[0].area()));
        ((TextView) findViewById(R.id.rightAreaTextBox)).setText(generateInfoText(pair[1].area()));

    }
}
