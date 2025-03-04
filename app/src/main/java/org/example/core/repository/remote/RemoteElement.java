package org.example.core.repository.remote;

import org.example.core.repository.RepositoryElement;

public interface RemoteElement extends RepositoryElement {
    String getRemoteUrl();
}
