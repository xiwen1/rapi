package org.rapi.rapi.application.api.infrastructure;

import org.rapi.rapi.application.api.group.CrudGroup;
import org.rapi.rapi.application.api.group.GroupId;
import org.rapi.rapi.application.api.group.JwtGroup;
import org.rapi.rapi.application.api.infrastructure.dto.CrudGroupDto;
import org.rapi.rapi.application.api.infrastructure.dto.JwtGroupDto;
import org.rapi.rapi.application.api.infrastructure.repository.CrudGroupRepository;
import org.rapi.rapi.application.api.infrastructure.repository.JwtGroupRepository;
import org.rapi.rapi.application.api.service.GroupPersistence;

public class GroupPersistenceImpl implements GroupPersistence {

    private final CrudGroupRepository crudGroupRepository;
    private final JwtGroupRepository jwtGroupRepository;

    public GroupPersistenceImpl(CrudGroupRepository crudGroupRepository,
        JwtGroupRepository jwtGroupRepository) {
        this.crudGroupRepository = crudGroupRepository;
        this.jwtGroupRepository = jwtGroupRepository;
    }

    @Override
    public void saveCrud(CrudGroup crudGroup) {
        crudGroupRepository.save(CrudGroupDto.fromDomain(crudGroup));
    }

    @Override
    public void saveJwt(JwtGroup jwtGroup) {
        jwtGroupRepository.save(JwtGroupDto.fromDomain(jwtGroup));
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
        return crudGroupRepository.findById(id.id().toString()).orElseThrow().toDomain();
    }

    @Override
    public JwtGroup findJwtById(GroupId id) {
        return jwtGroupRepository.findById(id.id().toString()).orElseThrow().toDomain();
    }
}
