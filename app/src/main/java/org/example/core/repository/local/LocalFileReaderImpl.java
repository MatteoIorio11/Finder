package org.example.core.repository.local;

import org.example.core.repository.AbstractFileReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class LocalFileReaderImpl extends AbstractFileReader<Path> {

    @Override
    public String getContent(Path path) {
        try {
            return Files.readString(path);
        }catch (IOException ignored) {}
        return "";
    }
}
