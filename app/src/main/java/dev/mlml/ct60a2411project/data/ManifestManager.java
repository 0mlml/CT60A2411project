package dev.mlml.ct60a2411project.data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ManifestManager {
    public static final String manifestFileName = "data-manifest";

    private static List<String[]> parseManifest(File manifest) throws IOException {
        List<String[]> entries = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(manifest)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] entry = line.split("\u001F");
                if (entry.length != 3) {
                    throw new IOException("Invalid manifest entry: " + line);
                }


                for (String[] existing : entries) {
                    if (existing[0].equals(entry[0])) {
                        entries.remove(existing);
                    }
                }
                entries.add(entry);
            }
        }

        return entries;
    }
  
    public static void updateOrCreateEntry(Class<?> clazz, Path dataFile)  {
        File manifest = new File(manifestFileName);
        List<String[]> entries = new ArrayList<>();

        if (manifest.exists()) {
            try {
                entries = parseManifest(manifest);
            } catch (IOException e) {
                Log.e("ManifestManager", "Failed to parse manifest", e);
            }
        }

        entries.add(new String[]{clazz.getName(), dataFile.toString(), String.valueOf(System.currentTimeMillis())});

        try {
            manifest.createNewFile();
        } catch (IOException e) {
            Log.e("ManifestManager", "Failed to create manifest", e);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(manifest);
            for (String[] entry : entries) {
                fileOutputStream.write(String.join("\u001F", entry).getBytes());
                fileOutputStream.write('\n');
            }

            fileOutputStream.close();
        } catch (IOException e) {
            Log.e("ManifestManager", "Failed to write to manifest", e);
        }

        Log.i("ManifestManager", "Updated manifest with " + clazz.getName() + " -> " + dataFile);
    }

    public static Path getExistingDataFile(Class clazz)  {
        File manifest = new File(manifestFileName);

        if (!manifest.exists()) {
            return null;
        }

        List<String[]> entries = null;
        try {
            entries = parseManifest(manifest);
            for (String[] entry : entries) {
                if (entry[0].equals(clazz.getName())) {
                    return Paths.get(entry[1]);
                }
            }
        } catch (IOException e) {
            Log.e("ManifestManager", "Failed to parse manifest", e);
        }

        return null;
    }
}
