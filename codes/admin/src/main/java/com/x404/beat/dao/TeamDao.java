package com.x404.beat.dao;

import com.x404.beat.models.Team;
import com.xc350.web.base.mongo.dao.impl.MongodbDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by chaox on 12/27/2017.
 */
@Repository
public class TeamDao extends MongodbDao<Team> {
    public void addTranslate(String id, String locale, String value) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set(locale,value);
        getMongoTemplate().updateFirst(query,update,Team.class);
    }
}
