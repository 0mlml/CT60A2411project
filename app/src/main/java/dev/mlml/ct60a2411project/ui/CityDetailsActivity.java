package dev.mlml.ct60a2411project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.SingleCityData;
import dev.mlml.ct60a2411project.databinding.ActivityDetailedCityBinding;
import dev.mlml.ct60a2411project.ui.compare.Comparator;

public class CityDetailsActivity extends AppCompatActivity {
    ActivityDetailedCityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = this.getIntent();
        if (intent != null) {
            String area = intent.getStringExtra("area");

            StringBuilder sb = new StringBuilder();
            try {
                SingleCityData cityData = CityDataFetcher.fetchAreas(area).get().getCities().get(area);
                sb.append("Population: ").append(cityData.getPopulation().value()).append("\n");
                sb.append("Births: ").append(cityData.getLiveBirths().value()).append("\n");
                sb.append("Deaths: ").append(cityData.getDeaths().value()).append("\n");
                sb.append("Immigration: ").append(cityData.getImmigration().value()).append("\n");
                sb.append("Emigration: ").append(cityData.getEmigration().value()).append("\n");
                sb.append("Marriages: ").append(cityData.getMarriages().value()).append("\n");
                sb.append("Divorces: ").append(cityData.getDivorces().value()).append("\n");
                sb.append("Population change: ").append(cityData.getTotalChange().value()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
                sb.append("Error fetching data.");
            }

            binding.detailName.setText(intent.getStringExtra("name"));
            binding.detailDesc.setText(sb.toString());
            binding.detailImage.setImageResource(intent.getIntExtra("imageResId", R.drawable.finland));

            binding.comparisonButton.setOnClickListener(view -> {
                Comparator.addToCompare(area, intent.getStringExtra("name"), intent.getIntExtra("imageResId", R.drawable.finland));
                Log.d("CityDetailsActivity", "Added " + area + " to comparison");
            });
        }
    }
}