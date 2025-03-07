package org.example.core.unit;


import jdk.jfr.Description;
import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.remote.RemoteRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitRepositoryTest extends AbstractSecretConfigurator {
    private static AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository;
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
}
