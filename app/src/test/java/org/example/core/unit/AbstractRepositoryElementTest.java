package org.example.core.unit;

import jdk.jfr.Description;
import org.example.core.repository.RepositoryElement;
import org.example.core.repository.local.LocalFileImpl;
import org.example.core.repository.remote.RemoteFileImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractRepositoryElementTest {
    @Description("Creating a new Repository element it should be possible to retrieve the name")
    @Test
    @Tag("unit")
    public void testGetName() throws MalformedURLException {
        final RepositoryElement<URL> remoteFile = new RemoteFileImpl("test", URI.create("https://github.com/MatteoIorio11/PPS-23-ScalaSim").toURL());
        final RepositoryElement<Path> localFile = new LocalFileImpl("test", Path.of("src/test/resources"));
        assertEquals("test", remoteFile.getName());
        assertEquals("test", localFile.getName());
    }

    @Description("Creating a new Repository element it should be possible to retrieve the path")
    @Test
    @Tag("unit")
    public void testGetPath() throws MalformedURLException {
        final Path expected = Path.of("src/test/resources");
        final URL expectedURL = URI.create("https://github.com/MatteoIorio11/PPS-23-ScalaSim").toURL();
        final RepositoryElement<Path> localFile = new LocalFileImpl("test", expected);
        final RepositoryElement<URL> remoteFile = new RemoteFileImpl("test", expectedURL);
        assertEquals(expected, localFile.getPath());
        assertEquals(expectedURL, remoteFile.getPath());
    }
}
