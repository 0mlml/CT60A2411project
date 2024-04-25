package dev.mlml.ct60a2411project.data.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import dev.mlml.ct60a2411project.data.DataFetcher;

/**
 * This class extends the DataFetcher class and provides methods for fetching coat of arms images.
 * It includes a cache for storing fetched coat of arms to avoid redundant network requests.
 * It also includes a method for fetching an image from a URL.
 */
public class CoatOfArmsFetcher extends DataFetcher {
    // Cache for storing fetched coat of arms
    private final static HashMap<String, Bitmap> cache = new HashMap<>();

    // The endpoint for fetching coat of arms
    private final static String endpoint = "https://www.mlml.dev/vaakunat/";

    /**
     * This method populates the cache with coat of arms images for a list of names.
     * It iterates over the list of names and fetches the coat of arms for each name.
     *
     * @param names The list of names to fetch the coat of arms for.
     */
    public static void populateCache(List<String> names) {
        for (String name : names) {
            fetchCoatOfArms(name);
        }
    }

    /**
     * This method fetches a coat of arms image for a given name.
     * It normalizes the name and checks if the image is already in the cache.
     * If the image is in the cache, it returns it from there.
     * Otherwise, it fetches the image from the endpoint, stores it in the cache, and returns it.
     *
     * @param name The name of the coat of arms to fetch.
     * @return A Future of the fetched coat of arms image as a Bitmap.
     */
    public static Future<Bitmap> fetchCoatOfArms(String name) {
        String normalizedName = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("-|\\s", "_").toLowerCase();

        FutureTask<Bitmap> futureTask = new FutureTask<>(() -> {
            if (cache.containsKey(normalizedName)) {
                return cache.get(normalizedName);
            }

            Bitmap coatOfArms = getImage(new URL(endpoint + normalizedName + ".png")).get();
            cache.put(normalizedName, coatOfArms);

            return cache.get(normalizedName);
        });
        new Thread(futureTask).start();
        return futureTask;
    }

    /**
     * This method fetches an image from a given URL.
     * It opens a connection to the URL, sets the request method to GET, and sets the request properties.
     * It then reads the image from the input stream and returns it as a Bitmap.
     * If an exception occurs, it logs the error and returns null.
     * It disconnects the connection before returning.
     *
     * @param url The URL to fetch the image from.
     * @return A Future of the fetched image as a Bitmap.
     */
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