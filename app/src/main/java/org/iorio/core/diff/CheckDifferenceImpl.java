package org.iorio.core.diff;

import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryFactory;
import org.iorio.core.repository.local.LocalFileReaderImpl;
import org.iorio.core.repository.remote.html.RemoteFileReaderImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CheckDifferenceImpl extends CheckDifference<Path, URL> {
    private final static String GITHUB_URL = "https://github.com";

    @Override
    public List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> checkDifference(String user, String repo, String accessToken, String localPath, String branchA, String branchB) {
        logger.info("Checking difference between branches");
        System.setProperty("GITHUB_TOKEN", accessToken);
        try {
            final URL url = URI.create(GITHUB_URL + "/" + user + "/" + repo + "/tree/" + branchA).toURL();
            BranchSwitcher.switchBranch(localPath, branchB);
            final var remoteRepository = RepositoryFactory.remoteRepository(repo, url, accessToken);
            final var localRepository = RepositoryFactory.localRepository(repo, Path.of(localPath));
            final var localReader = new LocalFileReaderImpl();
            final var remoteReader = new RemoteFileReaderImpl();
            return lookForDifferences(localRepository, remoteRepository, localReader, remoteReader);
        }catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }
}
