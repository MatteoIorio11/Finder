package org.example.core.unit;

import jdk.jfr.Description;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.RepositoryElement;
import org.example.core.repository.local.LocalFileImpl;
import org.example.core.repository.local.LocalFileReaderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileUnitTest extends AbstractRepositoryElementUnitTest<Path> {
    private static AbstractRepositoryFile<Path> file;
    public FileUnitTest() {
        super(file);
    }

    @BeforeAll
    public static void init() {
        file = new LocalFileImpl("FileTest", Path.of("src/test/resources/.env"));
    }

    @Description("If the file exists, then It should be possible to read the Its content")
    @Test
    @Tag("unit")
    public void testContentOfExistingFileShouldExist() {
        final String content = file.getContent(new LocalFileReaderImpl());
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }

    @Description("If the file does not exist, then It should throw an exception")
    @Test
    @Tag("unit")
    public void testContentOfNonExistingFileShouldThrowException() {
        final var nonExistingFile = new LocalFileImpl("NonExistingFile", Path.of("src/INVALID"));
        assertThrows(RuntimeException.class, () -> nonExistingFile.getContent(new LocalFileReaderImpl()));
    }
}
