package dev.mlml.ct60a2411project.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import dev.mlml.ct60a2411project.R;

public class SearchableListAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> cityList;
    private List<String> displayed; // Filtered list

    public SearchableListAdapter(@NonNull Context context, List<String> cityList) {
        super(context, R.layout.list_city);
        this.cityList = cityList;
        this.displayed = cityList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String city = getItem(position);
        if (Objects.isNull(convertView)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_city, parent, false);
        }
        ((ImageView) convertView.findViewById(R.id.listImage)).setImageResource(R.drawable.finland);
        ((TextView) convertView.findViewById(R.id.listName)).setText(city);
        return convertView;
    }

    @Override
    public int getCount() {
        return displayed.size();
    }

    @Override
    public String getItem(int position) {
        return displayed.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<String> filteredList = ((constraint == null) || (constraint.length() == 0)) ? cityList : cityList.stream().filter(city -> city.toLowerCase().contains(constraint.toString().toLowerCase())).collect(Collectors.toList());

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                displayed = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}