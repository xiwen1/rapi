package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.infrastructure.dto.InventoryDto;
import org.rapi.rapi.application.api.infrastructure.repository.InventoryRepository;
import org.rapi.rapi.application.api.inventory.Inventory;
import org.rapi.rapi.application.api.inventory.InventoryId;
import org.rapi.rapi.application.api.service.InventoryPersistence;
import org.springframework.stereotype.Service;

@Service
public class InventoryPersistenceImpl implements InventoryPersistence {

    private final InventoryRepository inventoryRepository;

    public InventoryPersistenceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void save(Inventory inventory) {
        inventoryRepository.save(InventoryDto.fromDomain(inventory));
    }

    @Override
    public void delete(InventoryId inventoryId) {
        inventoryRepository.deleteById(inventoryId.id().toString());
    }

    @Override
    public Inventory findById(InventoryId id) {
        return inventoryRepository.findById(id.id().toString()).orElseThrow().toDomain();
    }
}
