package org.iorio.core.repository;

import org.iorio.core.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositoryUtils {
    private RepositoryUtils() {}

    public static <X extends AbstractRepository<?, ?, ?>, Y extends AbstractRepository<?, ?, ?>> List<Pair<AbstractRepositoryFile<?>, AbstractRepositoryFile<?>>> commonFilesFromRepository(final X repo1, final Y repo2) {
        return repo1.getFiles().stream()
                .filter(file -> repo2.hasFile(file.getName()))
                .map(file -> new Pair<AbstractRepositoryFile<?>, Optional<? extends AbstractRepositoryFile<?>>>(file, repo2.getFile(file.getName())))
                .filter(entry -> entry.y().isPresent())
                .map(pair -> new Pair<AbstractRepositoryFile<?>, AbstractRepositoryFile<?>>(pair.x(), pair.y().get()))
                .toList();
    }
}
