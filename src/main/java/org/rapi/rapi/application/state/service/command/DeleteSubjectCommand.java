package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.SubjectId;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class DeleteSubjectCommand {

    private final CollectionPersistence collectionPersistence;

    public DeleteSubjectCommand(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public void deleteSubject(SubjectId subjectId, CollectionId collectionId) {
        var collection = collectionPersistence.findById(collectionId);
        collection.removeSubject(subjectId);
        collectionPersistence.save(collection);
    }
}
