package dev.mlml.ct60a2411project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import dev.mlml.ct60a2411project.databinding.ActivitySearchBinding;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    ListAdapter listAdapter;
    ArrayList<ListData> dataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListView();
        setupSearchView();
    }

    private void setupListView() {
        int[] imageList = {R.drawable.finland, R.drawable.finland, R.drawable.finland, R.drawable.finland, R.drawable.finland, R.drawable.finland, R.drawable.finland};
        int[] ingredientList = {R.string.pastaIngredients, R.string.maggiIngredients, R.string.cakeIngredients, R.string.pancakeIngredients, R.string.pizzaIngredients, R.string.burgerIngredients, R.string.friesIngredients};
        int[] descList = {R.string.pastaDesc, R.string.pastaDesc, R.string.cakeDesc, R.string.pancakeDesc, R.string.pizzaDesc, R.string.burgerDesc, R.string.friesDesc};
        String[] nameList = {"Pasta", "Maggi", "Cake", "Pancake", "Pizza", "Burgers", "Fries"};
        String[] timeList = {"30 mins", "2 mins", "45 mins", "10 mins", "60 mins", "45 mins", "30 mins"};

        for (int i = 0; i < imageList.length; i++) {
            ListData listData = new ListData(nameList[i], timeList[i], ingredientList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAdapter(this, dataArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListData data = listAdapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, DetailedCity.class);
                intent.putExtra("name", data.name);
                intent.putExtra("time", data.time);
                intent.putExtra("ingredients", data.ingredients);
                intent.putExtra("desc", data.desc);
                intent.putExtra("image", data.image);
                startActivity(intent);
            }
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
                listAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}