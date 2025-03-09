package org.example.core.unit.repository;

import jdk.jfr.Description;
import org.example.core.repository.AbstractFileReader;
import org.example.core.repository.remote.RemoteFileReaderImpl;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class RemoteFileReaderUniTest {
    private final AbstractFileReader<URL> reader = new RemoteFileReaderImpl();

    @Description("If the URL is valid, then It should be possible to read the Its content")
    @Test
    @Tag("unit")
    public void testContentOfExistingFileShouldExist() throws MalformedURLException {
        final String content = reader.getContent(URI.create("https://github.com/MatteoIorio11/MatteoIorio11/blob/main/README.md").toURL());
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }
}
