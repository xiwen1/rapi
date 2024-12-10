package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.infrastructure.mapping.ApiMappingService;
import org.rapi.rapi.application.api.infrastructure.repository.InventoryRepository;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class InventoryPersistenceImpl implements InventoryPersistence {

    private final InventoryRepository inventoryRepository;
    private final ApiMappingService apiMappingService;

    public InventoryPersistenceImpl(InventoryRepository inventoryRepository,
        ApiMappingService apiMappingService) {
        this.inventoryRepository = inventoryRepository;
        this.apiMappingService = apiMappingService;
    }


    @Override
    public void save(Inventory inventory) {
        inventoryRepository.save(apiMappingService.toInventoryDto(inventory));
    }

    @Override
    public void delete(InventoryId inventoryId) {
        inventoryRepository.deleteById(inventoryId.id().toString());
    }

    @Override
    public Inventory findById(InventoryId id) {
        return apiMappingService.fromInventoryDto(
            (inventoryRepository.findById(id.id().toString()).orElseThrow()));
    }
}
