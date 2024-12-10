package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.State;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateStateCommand {

    private final CollectionPersistence collectionPersistence;

    public CreateStateCommand(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public State createState(CollectionId collectionId, String name) {
        // preparing
        var collection = collectionPersistence.findById(collectionId);

        // operation
        var state = collection.createState(name);

        // persistence
        collectionPersistence.save(collection);
        return state;
    }
}
