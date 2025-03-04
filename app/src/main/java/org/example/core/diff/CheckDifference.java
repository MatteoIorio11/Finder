package org.example.core.diff;

import org.example.core.repository.Repository;
import org.example.core.repository.RepositoryFactory;
import org.example.core.repository.remote.RemoteDirectory;
import org.example.core.repository.remote.RemoteFile;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class CheckDifference {
    private final static String GITHUB_URL = "https://github.com";
    public static List<File> checkDifference(final String user, final String repo, final String accessToken, final String localPath, final String branchA, final String branchB) {
        // "https://github.com/MatteoIorio11/PPS-23-ScalaSim/tree/dev"
        try {
            final URL url = URI.create(GITHUB_URL + "/" + user + "/" + repo + "/tree/" + branchA).toURL();
//            final Repository<RemoteDirectory<RemoteFile>, RemoteFile> remoteRepositoryA = RepositoryFactory.remoteRepository(url, accessToken);
        } catch (Exception ignored) {
        }
        return null;
    }
}
