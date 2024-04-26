package dev.mlml.ct60a2411project.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CityData;
import dev.mlml.ct60a2411project.data.impl.CityDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import dev.mlml.ct60a2411project.data.impl.WeatherData;
import dev.mlml.ct60a2411project.data.impl.WeatherDataFetcher;
import dev.mlml.ct60a2411project.databinding.ActivityDetailedCityBinding;
import dev.mlml.ct60a2411project.ui.compare.Comparator;
import lombok.SneakyThrows;

public class CityDetailsActivity extends AppCompatActivity {
    ActivityDetailedCityBinding binding;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = this.getIntent();
        if (intent != null) {
            String area = intent.getStringExtra("area");

            RecyclerView rv = findViewById(R.id.detailedCityDetailList);
            rv.setLayoutManager(new LinearLayoutManager(this));

            List<String> details = new ArrayList<>();
            try {
                CityData cityData = CityDataFetcher.fetchArea(area).get();
                details.add(String.format(Locale.getDefault(), "Population: %d", cityData.getPopulation().value()));
                details.add(String.format(Locale.getDefault(), "Births: %d", cityData.getLiveBirths().value()));
                details.add(String.format(Locale.getDefault(), "Deaths: %d", cityData.getDeaths().value()));
                details.add(String.format(Locale.getDefault(), "Immigration: %d", cityData.getImmigration().value()));
                details.add(String.format(Locale.getDefault(), "Emigration: %d", cityData.getEmigration().value()));
                details.add(String.format(Locale.getDefault(), "Marriages: %d", cityData.getMarriages().value()));
                details.add(String.format(Locale.getDefault(), "Divorces: %d", cityData.getDivorces().value()));
                details.add(String.format(Locale.getDefault(), "Population change: %d", cityData.getTotalChange().value()));
                WeatherData weatherData = WeatherDataFetcher.getWeatherData(intent.getStringExtra("name")).get();
                details.add(String.format(Locale.getDefault(), "Temperature: %.1f°C", weatherData.getTemperature()));
                details.add(String.format(Locale.getDefault(), "Feels like: %.1f°C", weatherData.getFeelsLike()));
                details.add(String.format(Locale.getDefault(), "Humidity: %d%%", weatherData.getHumidity()));
                details.add(String.format(Locale.getDefault(), "Wind speed: %.1f m/s", weatherData.getWindSpeed()));
                details.add(String.format(Locale.getDefault(), "Wind direction: %s", weatherData.getWindDirection()));
                details.add(String.format(Locale.getDefault(), "Ground level: %d m", weatherData.getGroundLevel()));
                details.add(String.format(Locale.getDefault(), "Pressure: %d hPa", weatherData.getPressure()));
                details.add(String.format(Locale.getDefault(), "Sunrise: %tR", weatherData.getSunrise() * 1000));
                details.add(String.format(Locale.getDefault(), "Sunset: %tR", weatherData.getSunset() * 1000));
            } catch (Exception e) {
                Log.e("CityDetailsActivity", "Error fetching data.", e);
                details.add("\nError fetching data.");
            }
            CityDetailAdapter adapter = new CityDetailAdapter(details);
            rv.setAdapter(adapter);

            binding.detailedCityName.setText(intent.getStringExtra("name"));
            binding.detailedCityImage.setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(intent.getStringExtra("name")).get());

            binding.detailedCityComparisonButton.setOnClickListener(view -> {
                Comparator.addToCompare(area, intent.getStringExtra("name"));
                Log.d("CityDetailsActivity", "Added " + area + " to comparison");
            });
        }
    }
}