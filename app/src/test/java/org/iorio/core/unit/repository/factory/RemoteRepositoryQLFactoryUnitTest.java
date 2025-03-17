package org.iorio.core.unit.repository.factory;

import jdk.jfr.Description;
import org.iorio.core.repository.RepositoryFactory;
import org.iorio.core.repository.remote.graphql.RemoteRepositoryQLImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RemoteRepositoryQLFactoryUnitTest {
    @Description("If the repositrory name is Null an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testInvalidRepositoryName() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepositoryQL(null, "owner", "branch", "token"));
    }

    @Description("If the owner is Null an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testInvalidOwner() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepositoryQL("repoName", null, "branch", "token"));
    }

    @Description("If the branch is Null an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testInvalidBranch() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepositoryQL("repoName", "owner", null, "token"));
    }

    @Description("If the token is Null an IllegalArgumentException should be thrown")
    @Test
    @Tag("unit")
    public void testInvalidToken() {
        assertThrows(IllegalArgumentException.class,
                () -> RepositoryFactory.remoteRepositoryQL("repoName", "owner", "branch", null));
    }

    @Description("If none of the input values are null, and all the input values are valid, a repository should be created")
    @Test
    @Tag("unit")
    public void testValidInput() {
        try(final var mockFactory = Mockito.mockStatic(RepositoryFactory.class)) {
            mockFactory.when(() -> RepositoryFactory.remoteRepositoryQL("FinderTest", "MatteoIorio11", "main", "token"))
                    .thenReturn(new RemoteRepositoryQLImpl("FinderTest", "apath"));
            final var repository = RepositoryFactory.remoteRepositoryQL("FinderTest", "MatteoIorio11", "main", "token");
            assertEquals("FinderTest", repository.getName());
            assertEquals("apath", repository.getPath());
        }
    }
}
