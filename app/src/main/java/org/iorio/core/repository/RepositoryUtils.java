package org.iorio.core.repository;

import org.iorio.core.utils.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RepositoryUtils {
    private RepositoryUtils() {}

    public static <A, B, X extends AbstractRepository<A, AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryFile<A>>,
            Y extends AbstractRepository<B, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryFile<A>, AbstractRepositoryFile<B>>> commonFilesFromRepository(
            final X repo1,
            final Y repo2) {
        return filterAndMap(repo1.getFiles(),
                (file) -> {
                    if (repo2.getFile(file.getName()).isPresent()) {
                        return Optional.of(new Pair<>(file, repo2.getFile(file.getName()).get()));
                    }
                    return Optional.empty();
                });
    }

    public static <A, B, X extends AbstractRepository<A, AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryFile<A>>,
            Y extends AbstractRepository<B, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectoriesFromRepository(
            final X repo1,
            final Y repo2) {
        return filterAndMap(repo1.getDirectories(),
                (directory) -> {
                    if (repo2.getDirectory(directory.getName()).isPresent()) {
                        return Optional.of(new Pair<>(directory, repo2.getDirectory(directory.getName()).get()));
                    }
                    return Optional.empty();
                });
    }

    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectories(
            final X dir1,
            final Y dir2
    ) {
        return filterAndMap(dir1.getInnerDirectories(),
                (file) -> {
                    if (dir2.getDirectory(file.getName()).isPresent()) {
                        return Optional.of(new Pair<>(file, dir2.getDirectory(file.getName()).get()));
                    }
                    return Optional.empty();
                });
    }

    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryFile<A>, AbstractRepositoryFile<B>>> commonFilesFromDirectory(
            final X dir1,
            final Y dir2
    ) {
        return
                filterAndMap(dir1.getFiles(),
                        (file) -> {
                    if (dir2.getFile(file.getName()).isPresent()) {
                        return Optional.of(new Pair<>(file, dir2.getFile(file.getName()).get()));
                    }
                    return Optional.empty();
                });
    }

    private static <X, Y> List<Pair<X, Y>> filterAndMap(final List<X> collection, final Function<X, Optional<Pair<X, Y>>> mapper) {
        return collection.stream()
                .map(mapper)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
