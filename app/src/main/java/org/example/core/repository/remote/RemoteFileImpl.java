package org.example.core.repository.remote;


import java.net.URL;
import java.util.Objects;

public class RemoteFileImpl implements RemoteFile  {

    private final String name;
    private final URL url;
    public RemoteFileImpl(final String name, final URL url) {
        this.name = Objects.requireNonNull(name);
        this.url = Objects.requireNonNull(url);
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public URL getPath() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String toString() {
        return "RemoteFileImpl{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
