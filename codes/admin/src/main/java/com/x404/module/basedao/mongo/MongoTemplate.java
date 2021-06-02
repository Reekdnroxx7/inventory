package com.x404.module.basedao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.xc350.web.base.mongo.BeforeUpdateEvent;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by chaox on 4/30/2017.
 */
public class MongoTemplate extends org.springframework.data.mongodb.core.MongoTemplate {
    public MongoTemplate(Mongo mongo, String databaseName) {
        super(mongo, databaseName);
    }

    public MongoTemplate(Mongo mongo, String databaseName, UserCredentials userCredentials) {
        super(mongo, databaseName, userCredentials);
    }

    public MongoTemplate(MongoDbFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    public MongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
    }

    @Override
    protected WriteResult doUpdate(String collectionName, Query query, Update update, Class<?> entityClass, boolean upsert, boolean multi) {
       if(entityClass != null){
           maybeEmitEvent(new BeforeUpdateEvent(entityClass,update.getUpdateObject(),collectionName));
       }
        return super.doUpdate(collectionName, query, update, entityClass, upsert, multi);
    }

    @Override
    public void save(Object entity) {
        Pair<String, Object> pair = extractIdPropertyAndValue(entity);
        if(pair.getSecond() == null){
            super.save(entity);
        }
        String collectionName = super.getCollectionName(entity.getClass());
        super.maybeEmitEvent(new BeforeConvertEvent<>(entity,collectionName));
        DBObject dbDoc = new BasicDBObject();
        getConverter().write(entity, dbDoc);
        dbDoc.removeField(pair.getFirst());
        Update update = new Update();
        Map<String,Object> map = dbDoc.toMap();
                map.forEach(update::set);
        this.upsert(Query.query(Criteria.where(pair.getFirst()).is(pair.getSecond())),
                update, collectionName);
    }

    public Pair<String, Object> extractIdPropertyAndValue(Object object) {

        Assert.notNull(object, "Id cannot be extracted from 'null'.");

        Class<?> objectType = object.getClass();


        MongoPersistentEntity<?> entity = super.getConverter().getMappingContext().getPersistentEntity(objectType);

        if (entity != null && entity.hasIdProperty()) {

            MongoPersistentProperty idProperty = entity.getIdProperty();
            return Pair.of(idProperty.getFieldName(), entity.getPropertyAccessor(object).getProperty(idProperty));
        }

        throw new MappingException("No id property found for object of type " + objectType);
    }




    public static final class Pair<S, T> {

        private final  S first;
        private final  T second;

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
