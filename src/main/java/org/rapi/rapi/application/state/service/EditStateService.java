package org.rapi.rapi.application.state.service;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;

public class EditStateService {
    private final CollectionPersistence collectionPersistence;

    public EditStateService(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public void editState(CollectionId collectionId, StateId stateId, String name) {
        // preparing
        var collection = collectionPersistence.findById(collectionId);

        // operation
        collection.editState(stateId, name);

        // persistence
        collectionPersistence.save(collection);

    }
}
