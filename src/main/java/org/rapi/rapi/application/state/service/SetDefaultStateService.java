package org.rapi.rapi.application.state.service;

import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;

public class SetDefaultStateService {
    private final CollectionPersistence collectionPersistence;

    public SetDefaultStateService(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public void setDefaultState(CollectionId collectionId, StateId stateId) {
        // preparing
        var collection = collectionPersistence.findById(collectionId);

        // operation
        collection.changeDefaultState(stateId);

        // persistence
        collectionPersistence.save(collection);
    }

}
