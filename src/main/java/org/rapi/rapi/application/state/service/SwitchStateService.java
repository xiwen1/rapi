package org.rapi.rapi.application.state.service;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.collection.SubjectId;

public class SwitchStateService {
    private final CollectionPersistence collectionPersistence;

    public SwitchStateService(CollectionPersistence collectionPersistence) {
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
