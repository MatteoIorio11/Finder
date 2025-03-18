package org.iorio.core.diff;

import org.apache.commons.text.diff.StringsComparator;
import org.iorio.core.repository.*;
import org.iorio.core.utils.Pair;
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
    public abstract List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> checkDifference(final String user, final String repo, final String accessToken, final String localPath, final String branchA, final String branchB);

    protected List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> lookForDifferences(final AbstractRepository<X, AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryFile<X>> repo1,
                                                                                                            final AbstractRepository<Y, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>, AbstractRepositoryFile<Y>> repo2,
                                                                                                            final FileReader<X> reader1,
                                                                                                            final FileReader<Y> reader2) {
        final List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> output =
                new LinkedList<>(checkForDifferences(commonFilesFromRepository(repo1, repo2), reader1, reader2));
        checkCommonDirectoriesFromRepository(repo1, repo2)
                .forEach(entry -> {
                    output.addAll(checkForDifferences(commonFilesFromDirectory(entry.x(), entry.y()), reader1, reader2));
                    output.addAll(checkAllDifferences(checkCommonDirectories(entry.x(), entry.y()), reader1, reader2));
                });
        return output;
    }

    protected List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> checkAllDifferences(final List<Pair<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> commonDirectories,
                                                                                                      final FileReader<X> reader1,
                                                                                                      final FileReader<Y> reader2) {
        final List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> differences = new LinkedList<>();
        final Stack<Pair<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> stack = new Stack<>();

        stack.addAll(commonDirectories);
        // Avoid StackOverflowError in case of very deep directory structure
        while (!stack.isEmpty()) {
            final var commonPair = stack.pop();
            final var directories = checkCommonDirectories(commonPair.x(), commonPair.y());
            differences.addAll(checkForDifferences(commonFilesFromDirectory(commonPair.x(), commonPair.y()), reader1, reader2));
            stack.addAll(directories);
        }
        return differences;
    }

    protected List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> commonFilesFromRepository(final AbstractRepository<X, AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryFile<X>> localRepository, final AbstractRepository<Y, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>, AbstractRepositoryFile<Y>> remoteRepository) {
        logger.info("Checking common files");
        return RepositoryUtils.commonFilesFromRepository(localRepository, remoteRepository);
    }

    protected List<Pair<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> checkCommonDirectoriesFromRepository(final AbstractRepository<X, AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryFile<X>> localRepository, final AbstractRepository<Y, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>, AbstractRepositoryFile<Y>> remoteRepository) {
        logger.info("Checking common directories inside repository");
        return RepositoryUtils.checkCommonDirectoriesFromRepository(localRepository, remoteRepository);
    }

    protected List<Pair<AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>>, AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>>>> checkCommonDirectories(final AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>> localDirectory, final AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>> remoteDirectory) {
        logger.info("Checking common directories");
        return RepositoryUtils.checkCommonDirectories(localDirectory, remoteDirectory);
    }

    protected List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> commonFilesFromDirectory(final AbstractRepositoryDirectory<X, AbstractRepositoryFile<X>> localDir, final AbstractRepositoryDirectory<Y, AbstractRepositoryFile<Y>> remoteDir) {
        logger.info("Checking common files inside directory");
        return RepositoryUtils.commonFilesFromDirectory(localDir, remoteDir);
    }

    protected List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> checkForDifferences(final List<Pair<AbstractRepositoryFile<X>, AbstractRepositoryFile<Y>>> commonFiles,
                                                                                                      final FileReader<X> reader1,
                                                                                                      final FileReader<Y> reader2) {
        logger.info("Checking for differences between files");
        return RepositoryUtils.checkForDifferences(commonFiles,
                (file1, file2) -> {
                    final var content1 = normalize(String.join("\n", file1.getContent(reader1)));
                    final var content2 = normalize(String.join("\n", file2.getContent(reader2)));
                    final StringsComparator s = new StringsComparator(content1, content2);
                    return s.getScript().getLCSLength() != content1.length();
                });
    }

    protected String normalize(final String content) {
        return Normalizer.normalize(content, Normalizer.Form.NFKC) // Normalize Unicode composition
                .replaceAll("\\p{Cf}", "") // Remove invisible control characters
                .replaceAll("\\p{Zs}+", " ") // Normalize all whitespace (e.g., non-breaking spaces)
                .replaceAll("[\\r\\n]+", "\n") // Standardize newlines to "\n"
                .trim(); // Remove leading/trailing spaces
    }

}
