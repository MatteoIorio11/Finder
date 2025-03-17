package org.iorio.core.integration.repository.file;

import org.iorio.core.configuration.SecretConfigurator;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.remote.graphql.RemoteFileQLImpl;
import org.iorio.core.repository.remote.graphql.RemoteFileReaderQLImpl;
import org.junit.jupiter.api.BeforeAll;


public class RemoteRepositoryFileQLIntegrationTest extends AbstractRepositoryFileIntegrationTest<String, AbstractRepositoryFile<String>> {
    public RemoteRepositoryFileQLIntegrationTest() {
        super(
                new RemoteFileQLImpl(
                        "FinderTest/file1",
                       "file1"
                ),
                new RemoteFileReaderQLImpl("MatteoIorio11",
                        "FinderTest",
                        "main"));
    }
    @BeforeAll
    public static void setUp() {
        SecretConfigurator.readTestSecrets();
    }
}
