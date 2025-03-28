package org.iorio.core.unit.repository;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryElement;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.remote.html.RemoteFileImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractRepositoryElementUnitTest<T> {
    private final RepositoryElement<T> element;

    public AbstractRepositoryElementUnitTest(final RepositoryElement<T> element) {
        this.element = element;
    }

    @Description("The named of the element and the path should not be null")
    @Test
    @Tag("unit")
    public void testElementParametersAreNotNull() {
        assertNotNull(this.element.getName());
        assertNotNull(this.element.getPath());
    }

    @Description("Creating a new Repository element it should be possible to retrieve the name")
    @Test
    @Tag("unit")
    public void testGetName() throws MalformedURLException {
        final String expected = "test";
        final RepositoryElement<URL> remoteFile = new RemoteFileImpl(expected, URI.create("https://github.com/MatteoIorio11/PPS-23-ScalaSim").toURL());
        final RepositoryElement<Path> localFile = new LocalFileImpl(expected, Path.of("src/test/resources"));
        assertEquals(expected, remoteFile.getName());
        assertEquals(expected, localFile.getName());
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
