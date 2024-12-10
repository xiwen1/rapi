package org.rapi.rapi.application.project.crew;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Crew implements Entity<CrewId> {

    private final CrewId id;
    private String email;

    private Crew(CrewId id, String email) {
        this.id = id;
        this.email = email;
    }

    public static Crew fromRaw(CrewId id, String email) {
        return new Crew(id, email);
    }

    public static Crew create(String email) {
        return new Crew(CrewId.create(), email);
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
