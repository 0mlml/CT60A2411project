package dev.mlml.ct60a2411project.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

import dev.mlml.ct60a2411project.data.impl.CityCodesDataFetcher;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import dev.mlml.ct60a2411project.databinding.ActivitySearchBinding;
import dev.mlml.ct60a2411project.details.CityDetailsActivity;

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
            regions = CityCodesDataFetcher.getRegions().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setupListView(regions.values().stream().toList());
        setupSearchView();

        new Thread(() -> CoatOfArmsFetcher.populateCache(regions.values().stream().toList())).start();
    }

    private void setupListView(List<String> cities) {
        searchableListAdapter = new SearchableListAdapter(this, cities);
        binding.searchResultListView.setAdapter(searchableListAdapter);
        binding.searchResultListView.setClickable(true);
        binding.searchResultListView.setOnItemClickListener((parent, view, position, id) -> {
            String cityName = searchableListAdapter.getItem(position);
            Intent intent = new Intent(SearchActivity.this, CityDetailsActivity.class);
            intent.putExtra("name", cityName);
            intent.putExtra("area", CityCodesDataFetcher.getData().getAreaByName(cityName));
            startActivity(intent);
        });
    }

    private void setupSearchView() {
        binding.searchSearchBoxView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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