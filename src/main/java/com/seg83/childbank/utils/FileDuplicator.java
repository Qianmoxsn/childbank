package com.seg83.childbank.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDuplicator {
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
