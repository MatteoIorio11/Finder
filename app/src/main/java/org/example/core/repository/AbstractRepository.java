package org.example.core.repository;

import org.example.core.repository.local.LocalFileImpl;
import org.example.core.repository.local.LocalFileReaderImpl;
import org.example.core.repository.remote.RemoteFileReaderImpl;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public abstract class AbstractRepository<P, X extends AbstractRepositoryDirectory<P, Y>, Y extends AbstractRepositoryFile<P>> implements RepositoryElement<P>{
    private final Map<String, X> directories;
    private final Map<String, Y> files;
    private final String name;
    private final P path;

    protected AbstractRepository(final String name, final P path) {
        this.name = name;
        this.path = path;
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
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
        this.directories.put(directory.getName(), directory);
    }
    public void addFile(Y file){
        this.files.put(file.getName(), file);
    }
    public List<X> getDirectories(){
        return directories.values().stream().toList();
    }
    public List<Y> getFiles(){
        return files.values().stream().toList();
    }

    public boolean hasDirectory(String name){
        return this.directories.containsKey(name);
    }

    public Optional<X> getDirectory(String name){
        return Optional.ofNullable(this.directories.get(name));
    }

    public Optional<Y> getFile(String name){
        return Optional.ofNullable(this.files.get(name));
    }

    public boolean hasFile(String name){
        return this.files.containsKey(name);
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
