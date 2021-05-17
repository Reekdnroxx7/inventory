package com.x404.beat.dao;

import com.x404.beat.models.League;
import com.xc350.web.base.mongo.dao.impl.MongodbDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by chaox on 12/27/2017.
 */
@Repository
public class SbobetLeagueDao extends MongodbDao<League> {


    public void setAllStatus(boolean flag){
        Update update = new Update();
        update.set("disable",flag);
        this.getMongoTemplate().updateMulti(new Query(), update,League.class);
    }
}
