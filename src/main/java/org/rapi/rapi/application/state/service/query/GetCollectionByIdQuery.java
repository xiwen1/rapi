package org.rapi.rapi.application.state.service.query;

import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class GetCollectionByIdQuery {

    private final CollectionPersistence collectionPersistence;

    public GetCollectionByIdQuery(CollectionPersistence collectionPersistence) {
        this.collectionPersistence = collectionPersistence;
    }

    public Collection getCollectionById(CollectionId collectionId) {
        return collectionPersistence.findById(collectionId);
    }
}
