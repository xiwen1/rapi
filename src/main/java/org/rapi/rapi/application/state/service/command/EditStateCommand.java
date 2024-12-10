package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class EditStateCommand {

    private final CollectionPersistence collectionPersistence;

    public EditStateCommand(CollectionPersistence collectionPersistence) {
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
