package com.x404.admin.core.mongo;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by chaox on 5/8/2017.
 */
public class JsonWriteConverter implements Converter<JsonNode,DBObject> {
    @Override
    public DBObject convert(JsonNode source) {
        return (DBObject) JSON.parse(source.toString());
    }
}
