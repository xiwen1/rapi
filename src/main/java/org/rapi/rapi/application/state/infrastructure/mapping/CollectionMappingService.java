package org.rapi.rapi.application.state.infrastructure.mapping;

import io.vavr.collection.List;
import java.util.UUID;
import org.rapi.rapi.application.state.collection.Collection;
import org.rapi.rapi.application.state.collection.CollectionId;
import org.rapi.rapi.application.state.collection.State;
import org.rapi.rapi.application.state.collection.StateId;
import org.rapi.rapi.application.state.collection.Subject;
import org.rapi.rapi.application.state.collection.SubjectId;
import org.rapi.rapi.application.state.infrastructure.dto.CollectionDto;
import org.rapi.rapi.application.state.infrastructure.dto.StateDto;
import org.rapi.rapi.application.state.infrastructure.dto.SubjectDto;
import org.springframework.stereotype.Service;

@Service
public class CollectionMappingService {

    public StateDto toStateDto(State state) {
        var dto = new StateDto();
        dto.setId(state.getId().id().toString());
        dto.setName(state.getName());
        return dto;
    }

    public State fromStateDto(StateDto stateDto) {
        return State.fromRaw(new StateId(UUID.fromString(stateDto.getId())), stateDto.getName());
    }

    public SubjectDto toSubjectDto(Subject subject) {
        var dto = new SubjectDto();
        dto.setId(subject.getId().id().toString());
        dto.setCurrentState(subject.getCurrentState().id().toString());
        return dto;
    }

    public Subject fromSubjectDto(SubjectDto subjectDto) {
        return Subject.fromRaw(new SubjectId(UUID.fromString(subjectDto.getId())),
            new StateId(UUID.fromString(subjectDto.getCurrentState())));
    }

    public CollectionDto toCollectionDto(Collection collection) {
        var dto = new CollectionDto();
        dto.setId(collection.getId().id().toString());
        dto.setSubjects(collection.getSubjects().map(this::toSubjectDto).toJavaList());
        dto.setStates(collection.getStates().map(this::toStateDto).toJavaList());
        dto.setDefaultState(collection.getDefaultState().id().toString());
        return dto;
    }

    public Collection fromCollectionDto(CollectionDto collectionDto) {
        var subjects = collectionDto.getSubjects().stream().map(this::fromSubjectDto).toList();
        var states = collectionDto.getStates().stream().map(this::fromStateDto).toList();
        return Collection.fromRaw(new CollectionId(UUID.fromString(collectionDto.getId())),
            List.ofAll(subjects), List.ofAll(states),
            new StateId(UUID.fromString(collectionDto.getDefaultState())));
    }
}
