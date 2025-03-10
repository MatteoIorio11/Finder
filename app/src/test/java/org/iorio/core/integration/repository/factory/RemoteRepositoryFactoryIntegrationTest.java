package org.iorio.core.integration.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.repository.RepositoryFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

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
        final List<String> remoteFiles = List.of(
                "FinderTest/file1",
                "FinderTest/file2",
                "FinderTest/.gitignore"
        );
        assertNotNull(remoteRepo);
        assertEquals(remoteFiles.size(), remoteRepo.getFiles().size());
        assertTrue(remoteFiles.stream().allMatch(remoteRepo::hasFile));
    }

}
