package org.iorio.core.diff;

import org.apache.commons.text.diff.StringsComparator;
import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Check the difference between two branches
 */
public abstract class AbstractCheckDifference<X, Y> {
    protected final static Logger logger = LoggerFactory.getLogger(AbstractCheckDifference.class);
    /**
     * Check the difference between two branches, this method will return a list of pair of files that present differences.
     * @param user the user
     * @param repo the remote repository name
     * @param accessToken the access token
     * @param localPath the local path where the repository is stored
     * @param branchA the first branch
     * @param branchB the second branch
     * @return the list of differences
     */
    public abstract List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> checkDifference(final String user, final String repo, final String accessToken, final String localPath, final String branchA, final String branchB);

    protected List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> lookForDifferences(final AbstractRepository<X, AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryFile<X>> repo1,
                                                                                                            final AbstractRepository<Y, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>, AbstractRepositoryFile<Y>> repo2,
                                                                                                            final FileReader<X> reader1,
                                                                                                            final FileReader<Y> reader2) {
        final List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> output =
                new LinkedList<>(checkForDifferences(commonFilesFromRepository(repo1, repo2), reader1, reader2));
        checkCommonDirectoriesFromRepository(repo1, repo2)
                .forEach(entry -> {
                    output.addAll(checkForDifferences(commonFilesFromDirectory(entry.getKey(), entry.getValue()), reader1, reader2));
                    output.addAll(checkAllDifferences(checkCommonDirectories(entry.getKey(), entry.getValue()), reader1, reader2));
                });
        return output;
    }

    protected List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> checkAllDifferences(final List<Map.Entry<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> commonDirectories,
                                                                                                      final FileReader<X> reader1,
                                                                                                      final FileReader<Y> reader2) {
        final List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> differences = new LinkedList<>();
        final Stack<Map.Entry<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> stack = new Stack<>();

        stack.addAll(commonDirectories);
        // Avoid StackOverflowError in case of very deep directory structure
        while (!stack.isEmpty()) {
            final var commonPair = stack.pop();
            final var directories = checkCommonDirectories(commonPair.getKey(), commonPair.getValue());
            differences.addAll(checkForDifferences(commonFilesFromDirectory(commonPair.getKey(), commonPair.getValue()), reader1, reader2));
            stack.addAll(directories);
        }
        return differences;
    }

    protected List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> commonFilesFromRepository(final AbstractRepository<X, AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryFile<X>> localRepository, final AbstractRepository<Y, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>, AbstractRepositoryFile<Y>> remoteRepository) {
        logger.info("Checking common files");
        return localRepository.getFiles().stream()
                .filter(file -> remoteRepository.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteRepository.getFile(file.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    protected List<Map.Entry<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> checkCommonDirectoriesFromRepository(final AbstractRepository<X, AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryFile<X>> localRepository, final AbstractRepository<Y, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>, AbstractRepositoryFile<Y>> remoteRepository) {
        logger.info("Checking common directories inside repository");
        return localRepository.getDirectories().stream()
                .filter(directory -> remoteRepository.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteRepository.getDirectory(directory.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    protected List<Map.Entry<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> checkCommonDirectories(final AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>> localDirectory, final AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>> remoteDirectory) {
        logger.info("Checking common directories");
        return localDirectory.getInnerDirectories().stream()
                .filter(directory -> remoteDirectory.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteDirectory.getDirectory(directory.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    protected List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> commonFilesFromDirectory(final AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>> localDir, final AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>> remoteDir) {
        logger.info("Checking common files inside directory");
        return localDir.getFiles().stream()
                .filter(file -> remoteDir.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteDir.getFile(file.getName())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .toList();
    }

    protected List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> checkForDifferences(final List<Map.Entry<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> commonFiles,
                                                                                                      final FileReader<X> reader1,
                                                                                                      final FileReader<Y> reader2) {
        logger.info("Checking for differences between files");
        return commonFiles.stream()
                .filter(commonFile -> {
                    final var content1 = normalize(String.join("\n", commonFile.getKey().getContent(reader1)));
                    final var content2 = normalize(String.join("\n", commonFile.getValue().getContent(reader2)));
                    final StringsComparator s = new StringsComparator(content1, content2);
                    return s.getScript().getLCSLength() != content1.length();
                })
                .toList();
    }

    protected String normalize(final String content) {
        return Normalizer.normalize(content, Normalizer.Form.NFKC) // Normalize Unicode composition
                .replaceAll("\\p{Cf}", "") // Remove invisible control characters
                .replaceAll("\\p{Zs}+", " ") // Normalize all whitespace (e.g., non-breaking spaces)
                .replaceAll("[\\r\\n]+", "\n") // Standardize newlines to "\n"
                .trim(); // Remove leading/trailing spaces
    }

}
