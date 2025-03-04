package org.example.core.repository.remote;


public class RemoteFileImpl implements RemoteFile  {

    private final String name, url;
    public RemoteFileImpl(final String name, final String url) {
        this.name = name;
        this.url = url;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getContent() {
        // Read lazily
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
