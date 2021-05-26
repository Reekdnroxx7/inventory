package com.x404.admin.core.support.config;

import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DynamicProperties extends Properties {
    /**
     *
     */
    private static final long serialVersionUID = -3713596509425421136L;
    private Properties properties;

    public DynamicProperties(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            this.properties = new Properties();
            properties.load(new FileInputStream(file));
            FileMonitor.getInstance().addFileChangeListener(new FileChangeListener() {

                @Override
                public void fileChanged(File file) {
                    LoggerFactory.getLogger(getClass()).info("刷新配置文件" + file);
                    Properties temp = new Properties();
                    try {
                        temp.load(new FileInputStream(file));
                        properties = temp;
                    } catch (IOException e) {
                        LoggerFactory.getLogger(getClass()).error("", e);
                    }
                }
            }, file);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object setProperty(String key, String value) {
        return properties.setProperty(key, value);
    }

    public void load(Reader reader) throws IOException {
        throw new UnsupportedOperationException();
//		properties.load(reader);
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public Enumeration<Object> keys() {
        return properties.keys();
    }

    public Enumeration<Object> elements() {
        return properties.elements();
    }

    public boolean contains(Object value) {
        return properties.contains(value);
    }

    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    public Object get(Object key) {
        return properties.get(key);
    }

    public void load(InputStream inStream) throws IOException {
        throw new UnsupportedOperationException();
//		properties.load(inStream);
    }

    public Object put(Object key, Object value) {
        return properties.put(key, value);
    }

    public Object remove(Object key) {
        return properties.remove(key);
    }

    public void putAll(Map<? extends Object, ? extends Object> t) {
        properties.putAll(t);
    }

    public void clear() {
        properties.clear();
    }


    public String toString() {
        return properties.toString();
    }

    public Set<Object> keySet() {
        return properties.keySet();
    }

    public Set<java.util.Map.Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public Collection<Object> values() {
        return properties.values();
    }

    @SuppressWarnings("deprecation")
    public void save(OutputStream out, String comments) {
        properties.save(out, comments);
    }

    public boolean equals(Object o) {
        return properties.equals(o);
    }

    public void store(Writer writer, String comments) throws IOException {
        properties.store(writer, comments);
    }

    public int hashCode() {
        return properties.hashCode();
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    public void forEach(BiConsumer<? super Object, ? super Object> action) {
        properties.forEach(action);
    }

    public void store(OutputStream out, String comments) throws IOException {
        properties.store(out, comments);
    }

    public void replaceAll(
            BiFunction<? super Object, ? super Object, ? extends Object> function) {
        properties.replaceAll(function);
    }

    public Object putIfAbsent(Object key, Object value) {
        return properties.putIfAbsent(key, value);
    }

    public boolean remove(Object key, Object value) {
        return properties.remove(key, value);
    }

    public boolean replace(Object key, Object oldValue, Object newValue) {
        return properties.replace(key, oldValue, newValue);
    }

    public void loadFromXML(InputStream in) throws IOException,
            InvalidPropertiesFormatException {
        throw new UnsupportedOperationException();
        //properties.loadFromXML(in);
    }

    public Object replace(Object key, Object value) {
        return properties.replace(key, value);
    }

    public Object computeIfAbsent(Object key,
                                  Function<? super Object, ? extends Object> mappingFunction) {
        return properties.computeIfAbsent(key, mappingFunction);
    }

    public void storeToXML(OutputStream os, String comment) throws IOException {
        properties.storeToXML(os, comment);
    }

    public Object computeIfPresent(
            Object key,
            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return properties.computeIfPresent(key, remappingFunction);
    }

    public void storeToXML(OutputStream os, String comment, String encoding)
            throws IOException {
        properties.storeToXML(os, comment, encoding);
    }

    public Object compute(
            Object key,
            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return properties.compute(key, remappingFunction);
    }

    public Object merge(
            Object key,
            Object value,
            BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        return properties.merge(key, value, remappingFunction);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public Enumeration<?> propertyNames() {
        return properties.propertyNames();
    }

    public Set<String> stringPropertyNames() {
        return properties.stringPropertyNames();
    }

    public void list(PrintStream out) {
        properties.list(out);
    }

    public void list(PrintWriter out) {
        properties.list(out);
    }


}
