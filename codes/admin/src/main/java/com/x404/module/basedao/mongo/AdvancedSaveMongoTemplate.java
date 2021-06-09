package com.x404.module.basedao.mongo;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/12/18.
 */
public class AdvancedSaveMongoTemplate extends MongoTemplate
{
    private static final String ID_FIELD = "_id";

    public AdvancedSaveMongoTemplate(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    public AdvancedSaveMongoTemplate(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    public AdvancedSaveMongoTemplate(MongoDbFactory mongoDbFactory, @Nullable MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
    }

    @Override
    public void save(Object entity) {
        Pair<String, Object> pair = extractIdPropertyAndValue(entity);
        if( pair.getSecond() == null ) {
            return;
        }
        String collectionName = super.getCollectionName(entity.getClass());
        super.maybeEmitEvent(new BeforeConvertEvent<>(entity, collectionName));
        Document dbDoc = new Document();
        getConverter().write(entity, dbDoc);
        dbDoc.remove(pair.getFirst());
        Update update = new Update();
        dbDoc.forEach(update::set);
        this.upsert(Query.query(Criteria.where(pair.getFirst()).is(pair.getSecond())),
                update, collectionName);
    }

    public Pair<String, Object> extractIdPropertyAndValue(Object object) {

        Assert.notNull(object, "Id cannot be extracted from 'null'.");

        Class<?> objectType = object.getClass();

        if( object instanceof Document ) {
            return Pair.of(ID_FIELD, ((Document) object).get(ID_FIELD));
        }

        MongoPersistentEntity<?> entity = super.getConverter().getMappingContext().getPersistentEntity(objectType);

        if( entity != null && entity.hasIdProperty() ) {

            MongoPersistentProperty idProperty = entity.getIdProperty();
            return Pair.of(idProperty.getFieldName(), entity.getPropertyAccessor(object).getProperty(idProperty));
        }

        throw new MappingException("No id property found for object of type " + objectType);
    }


    public static final class Pair<S, T>
    {

        private final S first;
        private final T second;

        public Pair(S first, T second) {
            this.first = first;
            this.second = second;
        }

        public static <S, T> Pair<S, T> of(S first, T second) {
            return new Pair<>(first, second);
        }

        /**
         * Returns the first element of the {@link Pair}.
         *
         * @return
         */
        public S getFirst() {
            return first;
        }

        /**
         * Returns the second element of the {@link Pair}.
         *
         * @return
         */
        public T getSecond() {
            return second;
        }

        public static <S, T> Collector<Pair<S, T>, ?, Map<S, T>> toMap() {
            return Collectors.toMap(Pair::getFirst, Pair::getSecond);
        }
    }

}
