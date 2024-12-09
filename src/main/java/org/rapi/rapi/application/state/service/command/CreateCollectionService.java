package org.rapi.rapi.application.state.service.command;

import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.service.CollectionPersistence;

public class CreateCollectionService {

    private final CollectionPersistence collectionPersistence;

    public CreateCollectionService(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public Collection createCollection() {
        Collection collection = Collection.create();
        collectionPersistence.save(collection);
        return collection;
    }
}
