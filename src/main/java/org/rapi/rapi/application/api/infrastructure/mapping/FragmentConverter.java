package org.rapi.rapi.application.api.infrastructure.mapping;

import org.rapi.rapi.application.api.endpoint.route.ConstantFragment;
import org.rapi.rapi.application.api.endpoint.route.Fragment;
import org.rapi.rapi.application.api.endpoint.route.SchemaFragment;
import org.rapi.rapi.application.api.infrastructure.dto.FragmentDto;
import org.springframework.stereotype.Service;

@Service
public class FragmentConverter {

    private final SchemaConverter schemaConverter;

    public FragmentConverter(SchemaConverter schemaConverter) {
        this.schemaConverter = schemaConverter;
    }

    public FragmentDto toFragmentDto(Fragment fragment) {
        switch (fragment) {
            case ConstantFragment constantFragment -> {
                var fragmentDto = new FragmentDto();
                fragmentDto.setConstant(constantFragment.constant());
                fragmentDto.setType("constant");
                return fragmentDto;
            }
            case SchemaFragment schemaFragment -> {
                var fragmentDto = new FragmentDto();
                fragmentDto.setName(schemaFragment.name());
                fragmentDto.setSchema(schemaConverter.toSchemaDto(schemaFragment.schema()));
                fragmentDto.setType("schema");
                return fragmentDto;
            }
        }
    }

    public Fragment fromFragmentDto(FragmentDto fragmentDto) {
        switch (fragmentDto.getType()) {
            case "constant" -> {
                return new ConstantFragment(fragmentDto.getConstant());
            }
            case "schema" -> {
                return new SchemaFragment(fragmentDto.getName(),
                    schemaConverter.fromSchemaDto(fragmentDto.getSchema()));
            }
            default -> throw new IllegalArgumentException("Invalid fragment type");
        }
    }
}
