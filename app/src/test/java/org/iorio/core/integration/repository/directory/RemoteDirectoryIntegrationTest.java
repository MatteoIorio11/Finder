package org.iorio.core.integration.repository.directory;

import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.RemoteDirectoryImpl;
import org.iorio.core.repository.remote.RemoteFileImpl;
import org.iorio.core.repository.remote.RemoteFileReaderImpl;
import org.junit.jupiter.api.BeforeAll;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class RemoteDirectoryIntegrationTest extends AbstractRepositoryDirectoryIntegrationTest<URL, AbstractRepositoryFile<URL>> {
    private static AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDirectory;
    public RemoteDirectoryIntegrationTest() {
        super(
                remoteDirectory,
                new RemoteFileReaderImpl()
        );
    }

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        SecretConfigurator.readTestSecrets();
        remoteDirectory = new RemoteDirectoryImpl(
                "FinderTest/dir1",
                URI.create("https://github.com/MatteoIorio11/FinderTest/tree/main/dir1").toURL()
        );
        remoteDirectory.addFile(new RemoteFileImpl(
                "FinderTest/dir1/file3",
                URI.create("https://github.com/MatteoIorio11/FinderTest/blob/main/dir1/file3").toURL()));

    }
}
