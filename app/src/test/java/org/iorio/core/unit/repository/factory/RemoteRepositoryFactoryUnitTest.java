package org.iorio.core.unit.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoteRepositoryFactoryUnitTest {
    @Description("If the repository URL is null, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testRemoteRepositoryWithNullUrl() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepository("repoName", null, "token"));
        assertEquals("The repository URL cannot be null",
                assertThrows(IllegalArgumentException.class, () -> RepositoryFactory.remoteRepository("repoName", null, "token")).getMessage());
    }

    @Description("If the repository token is null, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testRemoteRepositoryWithNullToken() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepository("repoName", URI.create("https://internship.jetbrains.com/admissions").toURL(), null));
        assertEquals("The repository token cannot be null",
                assertThrows(IllegalArgumentException.class, () -> RepositoryFactory.remoteRepository("repoName", URI.create("https://internship.jetbrains.com/admissions").toURL(), null)).getMessage());
    }

    @Description("If the repository name is null, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testRemoteRepositoryWithNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepository(null, URI.create("https://internship.jetbrains.com/admissions").toURL(), "token"));
        assertEquals("The repository name cannot be null",
                assertThrows(IllegalArgumentException.class, () -> RepositoryFactory.remoteRepository(null, URI.create("https://internship.jetbrains.com/admissions").toURL(), "token")).getMessage());
    }

    @Description("If the repository URL does not exists, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testRemoteRepositoryWithInvalidUrl() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepository("repoName", URI.create("https://matteoiorioaaaa.eu").toURL(), "token"));
    }


}
