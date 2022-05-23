package com.social.Social.util;

import org.modelmapper.ModelMapper;

public class MapperUtil {
    private static ModelMapper modelMapper = null;
    private MapperUtil(){
    }
    public static ModelMapper getModelMapper() {
        if (modelMapper == null){
            return new ModelMapper();
        }
        return modelMapper;
    }
}
