package com.x404.beat.core.mongo;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.x404.beat.core.util.JsonUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by chaox on 5/8/2017.
 */
public class JsonReadConverter implements Converter<DBObject, JsonNode> {
    @Override
    public JsonNode convert(DBObject source) {
        String serialize = JSON.serialize(source);
        return JsonUtils.readTree(serialize);
    }
}
