package org.iorio.core.repository;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.iorio.core.utils.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class RepositoryUtils {
    private RepositoryUtils() {}

    public static <A, B, X extends AbstractRepository<A, AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryFile<A>>,
            Y extends AbstractRepository<B, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryFile<A>, AbstractRepositoryFile<B>>> commonFilesFromRepository(
            @NonNull final X repo1,
            @NonNull final Y repo2) {
        return filterAndMap(repo1.getFiles(),
                (file) ->
                    repo2.getFile(file.getName())
                            .flatMap(f -> Optional.of(new Pair<>(file, f))));
    }

    public static <A, B, X extends AbstractRepository<A, AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryFile<A>>,
            Y extends AbstractRepository<B, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectoriesFromRepository(
            @NonNull final X repo1,
            @NonNull final Y repo2) {
        return filterAndMap(repo1.getDirectories(),
                (directory) ->
                    repo2.getDirectory(directory.getName())
                            .flatMap(dir -> Optional.of(new Pair<>(directory, dir))));
    }

    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectories(
            @NonNull final X dir1,
            @NonNull final Y dir2) {
        return filterAndMap(dir1.getInnerDirectories(),
                (file) ->
                    dir2.getDirectory(file.getName())
                            .flatMap(dir -> Optional.of(new Pair<>(file, dir))));
    }

    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryFile<A>, AbstractRepositoryFile<B>>> commonFilesFromDirectory(
            @NonNull final X dir1,
            @NonNull final Y dir2) {
        return filterAndMap(dir1.getFiles(),
                        (file) ->
                                dir2.getFile(file.getName())
                                        .flatMap(f -> Optional.of(new Pair<>(file, f))));
    }

    public static <A, B, X extends AbstractRepositoryFile<A>, Y extends AbstractRepositoryFile<B>> List<Pair<X, Y>> checkForDifferences(
            @NonNull final List<Pair<X, Y>> commonFiles,
            @NonNull final BiPredicate<X, Y> predicate) {
        return commonFiles.stream()
                .filter(pair -> predicate.test(pair.x(), pair.y()))
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
