package dev.mlml.ct60a2411project.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * This abstract class provides methods for sending HTTP requests.
 * It includes methods for sending POST and GET requests to a specified URL.
 * The methods return a Future of the response from the request as a string.
 * If an exception occurs during the request, the methods print the stack trace and return null.
 * The methods disconnect the connection before returning.
 */
public abstract class DataFetcher {
    /**
     * This method sends a POST request to the specified URL with the provided request body.
     * It opens a connection to the URL, sets the request method to POST, sets the request properties, and writes the request body to the output stream.
     * It then reads the response from the input stream and returns it as a string.
     * If an exception occurs, it prints the stack trace and returns null.
     * It disconnects the connection before returning.
     *
     * @param url         The URL to send the POST request to.
     * @param requestBody The body of the POST request.
     * @return A Future of the response from the POST request as a string.
     */
    protected static Future<String> post(URL url, String requestBody) {
        Callable<String> callable = () -> {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask;
    }

    /**
     * This method sends a GET request to the specified URL.
     * It opens a connection to the URL, sets the request method to GET, and sets the request properties.
     * It then reads the response from the input stream and returns it as a string.
     * If an exception occurs, it prints the stack trace and returns null.
     * It disconnects the connection before returning.
     *
     * @param url The URL to send the GET request to.
     * @return A Future of the response from the GET request as a string.
     */
    protected static Future<String> get(URL url) {
        Callable<String> callable = () -> {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        };

        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask;
    }
}
