package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class DeleteStateCommand {

    private final CollectionPersistence collectionPersistence;

    public DeleteStateCommand(CollectionPersistence collectionPersistence) {
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
