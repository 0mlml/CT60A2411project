package dev.mlml.ct60a2411project.ui.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import lombok.SneakyThrows;

public class ExploreListAdapter extends ArrayAdapter<Explorator.SortedCity> {
    private List<Explorator.SortedCity> displayed;

    @SneakyThrows
    public ExploreListAdapter(Context context) {
        super(context, R.layout.explore_list_city);
        displayed = Explorator.explore(Explorator.SortingCritera.Hottest);
    }

    @Override
    public int getCount() {
        return displayed.size();
    }

    @Override
    public Explorator.SortedCity getItem(int position) {
        return displayed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDisplayed(List<Explorator.SortedCity> displayed) {
        this.displayed = displayed;
        notifyDataSetChanged();
    }

    @NonNull
    @SneakyThrows
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Explorator.SortedCity city = getItem(position);
        if (Objects.isNull(convertView)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.explore_list_city, parent, false);
        }
        if (city != null) {
            ((ImageView) convertView.findViewById(R.id.listImage)).setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(city.name()).get());
            ((TextView) convertView.findViewById(R.id.listName)).setText(city.name());
            ((TextView) convertView.findViewById(R.id.listAttribute)).setText(city.attribute());
        }
        return convertView;
    }
}
