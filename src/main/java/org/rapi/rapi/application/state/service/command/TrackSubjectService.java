package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.Subject;
import org.rapi.rapi.application.state.service.CollectionPersistence;

public class TrackSubjectService {
    private final CollectionPersistence collectionPersistence;

    public TrackSubjectService(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public Subject trackSubject(CollectionId collectionId) {
        // preparing
        var collection = collectionPersistence.findById(collectionId);

        // operation
        var subject = collection.createSubject();

        // persistence
        collectionPersistence.save(collection);
        return subject;
    }
}
