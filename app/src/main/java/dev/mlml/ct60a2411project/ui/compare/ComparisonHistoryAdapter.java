package dev.mlml.ct60a2411project.ui.compare;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import dev.mlml.ct60a2411project.R;

public class ComparisonHistoryAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<Comparator.ComparisonCity[]> history;
    private final CompareActivity parent;

    public ComparisonHistoryAdapter(Context context, List<Comparator.ComparisonCity[]> history, CompareActivity parent) {
        super(context, R.layout.list_item);
        this.context = context;
        this.history = history;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return history.size();
    }

    private String formatCity(Comparator.ComparisonCity city) {
        return city.name();
    }

    @Override
    public String getItem(int position) {
        return formatCity(history.get(position)[0]) + " vs " + formatCity(history.get(position)[1]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent_) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
        } else {
            textView = (TextView) convertView;
        }

        String listItem = getItem(position);
        textView.setText(listItem);
        textView.setOnClickListener(v -> {
            parent.setComparison(history.get(position));
            notifyDataSetChanged();
        });

        return textView;
    }
}
