package dev.mlml.ct60a2411project.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

import dev.mlml.ct60a2411project.databinding.ActivitySearchBinding;
import dev.mlml.ct60a2411project.ui.CityDetailsActivity;
import dev.mlml.ct60a2411project.ui.MainActivity;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchableListAdapter searchableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        HashMap<String, String> regions;
        try {
            regions = MainActivity.cityCodesDataFetcher.getRegions().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setupListView(regions.values().stream().toList());
        setupSearchView();
    }

    private void setupListView(List<String> cities) {
        searchableListAdapter = new SearchableListAdapter(this, cities);
        binding.listview.setAdapter(searchableListAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            String cityName = searchableListAdapter.getItem(position);
            Intent intent = new Intent(SearchActivity.this, CityDetailsActivity.class);
            intent.putExtra("name", cityName);
            startActivity(intent);
        });
    }

    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchableListAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}