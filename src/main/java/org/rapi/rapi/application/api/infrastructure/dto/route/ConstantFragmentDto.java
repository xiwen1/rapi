package org.rapi.rapi.application.api.infrastructure.dto.route;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("constantFragment")
@Getter
@Setter
public class ConstantFragmentDto implements FragmentDto {

    private String constant;
}
