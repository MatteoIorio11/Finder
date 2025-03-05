package org.example.core.repository;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
}
