package dev.mlml.ct60a2411project.data;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public abstract class DataFetcher {
    public DataFetcher(URL url) {

    }

    public void update(boolean force) {
        Path existing = ManifestManager.getExistingDataFile(getClass());
        if (!force && Objects.nonNull(existing)) {
            return;
        }

        File dataFile = new File(System.getProperty("java.io.tmpdir") + File.separator + randomTemporyFileName());

        try {
            String fileName = randomTemporyFileName();
            File tempFile = downloadData(fileName);
        } catch (IOException e) {
            if (Objects.isNull(existing)) {
                throw new RuntimeException("Failed to download data", e);
            }
            return;
        }

        if (Objects.nonNull(dataFile)) {
            parseData(dataFile.toPath());
            ManifestManager.updateOrCreateEntry(getClass(), dataFile.toPath());
        }
    }

    private void parseData(Path dataFile) {
        // Parse the data file
    }

    private String randomTemporyFileName() {
        return getClass().getName() + "-" + System.currentTimeMillis();
    }

    private File downloadData(String fileName) throws IOException {
        String outputPath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        File outputFile = new File(outputPath);
        outputFile.createNewFile();
        return outputFile;
    }

    public record DataEntry(Enum<?> type, String key, String value) {

    }
}
