package org.iorio.core.unit.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoteRepositoryFactoryUnitTest {
    @Description("If the repository URL is null, an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testRemoteRepositoryWithNullUrl() {
        assertThrows(IllegalArgumentException.class, () -> RepositoryFactory.remoteRepository("repoName", null, "token"));
    }

    
}
