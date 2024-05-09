package com.seg83.childbank.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The FileDuplicator class provides functionality to duplicate a file.
 */
public class FileDuplicator {

    /**
     * Restores a file from the source path to the target path.
     *
     * @param source The source path of the file to be duplicated.
     * @param target The target path where the duplicated file will be restored.
     */
    public static void restoreFile(Path source, Path target) {
        File sourceFile = source.toFile();
        File targetFile = target.toFile();
        if (targetFile.exists()) {
            targetFile.delete();
        }
        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

