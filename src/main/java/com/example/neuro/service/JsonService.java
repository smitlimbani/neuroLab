package com.example.neuro.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonService<T> {


    private ObjectMapper mapper = new ObjectMapper();
    public JsonService(){
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public List<T> fromJsonList(String jsonString, String key) throws JsonProcessingException {
        JsonNode node = mapper.readTree(jsonString);
        String str = node.get(key).toString();
        List<T> objects = mapper.readValue(str, new TypeReference<List<T>>() {});
        return objects;
    }

    public Object fromJson(String jsonString, String key, java.lang.Class classRef) throws JsonProcessingException {
        JsonNode node = mapper.readTree(jsonString);
        String str = node.get(key).toString();
        return mapper.readValue(str, classRef);
    }

    public String toJson(Object object,String key) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().withRootName(key).writeValueAsString(object);
    }

    public String toJson(List<Object> objects, String key) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().withRootName(key).writeValueAsString(objects);
    }

    public String toJson(Object object, String key, String jsonString) throws JsonProcessingException {
        Map<String,Object> map1 = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});
        Map<String,Object> map2 = mapper.readValue(this.toJson(object,key), new TypeReference<HashMap<String, Object>>() {});
        map1.putAll(map2);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map1); //Done
    }

    public String toJson(List<Object> objects, String key, String jsonString) throws JsonProcessingException {
        Map<String,Object> map1 = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {});
        Map<String,Object> map2 = mapper.readValue(this.toJson(objects,key), new TypeReference<HashMap<String, Object>>() {});
        map1.putAll(map2);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map1);
    }

}