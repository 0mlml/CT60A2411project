package dev.mlml.ct60a2411project.ui.minigame.impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.mlml.ct60a2411project.R;
import dev.mlml.ct60a2411project.data.impl.CoatOfArmsFetcher;
import lombok.SneakyThrows;

public class FlashCardsAdapter extends BaseAdapter {
    private final Context context;
    private final FlashCards flashCards;
    private List<String> municipalities;

    public FlashCardsAdapter(Context context, List<String> municipalities, FlashCards flashCards) {
        this.context = context;
        this.municipalities = municipalities;
        this.flashCards = flashCards;
    }

    @Override
    public int getCount() {
        return municipalities.size();
    }

    @Override
    public Object getItem(int position) {
        return municipalities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SneakyThrows
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context, null, R.style.flashCardImageView);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 150));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        String municipality = municipalities.get(position);
        imageView.setImageBitmap(CoatOfArmsFetcher.fetchCoatOfArms(municipality).get());
        imageView.setOnClickListener(v -> {
            flashCards.consume(municipality);
            ArrayList<String> remainingMunicipalities = new ArrayList<>(flashCards.getUnconsumedMunicipalities());
            if (remainingMunicipalities.isEmpty()) {
                Intent intent = new Intent(context, FlashCardsResultActivity.class);
                intent.putExtra("score", flashCards.getScore());
                context.startActivity(intent);
                return;
            }
            Collections.shuffle(remainingMunicipalities);
            municipalities = remainingMunicipalities;
            notifyDataSetChanged();
        });

        return imageView;
    }
}
