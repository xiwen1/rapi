package org.rapi.rapi.application.project.service;

import io.vavr.collection.List;
import org.rapi.rapi.application.project.crew.CrewId;
import org.rapi.rapi.application.project.project.Project;
import org.rapi.rapi.application.project.project.ProjectId;

public interface ProjectPersistence {

    void save(Project project);

    Project findById(ProjectId projectId);

    void delete(ProjectId projectId);

    List<Project> findAllByCrewId(CrewId crewId);

    List<Project> findAllByInvitation(CrewId crewId);
}
