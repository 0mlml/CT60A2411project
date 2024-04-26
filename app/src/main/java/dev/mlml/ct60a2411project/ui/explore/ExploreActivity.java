package dev.mlml.ct60a2411project.ui.explore;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import dev.mlml.ct60a2411project.R;

public class ExploreActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore);

        ListView lv = findViewById(R.id.exploreOutputList);
        ExploreListAdapter adapter = new ExploreListAdapter(this);
        lv.setAdapter(adapter);

        for (Explorator.SortingCritera criteria : Explorator.SortingCritera.values()) {
            findViewById(getResources().getIdentifier(criteria.toId(), "id", getPackageName())).setOnClickListener(v -> {
                adapter.setDisplayed(Explorator.explore(criteria));
            });
        }
    }
}
