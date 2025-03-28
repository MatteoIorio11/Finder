package org.iorio.core.unit.repository.collection;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryCollection;
import org.iorio.core.repository.remote.html.RemoteCollection;
import org.iorio.core.repository.remote.html.RemoteDirectoryImpl;
import org.iorio.core.repository.remote.html.RemoteFileImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoteCollectionUnitTest extends AbstractRepositoryCollectionUnitTest<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>>{
    private static RepositoryCollection<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> collection;
    private static List<AbstractRepositoryFile<URL>> files;
    private static List<AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>> directories;
    public RemoteCollectionUnitTest() {
        super(collection);
    }

    @BeforeAll
    public static void init() {
        try {
            files = List.of(
                    new RemoteFileImpl("file2", URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/file1").toURL()),
                    new RemoteFileImpl("file1", URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/file1").toURL())
            );
            directories = List.of(
                    new RemoteDirectoryImpl("dir1", URI.create("https://github.com/MatteoIorio11/FinderTest/tree/main/dir1").toURL())
            );
            collection = new RemoteCollection(files, directories);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Description("If we get the list of files, then It should return the list of files")
    @Test
    @Tag("unit")
    public void testGetFiles() {
        assertFalse(collection.getFiles().isEmpty());
        assertEquals(files, collection.getFiles());
    }

    @Description("If we get the list of directories, then It should return the list of directories")
    @Test
    @Tag("unit")
    public void testGetDirectories() {
        assertFalse(collection.getDirectories().isEmpty());
        assertEquals(directories, collection.getDirectories());
    }

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
