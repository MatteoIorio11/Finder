package org.iorio.core.integration.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.repository.RepositoryFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class RemoteRepositoryFactoryIntegrationTest {
    @BeforeAll
    public static void setUp() {
        SecretConfigurator.readTestSecrets();
    }

    @Description("If the repository URL is valid, then It should be possible to create a remote repository")
    @Test
    @Tag("integration")
    public void testRemoteRepositoryWithValidUrl() throws MalformedURLException {
        final var remoteRepo = RepositoryFactory.remoteRepository(
                "FinderTest",
                URI.create("https://github.com/MatteoIorio11/FinderTest").toURL(),
                System.getProperty("GITHUB_TOKEN")
        );
        final var directory = remoteRepo.getDirectory("FinderTest/dir1");
        assertNotNull(remoteRepo);
        // Check files and directories
        assertEquals(RepositoryElements.files.size(), remoteRepo.getFiles().size());
        assertEquals(RepositoryElements.directories.size(), remoteRepo.getDirectories().size());
        // Check the inner directory
        assertTrue(directory.isPresent());
        // Check all the files inside the directory
        assertEquals(RepositoryElements.filesInDirectory.size(), directory.get().getFiles().size());
        // Check if all the files and directories are present
        assertTrue(RepositoryElements.files.stream().allMatch(remoteRepo::hasFile));
        assertTrue(RepositoryElements.directories.stream().allMatch(remoteRepo::hasDirectory));
        assertTrue(RepositoryElements.filesInDirectory.stream().allMatch(directory.get()::hasFile));
    }

}
