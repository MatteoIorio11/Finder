package org.iorio.core.unit.repository.utils;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryUtils;
import org.iorio.core.repository.local.LocalFileImpl;
import org.iorio.core.repository.local.LocalRepositoryImpl;
import org.iorio.core.repository.remote.html.RemoteFileImpl;
import org.iorio.core.repository.remote.html.RemoteRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class RepositoryUtilUnitTest {
    @Mock static LocalRepositoryImpl localRepository = new LocalRepositoryImpl("test", Path.of("test"));
    @Mock static RemoteRepositoryImpl remoteRepository = new RemoteRepositoryImpl("test", URI.of("https://test.com").toURL());
    @Mock static LocalFileImpl localFile = new LocalFileImpl("test", Path.of("test"));
    @Mock static RemoteFileImpl remoteFile = new RemoteFileImpl("test", URI.of("test").toURL());
    @BeforeAll
    public static void init() {
        MockitoAnnotations.openMocks(RepositoryUtilUnitTest.class);
        when(localRepository.getFiles()).thenReturn(List.of(localFile));
        when(remoteRepository.getFiles()).thenReturn(List.of(remoteFile));
        when(localRepository.getFile("test")).thenReturn(Optional.of(localFile));
        when(remoteRepository.getFile("test")).thenReturn(Optional.of(remoteFile));
    }

    @Description("Giving two repositories with common files, It should be possible to retrieve all the common files")
    @Test
    @Tag("unit")
    public void testCommonFilesFromRepository() {

    }
}
