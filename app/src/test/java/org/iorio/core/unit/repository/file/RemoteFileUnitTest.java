package org.iorio.core.unit.repository.file;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.RemoteFileImpl;
import org.iorio.core.repository.remote.RemoteFileReaderImpl;
import org.iorio.core.unit.repository.AbstractRepositoryElementUnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RemoteFileUnitTest extends AbstractRepositoryElementUnitTest<URL> {
    private static AbstractRepositoryFile<URL> file;
    private static RemoteFileReaderImpl reader;
    public RemoteFileUnitTest() {
        super(file);
    }

    @BeforeAll
    public static void init() throws MalformedURLException {
        reader = Mockito.mock(RemoteFileReaderImpl.class);
        file = new RemoteFileImpl("FileTest",
                URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/file1").toURL());
        System.setProperty("GITHUB_TOKEN", "");
        when(reader.getContent(file.getPath())).thenReturn(List.of("TEST"));
        when(reader.getContent(new URL("https://napoli"))).thenThrow(new RuntimeException());
    }

    @Description("If the file does not exist, then It should throw an exception")
    @Test
    @Tag("unit")
    public void testContentOfNonExistingFileShouldThrowException() throws MalformedURLException {
        final var nonExistingFile = new RemoteFileImpl("NonExistingFile",
                URI.create("https://napoli").toURL());
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
