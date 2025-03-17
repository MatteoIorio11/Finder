package org.iorio.core.integration.repository.directory;

import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.FileReader;
import org.iorio.core.repository.remote.graphql.RemoteDirectoryQLImpl;
import org.iorio.core.repository.remote.graphql.RemoteFileQLImpl;
import org.iorio.core.repository.remote.graphql.RemoteFileReaderQLImpl;
import org.junit.jupiter.api.BeforeAll;

public class RemoteDirectoryQLIntegrationTest extends AbstractRepositoryDirectoryIntegrationTest<String, AbstractRepositoryFile<String>> {
    private static AbstractRepositoryDirectory<String, AbstractRepositoryFile<String>> remoteDirectory;
    protected RemoteDirectoryQLIntegrationTest() {
        super(remoteDirectory,
                new RemoteFileReaderQLImpl(
                        "MatteoIorio11",
                        "FinderTest",
                        "main"));

    }

    @BeforeAll
    public static void setUp() {
        SecretConfigurator.readTestSecrets();
        remoteDirectory = new RemoteDirectoryQLImpl("FinderTest/dir1",
                "dir1");
        remoteDirectory.addFile(
                new RemoteFileQLImpl("FinderTest/dir1/file3",
                        "dir1/file3")
        );
    }
}
