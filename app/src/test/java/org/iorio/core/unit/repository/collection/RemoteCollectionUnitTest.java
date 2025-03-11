package org.iorio.core.unit.repository.collection;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.RemoteCollection;
import org.iorio.core.repository.remote.RemoteDirectoryImpl;
import org.iorio.core.repository.remote.RemoteFileImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoteCollectionUnitTest {
    @Description("If the user creates a remote collection with a null list of files, this should throw an exception.")
    @Test
    @Tag("unit")
    public void testRemoteCollectionNullFiles() {
        assertThrows(NullPointerException.class, () -> new RemoteCollection(null, List.of()));
    }

    @Description("If the user creates a remote collection with a null list of directories, this should throw an exception.")
    @Test
    @Tag("unit")
    public void testRemoteCollectionNullDirectories() {
        assertThrows(NullPointerException.class, () -> new RemoteCollection(List.of(), null));
    }

    @Description("If the user creates a remote collection with non null values, It should be possible to retrieve those values")
    @Test
    @Tag("unit")
    public void testRemoteCollection() throws MalformedURLException {
        final List<AbstractRepositoryFile<URL>> files = List.of(
                new RemoteFileImpl("file", URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/file1").toURL())
        );
        final List<AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>> directories = List.of(
                new RemoteDirectoryImpl("directory", URI.create("https://github.com/MatteoIorio11/FinderTest/tree/main/dir1").toURL())
        );
        final RemoteCollection remoteCollection = new RemoteCollection(files, directories);
        assertEquals(files, remoteCollection.getFiles());
        assertEquals(directories, remoteCollection.getDirectories());
    }
}
