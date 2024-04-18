package dev.mlml.ct60a2411project.data;

import java.io.File;
import java.nio.file.Path;

public class ManifestManager {
    public static final String manifestFileName = "data-manifest";

    public static void updateOrCreateEntry(Class<?> clazz, Path dataFile) {
        File manifest = new File(manifestFileName);
        String currentManifest = "";
        if (manifest.exists()) {
            currentManifest = manifest.;
        }

    }
}
