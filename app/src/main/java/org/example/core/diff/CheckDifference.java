package org.example.core.diff;

import org.example.core.repository.AbstractRepository;
import org.example.core.repository.AbstractRepositoryDirectory;
import org.example.core.repository.AbstractRepositoryFile;
import org.example.core.repository.RepositoryFactory;
import org.example.core.repository.local.LocalFileReaderImpl;
import org.example.core.repository.remote.RemoteFileReaderImpl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CheckDifference {
    private final static String GITHUB_URL = "https://github.com";
    public static List<String> checkDifference(final String user, final String repo, final String accessToken, final String localPath, final String branchA, final String branchB) throws MalformedURLException {
        final URL url = URI.create(GITHUB_URL + "/" + user + "/" + repo + "/tree/" + branchA).toURL();
        System.out.println(url);
        BranchSwitcher.switchBranch(localPath, branchB);
        final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository = RepositoryFactory.remoteRepository(repo, url, accessToken);
        final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository = RepositoryFactory.localRepository(repo, Path.of(localPath));
        System.out.println(checkCommonDirectoriesFromRepository(localRepository, remoteRepository));
        return List.of();
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFilesFromRepository(final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository, final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository) {
        return localRepository.getFiles().stream()
                .filter(file -> remoteRepository.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteRepository.getFile(file.getName()).get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> checkCommonDirectoriesFromRepository(final AbstractRepository<Path, AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryFile<Path>> localRepository, final AbstractRepository<URL, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>, AbstractRepositoryFile<URL>> remoteRepository) {
        return localRepository.getDirectories().stream()
                .filter(directory -> remoteRepository.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteRepository.getDirectory(directory.getName()).get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>>, AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>>>> checkCommonDirectories(final AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDirectory, final AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDirectory) {
        return localDirectory.getInnerDirectories().stream()
                .filter(directory -> remoteDirectory.hasDirectory(directory.getName()))
                .map(directory -> Map.entry(directory, remoteDirectory.getDirectory(directory.getName()).get()))
                .toList();
    }

    private static List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFilesFromDirectory(final AbstractRepositoryDirectory<Path, AbstractRepositoryFile<Path>> localDir, final AbstractRepositoryDirectory<URL, AbstractRepositoryFile<URL>> remoteDir) {
        return localDir.getFiles().stream()
                .filter(file -> remoteDir.hasFile(file.getName()))
                .map(file -> Map.entry(file, remoteDir.getFile(file.getName()).get()))
                .toList();
    }

    private static List<String> checkForDifferences(final List<Map.Entry<AbstractRepositoryFile<Path>, AbstractRepositoryFile<URL>>> commonFiles) {
        return commonFiles.stream().filter(commonFile -> commonFile.getKey().getContent(new LocalFileReaderImpl()).equals(commonFile.getValue().getContent(new RemoteFileReaderImpl())))
                .map(commonFile -> commonFile.getKey().getName())
                .toList();
    }
}
