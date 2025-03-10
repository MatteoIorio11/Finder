package org.iorio.core.scraper.local;

import org.iorio.core.repository.AbstractRepository;
import org.iorio.core.repository.AbstractRepositoryDirectory;
import org.iorio.core.repository.AbstractRepositoryFile;
import org.iorio.core.repository.local.*;
import org.iorio.core.scraper.AbstractScraper;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * A local scraper implementation
 */
public class LocalScraperImpl extends AbstractScraper<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>, LocalCollection> {

    public LocalScraperImpl() {
        super(false);
    }

    @Override
    public AbstractRepository<Path,  AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>,  AbstractRepositoryFile<Path>> getRepository(final String repositoryName, final Path repositoryPath, final Optional<String> inputToken) {
        logger.info("Creating repository: " + repositoryName);
        final var repository = new LocalRepositoryImpl(repositoryName, repositoryPath);
        this.buildRepository(repositoryPath, repository, inputToken);
        return repository;
    }

    @Override
    protected Optional<LocalCollection> readFromPath(final String repositoryName, final Path path, final Optional<String> token) {
        logger.info("Reading from path: " + path);
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
