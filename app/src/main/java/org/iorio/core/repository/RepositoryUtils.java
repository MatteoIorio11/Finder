package org.iorio.core.repository;

import org.iorio.core.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class RepositoryUtils {
    private RepositoryUtils() {}

    public static <X extends AbstractRepository<?, ?, ?>, Y extends AbstractRepository<?, ?, ?>> List<Pair<AbstractRepositoryFile<?>, AbstractRepositoryFile<?>>> commonFilesFromRepository(
            final X repo1,
            final Y repo2) {
        return repo1.getFiles().stream()
                .filter(file -> repo2.hasFile(file.getName()))
                .map(file -> new Pair<AbstractRepositoryFile<?>, Optional<? extends AbstractRepositoryFile<?>>>(file, repo2.getFile(file.getName())))
                .filter(pair -> pair.y().isPresent())
                .map(pair -> new Pair<AbstractRepositoryFile<?>, AbstractRepositoryFile<?>>(pair.x(), pair.y().get()))
                .toList();
    }

    public static <X extends AbstractRepository<?, ?, ?>, Y extends AbstractRepository<?, ?, ?>> List<Pair<AbstractRepositoryDirectory<?, ? extends AbstractRepositoryFile<?>>, AbstractRepositoryDirectory<?, ? extends AbstractRepositoryFile<?>>>> checkCommonDirectoriesFromRepository(
            final X repo1,
            final Y repo2) {
        return repo1.getDirectories().stream()
                .filter(directory -> repo2.hasDirectory(directory.getName()))
                .map(directory ->
                        new Pair<AbstractRepositoryDirectory<?, ? extends AbstractRepositoryFile<?>>, Optional<? extends AbstractRepositoryDirectory<?, ? extends AbstractRepositoryFile<?>>>>(directory, repo2.getDirectory(directory.getName())))
                .filter(pair-> pair.y().isPresent())
                .map(entry ->
                        new Pair<AbstractRepositoryDirectory<?, ? extends AbstractRepositoryFile<?>>, AbstractRepositoryDirectory<?, ? extends AbstractRepositoryFile<?>>>(entry.x(), entry.y().get()))
                .toList();
    }

    public static <A, B, X extends AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, Y extends AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>> List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> checkCommonDirectories(
            final X dir1,
            final Y dir2
    ) {
        final List<Pair<AbstractRepositoryDirectory<A, AbstractRepositoryFile<A>>, AbstractRepositoryDirectory<B, AbstractRepositoryFile<B>>>> commonDirectories =
                new LinkedList<>();

        for (final var directory : dir1.getInnerDirectories()) {
            if (dir2.getDirectory(directory.getName()).isPresent()) {
                commonDirectories.add(new Pair<>(directory, dir2.getDirectory(directory.getName()).get()));
            }
        }
        return commonDirectories;
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
