package com.gestaofesta.config;

import java.io.IOException;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
    private static ObjectMapper mapper;

    public ObjectMapperContextResolver() {
    	
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return getMapper();
    }  
    
    public static ObjectMapper getMapper(){
    	if(mapper == null){
    		mapper = new ObjectMapper();
    		mapper.registerModule(new DateTimeModule());
        	mapper.registerModule(new JSONObjectModule());
        	mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    	}
    	return mapper;
    }
    
    public static <T> String toJson(T object) throws JsonProcessingException{
    	return getMapper().writeValueAsString(object);
    }
    
    public static <T> T fromJson(String json, Class<T> classe) throws IOException{
    	return getMapper().readValue(json, classe);
    }
}