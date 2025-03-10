package org.iorio.core.unit.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalRepositoryFactoryUnitTest {
    @Description("If the repository Path is null an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testLocalRepositoryWithNullPath() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.localRepository("repoName", null));
        assertEquals("The repository path can not be null",
                assertThrows(IllegalArgumentException.class,
                        () -> RepositoryFactory.localRepository("repoName", null)).getMessage());
    }

    @Description("If the repository name is null an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testLocalRepositoryWithNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.localRepository(null, Path.of("https://internship.jetbrains.com/admissions")));
        assertEquals("The repository name can not be null",
                assertThrows(IllegalArgumentException.class,
                        () -> RepositoryFactory.localRepository(null, Path.of("https://internship.jetbrains.com/admissions"))).getMessage());
    }

    @Description("If the repository path does not exist an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testLocalRepositoryWithInvalidPath() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.localRepository("repoName", Path.of("INVALID PATH")));
        assertEquals("The repository path does not exist",
                assertThrows(IllegalArgumentException.class,
                        () -> RepositoryFactory.localRepository("repoName", Path.of("INVALID PATH"))).getMessage());
    }
}
