package dev.mlml.ct60a2411project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import dev.mlml.ct60a2411project.databinding.ActivityDetailedCityBinding;

public class DetailedCity extends AppCompatActivity {
    ActivityDetailedCityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = this.getIntent();
    }
}