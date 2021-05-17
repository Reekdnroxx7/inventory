package com.x404.beat.task;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaox on 5/10/2017.
 */
public class SbobetUtils
{


    public static String replaceNullArrayNode(String replace) {
        if( StringUtils.isBlank(replace) ) {
            return replace;
        }
        char[] chars = replace.toCharArray();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( ; i < chars.length - 1; i++ ) {
            char c = chars[i];
            char n = chars[i + 1];
            sb.append(c);
            if( (c == ',' && (n == ']' || n == '}' || n == ',')) || (n == ',' && (c == '{' || c == '[' || c == ',')) ) {
                sb.append("null");
            }

        }
        sb.append(chars[i]);
        return sb.toString();
    }

    public static List<String> getSiteToken(String response) {
        String tokenStr = "[" + getScriptParams("$P.setToken('site',new tilib_Token(", "));", response) + "]";
        if( StringUtils.isBlank(tokenStr) ) {
            throw new RuntimeException("获取siteToken 失败！");
        }
        ArrayNode root = toArrayNode(tokenStr);
        JsonNode tokenNode = root.get(1);
        List<String> tk = new ArrayList<>();
        if( tokenNode instanceof ArrayNode) {
            for( JsonNode next : tokenNode ) {
                tk.add(next.asText());
            }
        }
        return tk;
    }

    public static List<String> getOddsToken(String response) {
        String tokenStr = "[" + getScriptParams("$P.setToken('od',new tilib_Token(", "));", response) + "]";
        if( StringUtils.isBlank(tokenStr) ) {
            throw new RuntimeException("获取oddsToken 失败！");
        }
        JsonNode root = toArrayNode(tokenStr);
        JsonNode tokenNode = root.get(1);
        ArrayList<String> tk = new ArrayList<>();
        if( tokenNode instanceof ArrayNode) {
            for( JsonNode next : tokenNode ) {
                tk.add(next.asText());
            }
        }
        return tk;
    }

    public static ArrayNode toArrayNode(String str) {
        String replace = str.replaceAll("'", "\"");
        replace = replaceNullArrayNode(replace);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        try {
            return (ArrayNode) objectMapper.readTree(replace);
        } catch( IOException e ) {
            throw new RuntimeException(e);
        }
    }

    public static String getScriptParams(String prefix, String suffix, String response) {
        int dataStart = response.indexOf(prefix);
        if( dataStart < 0 ) {
            return null;
        }
        int dataEn = response.indexOf(suffix, dataStart);
        if( dataEn < 0 ) {
            return null;
        }
        return response.substring(dataStart + prefix.length(), dataEn);
    }

    public static Long toInvalidLong(JsonNode node) {
        if( node == null || node instanceof NullNode || node instanceof MissingNode) {
            return null;
        }
        return node.asLong();
    }


    public static String toValidString(JsonNode node) {
        if( node == null || node instanceof NullNode || node instanceof MissingNode) {
            return null;
        }
        return node.asText();
    }

    public static Double toValidDoubleValue(JsonNode node) {
        if( node == null || node instanceof NullNode || node instanceof MissingNode) {
            return null;
        }
        return node.asDouble();
    }
}
