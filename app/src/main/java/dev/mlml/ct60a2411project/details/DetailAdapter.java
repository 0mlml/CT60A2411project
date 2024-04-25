package dev.mlml.ct60a2411project.details;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mlml.ct60a2411project.R;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private final List<String> details;

    public DetailAdapter(List<String> details) {
        this.details = details;
        Log.d("DetailAdapter", "Details: " + details.toString());
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.bind(details.get(position));

        // Set background color based on position for alternating effect
        if (position % 2 == 0) {
            holder.textView.setBackgroundResource(R.drawable.list_item_background);
        } else {
            holder.textView.setBackgroundResource(R.drawable.list_item_background_alternate);
        }
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public void bind(String detail) {
            textView.setText(detail);
        }
    }
}
