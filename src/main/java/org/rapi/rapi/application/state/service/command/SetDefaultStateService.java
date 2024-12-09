package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.CollectionPersistence;

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
