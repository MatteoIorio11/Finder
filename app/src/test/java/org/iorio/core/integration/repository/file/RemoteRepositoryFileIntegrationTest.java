package org.iorio.core.integration.repository.file;

import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.RemoteFileImpl;
import org.iorio.core.repository.remote.RemoteFileReaderImpl;
import org.junit.jupiter.api.BeforeAll;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class RemoteRepositoryFileIntegrationTest extends AbstractRepositoryFileIntegrationTest<URL, AbstractRepositoryFile<URL>> {
    public RemoteRepositoryFileIntegrationTest() throws MalformedURLException {
        super(
                new RemoteFileImpl(
                        "FinderTest/file1",
                        URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/file1").toURL()
                ),
                new RemoteFileReaderImpl());
    }
    @BeforeAll
    public static void setUp() {
        SecretConfigurator.readTestSecrets();
    }
}
