package org.iorio.core.diff;

import org.apache.commons.text.diff.StringsComparator;
import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.RepositoryFactory;
import org.iorio.core.repository.local.LocalFileReaderImpl;
import org.iorio.core.repository.remote.html.RemoteFileReaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Check the difference between two branches
 */
public class CheckDifference {
    private final static String GITHUB_URL = "https://github.com";
    private final static Logger logger = LoggerFactory.getLogger(CheckDifference.class);
    /**
     * Check the difference between two branches, this method will return a list of pair of files that present differences.
     * @param user the user
     * @param repo the remote repository name
     * @param accessToken the access token
     * @param localPath the local path where the repository is stored
     * @param branchA the first branch
     * @param branchB the second branch
     * @return the list of differences
     * @throws MalformedURLException if the URL is malformed
     */
    public static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>>  checkDifference(final String user, final String repo, final String accessToken, final String localPath, final String branchA, final String branchB) throws MalformedURLException {
        logger.info("Checking difference between branches");
        System.setProperty("GITHUB_TOKEN", accessToken);
        final URL url = URI.create(GITHUB_URL + "/" + user + "/" + repo + "/tree/" + branchA).toURL();
        BranchSwitcher.switchBranch(localPath, branchB);
        final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository = RepositoryFactory.remoteRepository(repo, url, accessToken);
        final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository = RepositoryFactory.localRepository(repo, Path.of(localPath));
        final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>>  output = new LinkedList<>(checkForDifferences(commonFilesFromRepository(localRepository, remoteRepository)));
        checkCommonDirectoriesFromRepository(localRepository, remoteRepository)
                .forEach(entry -> {
                    output.addAll(checkForDifferences(commonFilesFromDirectory(entry.getKey(), entry.getValue())));
                    output.addAll(checkAllDifferences(checkCommonDirectories(entry.getKey(), entry.getValue())));
                });
        return output;
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> checkAllDifferences(final List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> commonDirectories){
        final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> differences = new LinkedList<>();
        final Stack<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> stack = new Stack<>();

        stack.addAll(commonDirectories);
        // Avoid StackOverflowError in case of very deep directory structure
        while (!stack.isEmpty()) {
            final var commonPair = stack.pop();
            final var directories = checkCommonDirectories(commonPair.getKey(), commonPair.getValue());
            differences.addAll(checkForDifferences(commonFilesFromDirectory(commonPair.getKey(), commonPair.getValue())));
            stack.addAll(directories);
        }
        return differences;
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFilesFromRepository(final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository, final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository) {
        logger.info("Checking common files");
        return localRepository.getFiles().stream()
                .filter(file -> remoteRepository.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteRepository.getFile(file.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> checkCommonDirectoriesFromRepository(final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository, final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository) {
        logger.info("Checking common directories inside repository");
        return localRepository.getDirectories().stream()
                .filter(directory -> remoteRepository.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteRepository.getDirectory(directory.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> checkCommonDirectories(final AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDirectory, final AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDirectory) {
        logger.info("Checking common directories");
        return localDirectory.getInnerDirectories().stream()
                .filter(directory -> remoteDirectory.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteDirectory.getDirectory(directory.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFilesFromDirectory(final AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDir, final AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDir) {
        logger.info("Checking common files inside directory");
        return localDir.getFiles().stream()
                .filter(file -> remoteDir.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteDir.getFile(file.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> checkForDifferences(final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFiles) {
        logger.info("Checking for differences between files");
        final var localReader = new LocalFileReaderImpl();
        final var remoteReader = new RemoteFileReaderImpl();
        return commonFiles.stream()
                .filter(commonFile -> {
                    final var content1 = normalize(String.join("\n", commonFile.getKey().getContent(localReader)));
                    final var content2 = normalize(String.join("\n", commonFile.getValue().getContent(remoteReader)));
                    final StringsComparator s = new StringsComparator(content1, content2);
                    return s.getScript().getLCSLength() != content1.length();
                })
                .toList();
    }

    private static String normalize(final String content) {
        return Normalizer.normalize(content, Normalizer.Form.NFKC) // Normalize Unicode composition
                .replaceAll("\\p{Cf}", "") // Remove invisible control characters
                .replaceAll("\\p{Zs}+", " ") // Normalize all whitespace (e.g., non-breaking spaces)
                .replaceAll("[\\r\\n]+", "\n") // Standardize newlines to "\n"
                .trim(); // Remove leading/trailing spaces
    }

}
