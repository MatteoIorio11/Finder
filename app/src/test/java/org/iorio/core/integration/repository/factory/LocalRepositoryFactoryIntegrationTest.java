package org.iorio.core.integration.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LocalRepositoryFactoryIntegrationTest {

    @Description("If the repository path is valid, then It should be possible to create a local repository")
    @Test
    @Tag("integration")
    public void testLocalRepositoryWithValidPath() {
        final var localRepo = RepositoryFactory.localRepository(
                "FinderTest",
                Path.of(System.getProperty("user.home"), "FinderTest")
        );
        final var directory = localRepo.getDirectory("FinderTest/dir1");
        assertNotNull(localRepo);
        assertEquals("FinderTest", localRepo.getName());
        // Check for elements in the repository
        assertEquals(RepositoryElements.files.size(), localRepo.getFiles().size());
        assertEquals(RepositoryElements.directories.size(), localRepo.getDirectories().size());
        assertTrue(directory.isPresent());
        // Check for names
        assertTrue(localRepo.getFiles().stream().allMatch(file -> RepositoryElements.files.contains(file.getName())));
        assertTrue(localRepo.getDirectories().stream().allMatch(directory1 -> RepositoryElements.directories.contains(directory1.getName())));
        assertTrue(directory.get().getFiles().stream().allMatch(file -> RepositoryElements.filesInDirectory.contains(file.getName())));
    }
}
