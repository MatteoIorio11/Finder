package org.iorio.core.unit.repository.utils;

import jdk.jfr.Description;
import org.iorio.core.repository.*;
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
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RepositoryUtilsUnitTest {
    private static AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository;
    private static AbstractRepositoryFile<Path> localFile;
    private static AbstractRepositoryFile<Path> anotherFile;
    private static AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDirectory;
    private static AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> subDirectory;
    private static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository;
    private static AbstractRepositoryFile<URL> remoteFile;
    private static AbstractRepositoryFile<URL> anotherRemoteFile;
    private static AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDirectory;
    private static AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteSubDirectory;

    @BeforeAll
    public static void init() throws MalformedURLException {
        localRepository = Mockito.mock(LocalRepositoryImpl.class);
        remoteRepository = Mockito.mock(RemoteRepositoryImpl.class);
        localFile = Mockito.mock(LocalFileImpl.class);
        remoteFile = Mockito.mock(RemoteFileImpl.class);
        localDirectory = Mockito.mock(LocalDirectoryImpl.class);
        remoteDirectory = Mockito.mock(RemoteDirectoryImpl.class);
        subDirectory = Mockito.mock(LocalDirectoryImpl.class);
        remoteSubDirectory = Mockito.mock(RemoteDirectoryImpl.class);
        anotherFile = Mockito.mock(LocalFileImpl.class);
        anotherRemoteFile = Mockito.mock(RemoteFileImpl.class);


        // Name definition (all)
        when(localRepository.getName()).thenReturn("test");
        when(remoteRepository.getName()).thenReturn("test");
        when(localFile.getName()).thenReturn("test");
        when(remoteFile.getName()).thenReturn("test");
        when(localDirectory.getName()).thenReturn("test");
        when(remoteDirectory.getName()).thenReturn("test");
        when(subDirectory.getName()).thenReturn("test");
        when(remoteSubDirectory.getName()).thenReturn("test");
        // getFiles definition (repository-directory)
        when(localRepository.getFiles()).thenReturn(List.of(localFile));
        when(remoteRepository.getFiles()).thenReturn(List.of(remoteFile));
        when(localDirectory.getFiles()).thenReturn(List.of(localFile));
        when(remoteDirectory.getFiles()).thenReturn(List.of(remoteFile));
        // getFile definition (repository)
        when(localRepository.getFile("test")).thenReturn(Optional.of(localFile));
        when(remoteRepository.getFile("test")).thenReturn(Optional.of(remoteFile));
        // getFile from directory definition (directory)
        when(localDirectory.getFile("test")).thenReturn(Optional.of(localFile));
        when(remoteDirectory.getFile("test")).thenReturn(Optional.of(remoteFile));
        // getDirectories definition (repository)
        when(localRepository.getDirectories()).thenReturn(List.of(localDirectory));
        when(remoteRepository.getDirectories()).thenReturn(List.of(remoteDirectory));
        // getDirectory definition (repository-directory)
        when(localRepository.getDirectory("test")).thenReturn(Optional.of(subDirectory));
        when(remoteRepository.getDirectory("test")).thenReturn(Optional.of(remoteSubDirectory));
        when(localDirectory.getDirectory("test")).thenReturn(Optional.of(subDirectory));
        when(remoteDirectory.getDirectory("test")).thenReturn(Optional.of(remoteSubDirectory));
        // getInnerDirectories definition (directory)
        when(localDirectory.getInnerDirectories()).thenReturn(List.of(subDirectory));
        when(remoteDirectory.getInnerDirectories()).thenReturn(List.of(remoteSubDirectory));
        // getContent (file)
        when(localFile.getContent(any())).thenReturn(List.of("test"));
        when(anotherFile.getContent(any())).thenReturn(List.of("not-same"));
        when(remoteFile.getContent(any())).thenReturn(List.of("test"));
        when(anotherRemoteFile.getContent(any())).thenReturn(List.of("why-not?"));
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

    @Description("Giving two directories with common directories, It should be possible to retrieve all the common directories")
    @Test
    @Tag("unit")
    public void testCheckCommonDirectories() {
        final var expected = List.of(new Pair<>(subDirectory, remoteSubDirectory));
        final var output = RepositoryUtils.checkCommonDirectories(localDirectory, remoteDirectory);
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertEquals(expected, output);
    }

    @Description("Giving a list of common files, It should be possible to check if they have the same content")
    @Test
    @Tag("unit")
    public void testCheckForDifferences() {
        final var input = List.of(new Pair<>(localFile, remoteFile), new Pair<>(anotherFile, anotherRemoteFile));
        final var commonFiles = List.of(new Pair<>(localFile, remoteFile));
        final var output = RepositoryUtils.checkForDifferences(commonFiles,
                (x, y) ->
                        x.getContent(any()).equals(y.getContent(any())));
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertEquals(commonFiles, output);
    }
}
