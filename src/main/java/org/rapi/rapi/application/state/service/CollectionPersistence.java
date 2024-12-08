package org.rapi.rapi.application.state.service;

import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.State;

public interface CollectionPersistence {
    void save(Collection collection);
    Collection findById(CollectionId collectionId);

    void delete(CollectionId collectionId);

}
