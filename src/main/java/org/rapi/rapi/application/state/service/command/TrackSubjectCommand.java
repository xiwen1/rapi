package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.Subject;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class TrackSubjectCommand {

    private final CollectionPersistence collectionPersistence;

    public TrackSubjectCommand(CollectionPersistence collectionPersistence) {
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
