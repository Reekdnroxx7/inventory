package com.x404.module.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by chaox on 4/29/2017.
 */
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(value);
    }

    public static ObjectWriter writer() {
        return objectMapper.writer();
    }

    public static ObjectWriter writer(SerializationFeature feature) {
        return objectMapper.writer(feature);
    }

    public static ObjectWriter writer(SerializationFeature first, SerializationFeature... other) {
        return objectMapper.writer(first, other);
    }

    public static ObjectWriter writer(DateFormat df) {
        return objectMapper.writer(df);
    }

    public static ObjectWriter writerWithView(Class<?> serializationView) {
        return objectMapper.writerWithView(serializationView);
    }

    public static ObjectWriter writerFor(Class<?> rootType) {
        return objectMapper.writerFor(rootType);
    }

    public static ObjectWriter writerFor(TypeReference<?> rootType) {
        return objectMapper.writerFor(rootType);
    }

    public static ObjectWriter writerFor(JavaType rootType) {
        return objectMapper.writerFor(rootType);
    }

    public static ObjectWriter writer(PrettyPrinter pp) {
        return objectMapper.writer(pp);
    }

    public static ObjectWriter writerWithDefaultPrettyPrinter() {
        return objectMapper.writerWithDefaultPrettyPrinter();
    }

    public static ObjectWriter writer(FilterProvider filterProvider) {
        return objectMapper.writer(filterProvider);
    }

    public static ObjectWriter writer(FormatSchema schema) {
        return objectMapper.writer(schema);
    }

    public static ObjectWriter writer(Base64Variant defaultBase64) {
        return objectMapper.writer(defaultBase64);
    }

    public static ObjectWriter writer(CharacterEscapes escapes) {
        return objectMapper.writer(escapes);
    }

    public static ObjectWriter writer(ContextAttributes attrs) {
        return objectMapper.writer(attrs);
    }

    @Deprecated
    public static ObjectWriter writerWithType(Class<?> rootType) {
        return objectMapper.writerWithType(rootType);
    }

    @Deprecated
    public static ObjectWriter writerWithType(TypeReference<?> rootType) {
        return objectMapper.writerWithType(rootType);
    }

    @Deprecated
    public static ObjectWriter writerWithType(JavaType rootType) {
        return objectMapper.writerWithType(rootType);
    }

    public static ObjectReader reader() {
        return objectMapper.reader();
    }

    public static ObjectReader reader(DeserializationFeature feature) {
        return objectMapper.reader(feature);
    }

    public static ObjectReader reader(DeserializationFeature first, DeserializationFeature... other) {
        return objectMapper.reader(first, other);
    }

    public static ObjectReader readerForUpdating(Object valueToUpdate) {
        return objectMapper.readerForUpdating(valueToUpdate);
    }

    public static ObjectReader readerFor(JavaType type) {
        return objectMapper.readerFor(type);
    }

    public static ObjectReader readerFor(Class<?> type) {
        return objectMapper.readerFor(type);
    }

    public static ObjectReader readerFor(TypeReference<?> type) {
        return objectMapper.readerFor(type);
    }

    public static ObjectReader reader(JsonNodeFactory f) {
        return objectMapper.reader(f);
    }

    public static ObjectReader reader(FormatSchema schema) {
        return objectMapper.reader(schema);
    }

    public static ObjectReader reader(InjectableValues injectableValues) {
        return objectMapper.reader(injectableValues);
    }

    public static ObjectReader readerWithView(Class<?> view) {
        return objectMapper.readerWithView(view);
    }

    public static ObjectReader reader(Base64Variant defaultBase64) {
        return objectMapper.reader(defaultBase64);
    }

    public static ObjectReader reader(ContextAttributes attrs) {
        return objectMapper.reader(attrs);
    }

    @Deprecated
    public static ObjectReader reader(JavaType type) {
        return objectMapper.reader(type);
    }

    @Deprecated
    public static ObjectReader reader(Class<?> type) {
        return objectMapper.reader(type);
    }

    @Deprecated
    public static ObjectReader reader(TypeReference<?> type) {
        return objectMapper.reader(type);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static <T> T convertValue(Object fromValue, TypeReference<?> toValueTypeRef) throws IllegalArgumentException {
        return objectMapper.convertValue(fromValue, toValueTypeRef);
    }

    public static <T> T convertValue(Object fromValue, JavaType toValueType) throws IllegalArgumentException {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    @Deprecated
    public static JsonSchema generateJsonSchema(Class<?> t) throws JsonMappingException {
        return objectMapper.generateJsonSchema(t);
    }

    public static void acceptJsonFormatVisitor(Class<?> type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
        objectMapper.acceptJsonFormatVisitor(type, visitor);
    }

    public static void acceptJsonFormatVisitor(JavaType type, JsonFormatVisitorWrapper visitor) throws JsonMappingException {
        objectMapper.acceptJsonFormatVisitor(type, visitor);
    }

    public static JsonNode readTree(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readTree(byte[] content) throws IOException, JsonProcessingException {
        return objectMapper.readTree(content);
    }

    public static JsonNode readTree(File file) throws IOException, JsonProcessingException {
        return objectMapper.readTree(file);
    }

    public static JsonNode readTree(URL source) throws IOException, JsonProcessingException {
        return objectMapper.readTree(source);
    }

    public static void writeValue(JsonGenerator g, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(g, value);
    }

    public static void writeTree(JsonGenerator jgen, TreeNode rootNode) throws IOException, JsonProcessingException {
        objectMapper.writeTree(jgen, rootNode);
    }

    public static void writeTree(JsonGenerator jgen, JsonNode rootNode) throws IOException, JsonProcessingException {
        objectMapper.writeTree(jgen, rootNode);
    }

    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return objectMapper.createArrayNode();
    }

    public static JsonParser treeAsTokens(TreeNode n) {
        return objectMapper.treeAsTokens(n);
    }

    public static <T> T treeToValue(TreeNode n, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.treeToValue(n, valueType);
    }

    public static ObjectMapper copy() {
        return objectMapper.copy();
    }

    public static Version version() {
        return objectMapper.version();
    }

    public static ObjectMapper registerModule(Module module) {
        return objectMapper.registerModule(module);
    }

    public static ObjectMapper registerModules(Module... modules) {
        return objectMapper.registerModules(modules);
    }

    public static ObjectMapper registerModules(Iterable<Module> modules) {
        return objectMapper.registerModules(modules);
    }

    public static List<Module> findModules() {
        return ObjectMapper.findModules();
    }

    public static List<Module> findModules(ClassLoader classLoader) {
        return ObjectMapper.findModules(classLoader);
    }

    public static ObjectMapper findAndRegisterModules() {
        return objectMapper.findAndRegisterModules();
    }

    public static SerializationConfig getSerializationConfig() {
        return objectMapper.getSerializationConfig();
    }

    public static DeserializationConfig getDeserializationConfig() {
        return objectMapper.getDeserializationConfig();
    }

    public static DeserializationContext getDeserializationContext() {
        return objectMapper.getDeserializationContext();
    }

    public static ObjectMapper setSerializerFactory(SerializerFactory f) {
        return objectMapper.setSerializerFactory(f);
    }

    public static SerializerFactory getSerializerFactory() {
        return objectMapper.getSerializerFactory();
    }

    public static ObjectMapper setSerializerProvider(DefaultSerializerProvider p) {
        return objectMapper.setSerializerProvider(p);
    }

    public static SerializerProvider getSerializerProvider() {
        return objectMapper.getSerializerProvider();
    }

    public static ObjectMapper setMixIns(Map<Class<?>, Class<?>> sourceMixins) {
        return objectMapper.setMixIns(sourceMixins);
    }

    public static ObjectMapper addMixIn(Class<?> target, Class<?> mixinSource) {
        return objectMapper.addMixIn(target, mixinSource);
    }

    public static ObjectMapper setMixInResolver(ClassIntrospector.MixInResolver resolver) {
        return objectMapper.setMixInResolver(resolver);
    }

    public static Class<?> findMixInClassFor(Class<?> cls) {
        return objectMapper.findMixInClassFor(cls);
    }

    public static int mixInCount() {
        return objectMapper.mixInCount();
    }

    @Deprecated
    public static void setMixInAnnotations(Map<Class<?>, Class<?>> sourceMixins) {
        objectMapper.setMixInAnnotations(sourceMixins);
    }

    @Deprecated
    public static void addMixInAnnotations(Class<?> target, Class<?> mixinSource) {
        objectMapper.addMixInAnnotations(target, mixinSource);
    }

    public static VisibilityChecker<?> getVisibilityChecker() {
        return objectMapper.getVisibilityChecker();
    }

    @Deprecated
    public static void setVisibilityChecker(VisibilityChecker<?> vc) {
        objectMapper.setVisibilityChecker(vc);
    }

    public static ObjectMapper setVisibility(VisibilityChecker<?> vc) {
        return objectMapper.setVisibility(vc);
    }

    public static ObjectMapper setVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility) {
        return objectMapper.setVisibility(forMethod, visibility);
    }

    public static SubtypeResolver getSubtypeResolver() {
        return objectMapper.getSubtypeResolver();
    }

    public static ObjectMapper setSubtypeResolver(SubtypeResolver str) {
        return objectMapper.setSubtypeResolver(str);
    }

    public static ObjectMapper setAnnotationIntrospector(AnnotationIntrospector ai) {
        return objectMapper.setAnnotationIntrospector(ai);
    }

    public static ObjectMapper setAnnotationIntrospectors(AnnotationIntrospector serializerAI, AnnotationIntrospector deserializerAI) {
        return objectMapper.setAnnotationIntrospectors(serializerAI, deserializerAI);
    }

    public static ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy s) {
        return objectMapper.setPropertyNamingStrategy(s);
    }

    public static PropertyNamingStrategy getPropertyNamingStrategy() {
        return objectMapper.getPropertyNamingStrategy();
    }

    public static ObjectMapper setSerializationInclusion(JsonInclude.Include incl) {
        return objectMapper.setSerializationInclusion(incl);
    }

    public static ObjectMapper setDefaultPrettyPrinter(PrettyPrinter pp) {
        return objectMapper.setDefaultPrettyPrinter(pp);
    }

    public static ObjectMapper enableDefaultTyping() {
        return objectMapper.enableDefaultTyping();
    }

    public static ObjectMapper enableDefaultTyping(ObjectMapper.DefaultTyping dti) {
        return objectMapper.enableDefaultTyping(dti);
    }

    public static ObjectMapper enableDefaultTyping(ObjectMapper.DefaultTyping applicability, JsonTypeInfo.As includeAs) {
        return objectMapper.enableDefaultTyping(applicability, includeAs);
    }

    public static ObjectMapper enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping applicability, String propertyName) {
        return objectMapper.enableDefaultTypingAsProperty(applicability, propertyName);
    }

    public static ObjectMapper disableDefaultTyping() {
        return objectMapper.disableDefaultTyping();
    }

    public static ObjectMapper setDefaultTyping(TypeResolverBuilder<?> typer) {
        return objectMapper.setDefaultTyping(typer);
    }

    public static void registerSubtypes(Class<?>... classes) {
        objectMapper.registerSubtypes(classes);
    }

    public static void registerSubtypes(NamedType... types) {
        objectMapper.registerSubtypes(types);
    }

    public static TypeFactory getTypeFactory() {
        return objectMapper.getTypeFactory();
    }

    public static ObjectMapper setTypeFactory(TypeFactory f) {
        return objectMapper.setTypeFactory(f);
    }

    public static JavaType constructType(Type t) {
        return objectMapper.constructType(t);
    }

    public static JsonNodeFactory getNodeFactory() {
        return objectMapper.getNodeFactory();
    }

    public static ObjectMapper setNodeFactory(JsonNodeFactory f) {
        return objectMapper.setNodeFactory(f);
    }

    public static ObjectMapper addHandler(DeserializationProblemHandler h) {
        return objectMapper.addHandler(h);
    }

    public static ObjectMapper clearProblemHandlers() {
        return objectMapper.clearProblemHandlers();
    }

    public static ObjectMapper setConfig(DeserializationConfig config) {
        return objectMapper.setConfig(config);
    }

    @Deprecated
    public static void setFilters(FilterProvider filterProvider) {
        objectMapper.setFilters(filterProvider);
    }

    public static ObjectMapper setFilterProvider(FilterProvider filterProvider) {
        return objectMapper.setFilterProvider(filterProvider);
    }

    public static ObjectMapper setBase64Variant(Base64Variant v) {
        return objectMapper.setBase64Variant(v);
    }

    public static ObjectMapper setConfig(SerializationConfig config) {
        return objectMapper.setConfig(config);
    }

    public static JsonFactory getFactory() {
        return objectMapper.getFactory();
    }

    @Deprecated
    public static JsonFactory getJsonFactory() {
        return objectMapper.getJsonFactory();
    }

    public static ObjectMapper setDateFormat(DateFormat dateFormat) {
        return objectMapper.setDateFormat(dateFormat);
    }

    public static DateFormat getDateFormat() {
        return objectMapper.getDateFormat();
    }

    public static Object setHandlerInstantiator(HandlerInstantiator hi) {
        return objectMapper.setHandlerInstantiator(hi);
    }

    public static ObjectMapper setInjectableValues(InjectableValues injectableValues) {
        return objectMapper.setInjectableValues(injectableValues);
    }

    public static InjectableValues getInjectableValues() {
        return objectMapper.getInjectableValues();
    }

    public static ObjectMapper setLocale(Locale l) {
        return objectMapper.setLocale(l);
    }

    public static ObjectMapper setTimeZone(TimeZone tz) {
        return objectMapper.setTimeZone(tz);
    }

    public static boolean isEnabled(MapperFeature f) {
        return objectMapper.isEnabled(f);
    }

    public static ObjectMapper configure(MapperFeature f, boolean state) {
        return objectMapper.configure(f, state);
    }

    public static ObjectMapper enable(MapperFeature... f) {
        return objectMapper.enable(f);
    }

    public static ObjectMapper disable(MapperFeature... f) {
        return objectMapper.disable(f);
    }

    public static boolean isEnabled(SerializationFeature f) {
        return objectMapper.isEnabled(f);
    }

    public static ObjectMapper configure(SerializationFeature f, boolean state) {
        return objectMapper.configure(f, state);
    }

    public static ObjectMapper enable(SerializationFeature f) {
        return objectMapper.enable(f);
    }

    public static ObjectMapper enable(SerializationFeature first, SerializationFeature... f) {
        return objectMapper.enable(first, f);
    }

    public static ObjectMapper disable(SerializationFeature f) {
        return objectMapper.disable(f);
    }

    public static ObjectMapper disable(SerializationFeature first, SerializationFeature... f) {
        return objectMapper.disable(first, f);
    }

    public static boolean isEnabled(DeserializationFeature f) {
        return objectMapper.isEnabled(f);
    }

    public static ObjectMapper configure(DeserializationFeature f, boolean state) {
        return objectMapper.configure(f, state);
    }

    public static ObjectMapper enable(DeserializationFeature feature) {
        return objectMapper.enable(feature);
    }

    public static ObjectMapper enable(DeserializationFeature first, DeserializationFeature... f) {
        return objectMapper.enable(first, f);
    }

    public static ObjectMapper disable(DeserializationFeature feature) {
        return objectMapper.disable(feature);
    }

    public static ObjectMapper disable(DeserializationFeature first, DeserializationFeature... f) {
        return objectMapper.disable(first, f);
    }

    public static boolean isEnabled(JsonParser.Feature f) {
        return objectMapper.isEnabled(f);
    }

    public static ObjectMapper configure(JsonParser.Feature f, boolean state) {
        return objectMapper.configure(f, state);
    }

    public static ObjectMapper enable(JsonParser.Feature... features) {
        return objectMapper.enable(features);
    }

    public static ObjectMapper disable(JsonParser.Feature... features) {
        return objectMapper.disable(features);
    }

    public static boolean isEnabled(JsonGenerator.Feature f) {
        return objectMapper.isEnabled(f);
    }

    public static ObjectMapper configure(JsonGenerator.Feature f, boolean state) {
        return objectMapper.configure(f, state);
    }

    public static ObjectMapper enable(JsonGenerator.Feature... features) {
        return objectMapper.enable(features);
    }

    public static ObjectMapper disable(JsonGenerator.Feature... features) {
        return objectMapper.disable(features);
    }

    public static boolean isEnabled(JsonFactory.Feature f) {
        return objectMapper.isEnabled(f);
    }

    public static <T> T readValue(JsonParser jp, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(jp, valueType);
    }

    public static <T> T readValue(JsonParser jp, TypeReference<?> valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(jp, valueTypeRef);
    }

    public static <T> T readValue(JsonParser jp, ResolvedType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(jp, valueType);
    }

    public static <T> T readValue(JsonParser jp, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(jp, valueType);
    }

    public static <T extends TreeNode> T readTree(JsonParser jp) throws IOException, JsonProcessingException {
        return objectMapper.readTree(jp);
    }

    public static <T> MappingIterator<T> readValues(JsonParser jp, ResolvedType valueType) throws IOException, JsonProcessingException {
        return objectMapper.readValues(jp, valueType);
    }

    public static <T> MappingIterator<T> readValues(JsonParser jp, JavaType valueType) throws IOException, JsonProcessingException {
        return objectMapper.readValues(jp, valueType);
    }

    public static <T> MappingIterator<T> readValues(JsonParser jp, Class<T> valueType) throws IOException, JsonProcessingException {
        return objectMapper.readValues(jp, valueType);
    }

    public static <T> MappingIterator<T> readValues(JsonParser jp, TypeReference<?> valueTypeRef) throws IOException, JsonProcessingException {
        return objectMapper.readValues(jp, valueTypeRef);
    }

    public static JsonNode readTree(InputStream in) throws IOException, JsonProcessingException {
        return objectMapper.readTree(in);
    }

    public static JsonNode readTree(Reader r) throws IOException, JsonProcessingException {
        return objectMapper.readTree(r);
    }

    public static <T extends JsonNode> T valueToTree(Object fromValue) throws IllegalArgumentException {
        return objectMapper.valueToTree(fromValue);
    }

    public static boolean canSerialize(Class<?> type) {
        return objectMapper.canSerialize(type);
    }

    public static boolean canSerialize(Class<?> type, AtomicReference<Throwable> cause) {
        return objectMapper.canSerialize(type, cause);
    }

    public static boolean canDeserialize(JavaType type) {
        return objectMapper.canDeserialize(type);
    }

    public static boolean canDeserialize(JavaType type, AtomicReference<Throwable> cause) {
        return objectMapper.canDeserialize(type, cause);
    }

    public static <T> T readValue(File src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(File src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(File src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(URL src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(URL src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(URL src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, valueType);
    }

    public static <T> T readValue(String content, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, valueTypeRef);
    }

    public static <T> T readValue(String content, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(content, valueType);
    }

    public static <T> T readValue(Reader src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(Reader src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(Reader src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(InputStream src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(InputStream src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, int offset, int len, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, offset, len, valueType);
    }

    public static <T> T readValue(byte[] src, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueTypeRef);
    }

    public static <T> T readValue(byte[] src, int offset, int len, TypeReference valueTypeRef) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, offset, len, valueTypeRef);
    }

    public static <T> T readValue(byte[] src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, valueType);
    }

    public static <T> T readValue(byte[] src, int offset, int len, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(src, offset, len, valueType);
    }

    public static void writeValue(File resultFile, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(resultFile, value);
    }

    public static void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(out, value);
    }

    public static void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        objectMapper.writeValue(w, value);
    }
}
