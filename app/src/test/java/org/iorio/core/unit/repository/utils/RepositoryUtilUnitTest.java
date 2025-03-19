package org.iorio.core.unit.repository.utils;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryUtils;
import org.iorio.core.repository.local.LocalDirectoryImpl;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.local.LocalRepositoryImpl;
import org.iorio.core.repository.remote.html.RemoteDirectoryImpl;
import org.iorio.core.repository.remote.html.RemoteFileImpl;
import org.iorio.core.repository.remote.html.RemoteRepositoryImpl;
import org.iorio.core.utils.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RepositoryUtilUnitTest {
    private static AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository;
    private static AbstractRepositoryFile<Path> localFile;
    private static AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDirectory;
    private static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository;
    private static AbstractRepositoryFile<URL> remoteFile;
    private static AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDirectory;
    @BeforeAll
    public static void init() throws MalformedURLException {
        localRepository = Mockito.mock(LocalRepositoryImpl.class);
        remoteRepository = Mockito.mock(RemoteRepositoryImpl.class);
        localFile = Mockito.mock(LocalFileImpl.class);
        remoteFile = Mockito.mock(RemoteFileImpl.class);
        localDirectory = Mockito.mock(LocalDirectoryImpl.class);
        remoteDirectory = Mockito.mock(RemoteDirectoryImpl.class);
        // Name definition
        when(localRepository.getName()).thenReturn("test");
        when(remoteRepository.getName()).thenReturn("test");
        when(localFile.getName()).thenReturn("test");
        when(remoteFile.getName()).thenReturn("test");
        when(localDirectory.getName()).thenReturn("test");
        when(remoteDirectory.getName()).thenReturn("test");
        // getFiles definition
        when(localRepository.getFiles()).thenReturn(List.of(localFile));
        when(remoteRepository.getFiles()).thenReturn(List.of(remoteFile));
        when(localDirectory.getFiles()).thenReturn(List.of(localFile));
        when(remoteDirectory.getFiles()).thenReturn(List.of(remoteFile));
        // getFile definition
        when(localRepository.getFile("test")).thenReturn(Optional.of(localFile));
        when(remoteRepository.getFile("test")).thenReturn(Optional.of(remoteFile));
        // getFile from directory definition
        when(localDirectory.getFile("test")).thenReturn(Optional.of(localFile));
        when(remoteDirectory.getFile("test")).thenReturn(Optional.of(remoteFile));
        // getDirectories definition
        when(localRepository.getDirectories()).thenReturn(List.of(localDirectory));
        when(remoteRepository.getDirectories()).thenReturn(List.of(remoteDirectory));
        // getDirectory definition
        when(localRepository.getDirectory("test")).thenReturn(Optional.of(localDirectory));
        when(remoteRepository.getDirectory("test")).thenReturn(Optional.of(remoteDirectory));
    }

    @Description("Giving two repositories with common files, It should be possible to retrieve all the common files")
    @Test
    @Tag("unit")
    public void testCommonFilesFromRepository() {
        final var expected = List.of(new Pair<>(localFile, remoteFile));
        final var output = RepositoryUtils.commonFilesFromRepository(localRepository, remoteRepository);
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertEquals(expected, output);
    }

    @Description("Giving two repositories with common directories, It should be possible to retrieve all the common directories")
    @Test
    @Tag("unit")
    public void testCheckCommonDirectoriesFromRepository() {
        final var expected = List.of(new Pair<>(localDirectory, remoteDirectory));
        final var output = RepositoryUtils.checkCommonDirectoriesFromRepository(localRepository, remoteRepository);
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertEquals(expected, output);
    }

    @Description("Giving two directories with common files, It should be possible to retrieve all the common files")
    @Test
    @Tag("unit")
    public void testCommonFilesFromDirectory() {
        final var expected = List.of(new Pair<>(localFile, remoteFile));
        final var output = RepositoryUtils.commonFilesFromDirectory(localDirectory, remoteDirectory);
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertEquals(expected, output);
    }
}
