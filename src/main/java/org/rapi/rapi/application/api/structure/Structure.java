package org.rapi.rapi.application.api.structure;

import lombok.Getter;
import org.rapi.rapi.sharedkernel.Entity;

@Getter
public class Structure implements Entity<StructureId> {
    private StructureId id;
}
