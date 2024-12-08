package org.rapi.rapi.application.state.service;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;

public class DeleteStateService {

    private final CollectionPersistence collectionPersistence;

    public DeleteStateService(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public void deleteState(CollectionId collectionId, StateId stateId) {
        // preparing
        var collection = collectionPersistence.findById(collectionId);

        // operation
        collection.removeState(stateId);

        // persistence
        collectionPersistence.save(collection);
    }
}
