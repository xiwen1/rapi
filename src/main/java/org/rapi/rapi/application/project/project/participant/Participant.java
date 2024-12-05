package org.rapi.rapi.application.project.project.participant;


import lombok.Getter;
import org.rapi.rapi.application.project.crew.CrewId;

@Getter
public class Participant {
    protected CrewId id;

    protected Participant(CrewId id) {
        this.id = id;
    }
}
