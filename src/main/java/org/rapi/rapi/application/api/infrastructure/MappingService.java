package org.rapi.rapi.application.api.infrastructure;


import org.modelmapper.ModelMapper;

public class MappingService {

    private final ModelMapper modelMapper;

    public MappingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


}
