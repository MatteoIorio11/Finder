package org.iorio.core.unit.repository.utils;

import jdk.jfr.Description;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryUtils;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.local.LocalRepositoryImpl;
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
    private static LocalRepositoryImpl localRepository;
    private static LocalFileImpl localFile;
    private static RemoteRepositoryImpl remoteRepository;
    private static RemoteFileImpl remoteFile;
    @BeforeAll
    public static void init() throws MalformedURLException {
        localRepository = Mockito.mock(LocalRepositoryImpl.class);
        remoteRepository = Mockito.mock(RemoteRepositoryImpl.class);
        localFile = Mockito.mock(LocalFileImpl.class);
        remoteFile = Mockito.mock(RemoteFileImpl.class);
        when(localRepository.getName()).thenReturn("test");
        when(remoteRepository.getName()).thenReturn("test");
        when(localFile.getName()).thenReturn("test");
        when(remoteFile.getName()).thenReturn("test");
        when(localRepository.getFiles()).thenReturn(List.of(localFile));
        when(remoteRepository.getFiles()).thenReturn(List.of(remoteFile));
        when(localRepository.getFile("test")).thenReturn(Optional.of(localFile));
        when(remoteRepository.getFile("test")).thenReturn(Optional.of(remoteFile));
    }

    @Description("Giving two repositories with common files, It should be possible to retrieve all the common files")
    @Test
    @Tag("unit")
    public void testCommonFilesFromRepository() {
        final var expected = List.of(new Pair<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>(localFile, remoteFile));
        final var output = RepositoryUtils.commonFilesFromRepository(localRepository, remoteRepository);
        assertNotNull(output);
        assertFalse(output.isEmpty());
        assertEquals(expected, output);
    }
}
