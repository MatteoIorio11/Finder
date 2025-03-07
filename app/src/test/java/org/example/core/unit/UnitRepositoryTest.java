package org.example.core.unit;


import jdk.jfr.Description;
import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.remote.RemoteDirectoryImpl;
import org.example.core.repository.remote.RemoteRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class UnitRepositoryTest {
    private static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository;
    @BeforeAll
    public static void init() throws MalformedURLException {
        remoteRepository = new RemoteRepositoryImpl("RepositoryTest", URI.create("https://github.com/MatteoIorio11/PPS-23-ScalaSim").toURL());
    }

    @Description("Creating a repository with a null name should throw an exception")
    @Test
    @Tag("unit")
    public void testCreateRepositoryWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new RemoteRepositoryImpl(null, URI.create("www.example.com").toURL()));
    }

    @Description("Creating a repository with a null path should throw an exception")
    @Test
    @Tag("unit")
    public void testCreateRepositoryWithNullPath() {
        assertThrows(NullPointerException.class, () -> new RemoteRepositoryImpl("RepositoryTest", null));
    }

    @Description("Querying a repository with a non existing directory should return False")
    @Test
    @Tag("unit")
    public void testQueryNonExistingDirectory() {
        assertFalse(remoteRepository.hasDirectory("nonExistingDirectory"));
    }

    @Description("Querying a repository with a non existing file should return False")
    @Test
    @Tag("unit")
    public void testQueryNonExistingFile() {
        assertFalse(remoteRepository.hasFile("nonExistingFile"));
    }

    @Description("Adding a null directory to a repository should throw an exception")
    @Test
    @Tag("unit")
    public void testAddNullDirectory() {
        assertThrows(NullPointerException.class, () -> remoteRepository.addDirectory(null));
    }

    @Description("Adding a null file to a repository should throw an exception")
    @Test
    @Tag("unit")
    public void testAddNullFile() {
        assertThrows(NullPointerException.class, () -> remoteRepository.addFile(null));
    }

    @Description("Adding a directory inside a repository, It should be possible to retrieve it")
    @Test
    @Tag("unit")
    public void testAddDirectory() throws MalformedURLException {
        final var directory = new RemoteDirectoryImpl("directory", URI.create("https://github.com/MatteoIorio11/PPS-23-ScalaSim").toURL());
        remoteRepository.addDirectory(directory);
        assertTrue(remoteRepository.hasDirectory("directory"));
        assertNotNull(remoteRepository.getDirectory("directory"));
    }
}
