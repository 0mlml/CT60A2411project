package dev.mlml.ct60a2411project.data.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;

public class CoatOfArmsFetcher extends DataFetcher {
    private final static HashMap<String, Bitmap> cache = new HashMap<>(); // Cache for storing fetched coat of arms

    private final static String endpoint = "https://www.mlml.dev/vaakunat/"; // The endpoint for fetching coat of arms

    public static Future<Bitmap> fetchCoatOfArms(String name) {
        FutureTask<Bitmap> futureTask = new FutureTask<>(() -> {
            if (cache.containsKey(name)) {
                return cache.get(name);
            }

            Bitmap coatOfArms = getImage(new URL(endpoint + name + ".png")).get();
            cache.put(name, coatOfArms);

            return cache.get(name);
        });
        new Thread(futureTask).start();
        return futureTask;
    }

    private static Future<Bitmap> getImage(URL url) {
        Callable<Bitmap> callable = () -> {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "image/png");

                try (InputStream in = new BufferedInputStream(connection.getInputStream())) {
                    return BitmapFactory.decodeStream(in);
                }
            } catch (Exception e) {
                Log.e("CoatOfArmsFetcher", "Failed to fetch coat of arms.", e);
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        };

        FutureTask<Bitmap> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask;
    }
}
