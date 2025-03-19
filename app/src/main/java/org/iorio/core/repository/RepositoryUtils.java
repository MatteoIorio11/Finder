package org.iorio.core.repository;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.iorio.core.utils.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class RepositoryUtils {
    private RepositoryUtils() {}

    /**
     * Check for common files between two repositories
     * @param repo1 First repository
     * @param repo2 Second repository
     * @return List of pairs of common files
     * @param <A> Type of path for the first repository
     * @param <B> Type of path for the second repository
     * @param <X> Type of the first repository
     * @param <Y> Type of the second repository
     */
    public static <A, B, X extends AbstractRepository<A, AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryFile<A>>,
            Y extends AbstractRepository<B, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryFile<A>, AbstractRepositoryFile<B>>> commonFilesFromRepository(
            @NonNull final X repo1,
            @NonNull final Y repo2) {
        return filterAndMap(repo1.getFiles(),
                (file) ->
                    repo2.getFile(file.getName())
                            .flatMap(f -> Optional.of(new Pair<>(file, f))));
    }

    /**
     * Check for common directories between two repositories
     * @param repo1 First repository
     * @param repo2 Second repository
     * @return List of pairs of common directories
     * @param <A> Type of path for the first repository
     * @param <B> Type of path for the second repository
     * @param <X> Type of the first repository
     * @param <Y> Type of the second repository
     */
    public static <A, B, X extends AbstractRepository<A, AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryFile<A>>,
            Y extends AbstractRepository<B, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectoriesFromRepository(
            @NonNull final X repo1,
            @NonNull final Y repo2) {
        return filterAndMap(repo1.getDirectories(),
                (directory) ->
                    repo2.getDirectory(directory.getName())
                            .flatMap(dir -> Optional.of(new Pair<>(directory, dir))));
    }

    /**
     * Check for common directories between two directories
     * @param dir1 First directory
     * @param dir2 Second directory
     * @return List of pairs of common directories
     * @param <A> Type of path for the first directory
     * @param <B> Type of path for the second directory
     * @param <X> Type of the first directory
     * @param <Y> Type of the second directory
     */
    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectories(
            @NonNull final X dir1,
            @NonNull final Y dir2) {
        return filterAndMap(dir1.getInnerDirectories(),
                (file) ->
                    dir2.getDirectory(file.getName())
                            .flatMap(dir -> Optional.of(new Pair<>(file, dir))));
    }

    /**
     * Check for common files between two directories
     * @param dir1 First directory
     * @param dir2 Second directory
     * @return List of pairs of common files
     * @param <A> Type of path for the first directory
     * @param <B> Type of path for the second directory
     * @param <X> Type of the first directory
     * @param <Y> Type of the second directory
     */
    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryFile<A>, AbstractRepositoryFile<B>>> commonFilesFromDirectory(
            @NonNull final X dir1,
            @NonNull final Y dir2) {
        return filterAndMap(dir1.getFiles(),
                        (file) ->
                                dir2.getFile(file.getName())
                                        .flatMap(f -> Optional.of(new Pair<>(file, f))));
    }

    /**
     * Given a list of common files between the two repositories, It checks if they are different or not by using the input predicate.
     * @param commonFiles List of common files
     * @param areDifferent Strategy used to check if the files are different or not. If predicate is True then the files are different
     * @return List of pairs of files that are different
     * @param <A> Type of path for the first repository
     * @param <B> Type of path for the second repository
     * @param <X> Type of the first repository
     * @param <Y> Type of the second repository
     */
    public static <A, B, X extends AbstractRepositoryFile<A>, Y extends AbstractRepositoryFile<B>> List<Pair<X, Y>> checkForDifferences(
            @NonNull final List<Pair<X, Y>> commonFiles,
            @NonNull final BiPredicate<X, Y> areDifferent) {
        return commonFiles.stream()
                .filter(pair -> areDifferent.test(pair.x(), pair.y()))
                .toList();
    }

    private static <X, Y> List<Pair<X, Y>> filterAndMap(@NonNull final List<X> collection, @NonNull final Function<X, Optional<Pair<X, Y>>> mapper) {
        return collection.stream()
                .map(mapper)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
