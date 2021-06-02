package com.x404.module.basedao.mongo.dao.impl;

import com.xc350.web.base.model.IdEntity;
import com.xc350.web.base.mongo.dao.IMongoBaseDao;
import com.xc350.web.base.utils.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.util.List;

public class MongodbDao<T extends IdEntity> implements IMongoBaseDao<T> {


    private MongoTemplate mongoTemplate;


    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected Class<T> entityClass;

    public MongodbDao() {
        super();
        entityClass = Reflections.getClassGenricType(getClass());
    }

    public void insertBatch(List<T> entityList) {
        mongoTemplate.insert(entityList, entityClass);
    }

    public void deleteByKey(Serializable key) {
        Query query = getKeyQuery(key);
        deleteByQuery(query);
    }

    public T selectByKey(Serializable key) {
        return mongoTemplate.findById(key, entityClass);
    }


    public List<T> selectAll() {
        return mongoTemplate.findAll(entityClass);
    }

    public void insert(T entity) {
        mongoTemplate.insert(entity);
    }


    public void upsert(T entity) {
        mongoTemplate.upsert(getKeyQuery(entity.getId()),buildUpdate(entity),entityClass);
    }

    public int countByQuery(Query query) {
        return (int) mongoTemplate.count(query, entityClass);
    }

    public void deleteByQuery(Query query) {
        getMongoTemplate().remove(query, entityClass);
    }

    public List<T> selectByQuery(Query query) {
        return getMongoTemplate().find(query, entityClass);
    }

    public void updateByQuerySelective(T entity, Query query) {
        Update update = buildUpdate(entity);
        getMongoTemplate().updateMulti(query, update, entityClass);
    }


    public void updateByQuery(T entity, Query query) {
        Update update = new Update();
        MongoPersistentEntity<?> persistentEntity = mongoTemplate.getConverter().getMappingContext().getPersistentEntity(entity.getClass());
        persistentEntity.doWithProperties(new PropertyHandler<MongoPersistentProperty>() {
            @Override
            public void doWithPersistentProperty(MongoPersistentProperty persistentProperty) {
                Object value = ReflectionUtils.getField(persistentProperty.getField(), entity);
                if (value == null && persistentProperty.getGetter() != null) {
                    value = ReflectionUtils.invokeMethod(persistentProperty.getGetter(),entity);
                }
                if (value == null) {
                    update.unset(persistentProperty.getName());
                } else {
                    update.set(persistentProperty.getName(), value);
                }
            }
        });
        getMongoTemplate().updateMulti(query, update, entityClass);
    }

    public void updateByKeySelective(T t) {
        Query query = new Query(Criteria.where("_id").is(t.getId()));
        updateByQuerySelective(t, query);
    }


    protected Query getKeyQuery(Serializable key) {
        Query query = new Query(Criteria.where("_id").is(key));
        return query;
    }


    public void insertSelective(T entity) {
        insert(entity);
    }

    public void updateByKey(T t) {
        Query query = new Query(Criteria.where("_id").is(t.getId()));
        updateByQuery(t, query);
    }

    private Update buildUpdate(final T entity) {
        Update update = new Update();
        MongoPersistentEntity<?> persistentEntity = mongoTemplate.getConverter().getMappingContext().getPersistentEntity(entity.getClass());
        persistentEntity.doWithProperties(new PropertyHandler<MongoPersistentProperty>() {
            @Override
            public void doWithPersistentProperty(MongoPersistentProperty persistentProperty) {
                Object value = ReflectionUtils.getField(persistentProperty.getField(), entity);
                if (value == null && persistentProperty.getGetter() != null) {
                    value = ReflectionUtils.invokeMethod(persistentProperty.getGetter(),entity);
                }
                if(value != null){
                    update.set(persistentProperty.getName(), value);
                }
            }
        });
        return update;
    }




}
