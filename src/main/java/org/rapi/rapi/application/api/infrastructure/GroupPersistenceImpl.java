package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.rapi.rapi.application.api.infrastructure.mapping.ApiMappingService;
import org.rapi.rapi.application.api.infrastructure.repository.CrudGroupRepository;
import org.rapi.rapi.application.api.infrastructure.repository.JwtGroupRepository;
import org.rapi.rapi.application.api.service.GroupPersistence;
import org.springframework.stereotype.Service;

@Service
public class GroupPersistenceImpl implements GroupPersistence {

    private final CrudGroupRepository crudGroupRepository;
    private final JwtGroupRepository jwtGroupRepository;
    private final ApiMappingService apiMappingService;

    public GroupPersistenceImpl(CrudGroupRepository crudGroupRepository,
        JwtGroupRepository jwtGroupRepository, ApiMappingService apiMappingService) {
        this.crudGroupRepository = crudGroupRepository;
        this.jwtGroupRepository = jwtGroupRepository;
        this.apiMappingService = apiMappingService;
    }


    @Override
    public void saveCrud(CrudGroup crudGroup) {
        crudGroupRepository.save(apiMappingService.toCrudGroupDto(crudGroup));
    }

    @Override
    public void saveJwt(JwtGroup jwtGroup) {
        jwtGroupRepository.save(apiMappingService.toJwtGroupDto(jwtGroup));
    }

    @Override
    public void deleteCrud(GroupId groupId) {
        crudGroupRepository.deleteById(groupId.id().toString());
    }

    @Override
    public void deleteJwt(GroupId groupId) {
        jwtGroupRepository.deleteById(groupId.id().toString());
    }

    @Override
    public CrudGroup findCrudById(GroupId id) {
        return apiMappingService.fromCrudGroupDto(
            crudGroupRepository.findById(id.id().toString()).orElseThrow());
    }

    @Override
    public JwtGroup findJwtById(GroupId id) {
        return apiMappingService.fromJwtGroupDto(
            jwtGroupRepository.findById(id.id().toString()).orElseThrow());
    }
}
