package org.rapi.rapi.application.project.project.participant;

import lombok.Getter;
import org.rapi.rapi.application.project.crew.CrewId;

@Getter
public class Member implements Participant {

    private final CrewId crew;

    public Member(CrewId crew) {
        this.crew = crew;
    }
}
