package org.iorio.core.diff;

import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryFactory;
import org.iorio.core.repository.local.LocalFileReaderImpl;
import org.iorio.core.repository.remote.graphql.RemoteFileReaderQLImpl;
import org.iorio.core.utils.Pair;

import java.nio.file.Path;
import java.util.List;

public class CheckDifferenceImpl extends AbstractCheckDifference<Path, String> {

    @Override
    public List<Pair<AbstractRepositoryFile<Path>, AbstractRepositoryFile<String>>> checkDifference(String user, String repo, String accessToken, String localPath, String branchA, String branchB) {
        logger.info("Checking difference between branches");
        System.setProperty("GITHUB_TOKEN", accessToken);
        BranchSwitcher.switchBranch(localPath, branchB);
        final var remoteRepository = RepositoryFactory.remoteRepositoryQL(repo, user, branchA, accessToken);
        final var localRepository = RepositoryFactory.localRepository(repo, Path.of(localPath));
        final var localReader = new LocalFileReaderImpl();
        final var remoteReader = new RemoteFileReaderQLImpl(user, repo, branchA);
        return lookForDifferences(localRepository, remoteRepository, localReader, remoteReader);
    }
}
