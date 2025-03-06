package org.example.core.diff;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.RepositoryFactory;
import org.example.core.repository.local.LocalFileReaderImpl;
import org.example.core.repository.remote.RemoteFileReaderImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Check the difference between two branches
 */
public class CheckDifference {
    private final static String GITHUB_URL = "https://github.com";
    /**
     * Check the difference between two branches
     * @param user the user
     * @param repo the repository
     * @param accessToken the access token
     * @param localPath the local path
     * @param branchA the first branch
     * @param branchB the second branch
     * @return the list of differences
     * @throws MalformedURLException if the URL is malformed
     */
    public static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>>  checkDifference(final String user, final String repo, final String accessToken, final String localPath, final String branchA, final String branchB) throws MalformedURLException {
        final URL url = URI.create(GITHUB_URL + "/" + user + "/" + repo + "/tree/" + branchA).toURL();
        BranchSwitcher.switchBranch(localPath, branchB);
        final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository = RepositoryFactory.remoteRepository(repo, url, accessToken);
        final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository = RepositoryFactory.localRepository(repo, Path.of(localPath));
        final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>>  output = new LinkedList<>(checkForDifferences(commonFilesFromRepository(localRepository, remoteRepository)));
        checkCommonDirectoriesFromRepository(localRepository, remoteRepository)
                .forEach(entry -> output.addAll(recursiveCheck(checkCommonDirectories(entry.getKey(), entry.getValue()))));
        return output;
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>>  recursiveCheck(final List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> commonDirectories){
        final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> differences = new LinkedList<>();
        commonDirectories
                .forEach(entry -> {
                    final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFiles = commonFilesFromDirectory(entry.getKey(), entry.getValue());
                    differences.addAll(checkForDifferences(commonFiles));
                    differences.addAll(recursiveCheck(checkCommonDirectories(entry.getKey(), entry.getValue())));
                });
        return differences;
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFilesFromRepository(final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository, final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository) {
        return localRepository.getFiles().stream()
                .filter(file -> remoteRepository.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteRepository.getFile(file.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> checkCommonDirectoriesFromRepository(final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository, final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository) {
        return localRepository.getDirectories().stream()
                .filter(directory -> remoteRepository.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteRepository.getDirectory(directory.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> checkCommonDirectories(final AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDirectory, final AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDirectory) {
        return localDirectory.getInnerDirectories().stream()
                .filter(directory -> remoteDirectory.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteDirectory.getDirectory(directory.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFilesFromDirectory(final AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDir, final AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDir) {
        return localDir.getFiles().stream()
                .filter(file -> remoteDir.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteDir.getFile(file.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> checkForDifferences(final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFiles) {
        final var localReader = new LocalFileReaderImpl();
        final var remoteReader = new RemoteFileReaderImpl();
        return commonFiles.stream()
                .filter(commonFile -> !commonFile.getKey().getContent(localReader).equals(commonFile.getValue().getContent(remoteReader)))
                .toList();
    }
}
