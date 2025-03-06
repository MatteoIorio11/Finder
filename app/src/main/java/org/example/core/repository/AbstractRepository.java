package org.example.core.repository;

import java.io.File;
import java.net.URL;
import java.util.*;

public abstract class AbstractRepository<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>> implements RepositoryElement<P>{
    private final List<X> directories;
    private final List<Y> files;
    private final String name;
    private final P path;

    protected AbstractRepository(final String name, final P path) {
        this.name = name;
        this.path = path;
        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public P getPath() {
        return this.path;
    }

    public void addDirectory(X directory) {
        this.directories.add(directory);
    }
    public void addFile(Y file){
        this.files.add(file);
    }
    public List<X> getDirectories(){
        return Collections.unmodifiableList(directories);
    }
    public List<Y> getFiles(){
        return Collections.unmodifiableList(files);
    }

    public List<String> differenceWithRepository(final AbstractRepository<?, ?, ?> repository) {
        final List<String> differences = new LinkedList<>(this.getDifferentFiles(this.getFiles(), repository.getFiles()));
    }

    private List<String> getDifferentFiles(final List<? extends AbstractRepositoryFile<?>> file1, final List<? extends AbstractRepositoryFile<?>> file2) {
        return file1.stream().filter(file -> !file2.contains(file)).map(AbstractRepositoryFile::getName).toList();
    }


    @Override
    public String toString() {
        return "AbstractRepository{" +
                "directories=" + this.directories +
                ", files=" + this.files +
                ", name='" + this.name + '\'' +
                ", path=" + this.path +
                '}';
    }
}
