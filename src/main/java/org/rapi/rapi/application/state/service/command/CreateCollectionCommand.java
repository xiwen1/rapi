package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class CreateCollectionCommand {

    private final CollectionPersistence collectionPersistence;

    public CreateCollectionCommand(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public Collection createCollection() {
        Collection collection = Collection.create();
        collectionPersistence.save(collection);
        return collection;
    }
}
