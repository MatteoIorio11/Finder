package org.iorio.core.repository.local;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.iorio.core.repository.FileReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LocalFileReaderImpl implements FileReader<Path> {

    @Override
    public List<String> getContent(@NonNull final Path path) {
        try {
            return Files.readAllLines(path);
        }catch (IOException ignored) {
            throw new RuntimeException("File not found");
        }
    }
}
