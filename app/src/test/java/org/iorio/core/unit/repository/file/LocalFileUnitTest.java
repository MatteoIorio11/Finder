package org.iorio.core.unit.repository.file;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.local.LocalFileReaderImpl;
import org.iorio.core.unit.repository.AbstractRepositoryElementUnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class LocalFileUnitTest extends AbstractRepositoryElementUnitTest<Path> {
    private static AbstractRepositoryFile<Path> file;
    private static LocalFileReaderImpl reader;
    public LocalFileUnitTest() {
        super(file);
    }

    @BeforeAll
    public static void init() {
        reader = Mockito.mock(LocalFileReaderImpl.class);
        file = new LocalFileImpl("FileTest", Path.of("src/test/resources/.env"));
        when(reader.getContent(Path.of("src/INVALID"))).thenThrow(new RuntimeException());
        when(reader.getContent(file.getPath())).thenReturn(List.of("TEST"));
    }

    @Description("If the file does not exist, then It should throw an exception")
    @Test
    @Tag("unit")
    public void testContentOfNonExistingFileShouldThrowException() {
        final var nonExistingFile = new LocalFileImpl("NonExistingFile", Path.of("src/INVALID"));
        assertThrows(RuntimeException.class, () -> nonExistingFile.getContent(reader));
    }

    @Description("If the file exists, then It should return the content")
    @Test
    @Tag("unit")
    public void testContentOfExistingFileShouldReturnContent() {
        final var content = file.getContent(reader);
        assertNotNull(content);
        assertEquals(List.of("TEST"), content);
    }
}
