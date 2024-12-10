package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class SetDefaultStateCommand {

    private final CollectionPersistence collectionPersistence;

    public SetDefaultStateCommand(CollectionPersistence collectionPersistence) {
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
