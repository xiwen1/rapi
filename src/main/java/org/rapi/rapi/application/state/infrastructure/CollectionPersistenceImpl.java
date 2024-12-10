package org.rapi.rapi.application.state.infrastructure;

import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.infrastructure.mapping.CollectionMappingService;
import org.rapi.rapi.application.state.infrastructure.repository.CollectionRepository;
import org.rapi.rapi.application.state.service.CollectionPersistence;
import org.springframework.stereotype.Service;

@Service
public class CollectionPersistenceImpl implements CollectionPersistence {

    private final CollectionRepository collectionRepository;
    private final CollectionMappingService collectionMappingService;

    public CollectionPersistenceImpl(CollectionRepository collectionRepository,
        CollectionMappingService collectionMappingService) {
        this.collectionRepository = collectionRepository;
        this.collectionMappingService = collectionMappingService;
    }

    @Override
    public void save(Collection collection) {
        collectionRepository.save(collectionMappingService.toCollectionDto(collection));
    }

    @Override
    public Collection findById(CollectionId collectionId) {
        return collectionMappingService.fromCollectionDto(
            collectionRepository.findById(collectionId.id().toString()).orElseThrow());
    }

    @Override
    public void delete(CollectionId collectionId) {
        collectionRepository.deleteById(collectionId.id().toString());
    }
}
