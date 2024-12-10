package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.collection.SubjectId;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class SwitchStateCommand {

    private final CollectionPersistence collectionPersistence;

    public SwitchStateCommand(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public void switchState(CollectionId collectionId, StateId stateId, SubjectId subjectId) {
        // preparing
        var collection = collectionPersistence.findById(collectionId);

        // operation
        collection.setStateForSubject(stateId, subjectId);

        // persistence
        collectionPersistence.save(collection);
    }
}
