package org.example.core.scraper.local;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.local.*;
import org.example.core.scraper.AbstractScraper;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class LocalScraperImpl extends AbstractScraper<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>, LocalCollection> {

    public LocalScraperImpl() {
        super(false);
    }

    @Override
    public AbstractRepository<Path,  AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>,  AbstractRepositoryFile<Path>> getRepository(final String repositoryName, final Path repositoryPath, final Optional<String> inputToken) {
        final var repository = new LocalRepositoryImpl(repositoryName, repositoryPath);
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    @Override
    protected Optional<LocalCollection> readFromPath(final String repositoryName, final Path path, final Optional<String> token) {
        final File rootDirectory = new File(path.toString());
        if (rootDirectory.exists() && rootDirectory.listFiles() != null) {
            final File[] allFiles = rootDirectory.listFiles();
            if (Objects.nonNull(allFiles)) {
                final List<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>> directories = new LinkedList<>();
                final List<AbstractRepositoryFile<Path>> files = new LinkedList<>();
                Stream.of(allFiles).filter(file -> !(file.isDirectory() && file.getName().equals(".git"))).forEach(file -> {
                    final String uniqueName = repositoryName + file.getPath().split(repositoryName)[1];
                    if (file.isDirectory()) {
                        directories.add(new LocalDirectoryImpl(uniqueName, file.toPath()));
                    } else {
                        files.add(new LocalFileImpl(uniqueName, file.toPath()));
                    }
                });
                return Optional.of(new LocalCollection(files, directories));
            }
        }
        return Optional.empty();
    }

}
