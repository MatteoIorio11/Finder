package org.iorio.core.unit.repository;


import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.RemoteDirectoryImpl;
import org.iorio.core.repository.remote.RemoteFileImpl;
import org.iorio.core.repository.remote.RemoteRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryUnitTest extends AbstractRepositoryElementUnitTest<URL> {
    private static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository;

    public RepositoryUnitTest() {
        super(remoteRepository);
    }
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
        assertTrue(remoteRepository.getDirectory("directory").isPresent());
        assertEquals(directory, remoteRepository.getDirectory("directory").get());
        assertFalse(remoteRepository.getDirectories().isEmpty());
    }

    @Description("Adding a file inside a repository, It should be possible to retrieve it")
    @Test
    @Tag("unit")
    public void testAddFile() throws MalformedURLException {
        final var file = new RemoteFileImpl("file", URI.create("https://github.com/MatteoIorio11/PPS-23-ScalaSim").toURL());
        remoteRepository.addFile(file);
        assertTrue(remoteRepository.hasFile("file"));
        assertTrue(remoteRepository.getFile("file").isPresent());
        assertEquals(file, remoteRepository.getFile("file").get());
        assertFalse(remoteRepository.getFiles().isEmpty());
    }
}
