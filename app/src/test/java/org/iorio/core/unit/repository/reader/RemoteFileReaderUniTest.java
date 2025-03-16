package org.iorio.core.unit.repository.reader;

import jdk.jfr.Description;
import org.iorio.core.repository.FileReader;
import org.iorio.core.repository.remote.html.RemoteFileReaderImpl;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RemoteFileReaderUniTest {
    private final FileReader<URL> reader = new RemoteFileReaderImpl();

    @Description("If the URL is valid, then It should be possible to read the Its content")
    @Test
    @Tag("unit")
    public void testContentOfExistingFileShouldExist() throws MalformedURLException {
        final List<String> content = reader.getContent(URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/file1").toURL());
        assertNotNull(content);
        assertFalse(content.isEmpty());
    }
}
