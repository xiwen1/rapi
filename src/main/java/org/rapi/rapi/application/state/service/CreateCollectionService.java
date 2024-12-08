package org.rapi.rapi.application.state.service;

import org.rapi.rapi.application.state.collection.Collection;

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
