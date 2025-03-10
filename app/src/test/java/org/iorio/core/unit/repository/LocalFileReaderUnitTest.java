package org.iorio.core.unit.repository;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractFileReader;
import org.iorio.core.repository.local.LocalFileReaderImpl;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocalFileReaderUnitTest {
    private final AbstractFileReader<Path> reader = new LocalFileReaderImpl();

    @Description("If the file exists, then It should be possible to read the Its content")
    @Test
    @Tag("unit")
    public void testContentOfExistingFileShouldExist() {
        final List<String> content = reader.getContent(Path.of("src/test/resources/.env"));
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }

    @Description("If the file does not exist, then It should throw an exception")
    @Test
    @Tag("unit")
    public void testContentOfNonExistingFileShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> reader.getContent(Path.of("src/INVALID")));
    }
}
