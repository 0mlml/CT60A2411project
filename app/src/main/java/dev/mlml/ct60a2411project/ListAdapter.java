package dev.mlml.ct60a2411project;

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
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> implements Filterable {
    private ArrayList<ListData> originalData;
    private ArrayList<ListData> filteredData;

    public ListAdapter(@NonNull Context context, ArrayList<ListData> dataArrayList) {
        super(context, R.layout.list_city, dataArrayList);
        this.originalData = new ArrayList<>(dataArrayList);
        this.filteredData = new ArrayList<>(dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListData listData = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_city, parent, false);
        }
        ImageView listImage = convertView.findViewById(R.id.listImage);
        TextView listName = convertView.findViewById(R.id.listName);
        TextView listTime = convertView.findViewById(R.id.listPart);
        listImage.setImageResource(listData.image);
        listName.setText(listData.name);
        listTime.setText(listData.time);
        return convertView;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public ListData getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = originalData;
                    results.count = originalData.size();
                } else {
                    ArrayList<ListData> filterResultsData = new ArrayList<>();
                    for (ListData data : originalData) {
                        if (data.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterResultsData.add(data);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<ListData>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}